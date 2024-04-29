package comp.Box.RateLimit;

import java.util.concurrent.atomic.AtomicLong;

/*
X-RateLimit-Limit, X-RateLimit-Remaining, and X-RateLimit-Reset, to know the current rate limit, remaining requests, and the time when the rate limit resets.
 This information helps you stay within the allowed limits and handle rate limit errors gracefully.

 Implement Backoff and Retry Mechanisms - When you encounter a rate limit error, instead of immediately giving up,
 implementing a backoff and retry mechanism is a recommended approach.
 Backoff refers to introducing a delay between retries to avoid overwhelming the API further.
  Consider using exponential backoff, where the delay between retries increases exponentially with each subsequent attempt.

  Informative Error Messages - When a rate limit is exceeded, provide clear and informative error messages to users.
  Explain the reason for the error and provide guidance on how to resolve it. This helps users understand the issue and take appropriate action.


 */
public class LeakyBucketRateLimiter {

    private final long capacity;
    private final long ratePerSecond;
    private final AtomicLong lastRequestTime;
    private final AtomicLong currentBucketSize;

    public LeakyBucketRateLimiter(long capacity, long ratePerSecond){
        this.capacity = capacity;
        this.ratePerSecond = ratePerSecond;
        this.lastRequestTime = new AtomicLong(System.currentTimeMillis());
        this.currentBucketSize = new AtomicLong(0);
    }

    public boolean isAllowed(){
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastRequestTime.getAndSet(currentTime);

        // Calculate the amount of tokens leaked since the last request
        long leakedTokens = elapsedTime * ratePerSecond / 1000;
        currentBucketSize.updateAndGet(bucketSize ->
                Math.max(0, Math.min(bucketSize + leakedTokens, capacity)));

        // Check if a request can be processed by consuming a token from the bucket
        if (currentBucketSize.get() > 0) {
            currentBucketSize.decrementAndGet();
            return true; // Request is allowed
        }
        return false; // Request is not allowed
    }
}
