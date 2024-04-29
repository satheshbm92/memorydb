package comp.Box.RateLimit;

/*
Time (seconds):   0   0.5   1   1.5   2   2.5   3
                  |    |    |    |    |    |    |
Requests:         R         R    R    R    R
                  |         |    |    |    |
Current Tokens:   3   2    3    2    1    0    1

Time (seconds):   0   1   2   3   4   5   6   7   8   9   10   11   12
                  |               |                   |                   |
Requests:         R               R                   R                   R
                  |               |                   |                   |
Current Tokens:   5   4   4   5   4   4   4   4   5   4    4    4    5
 */
public class LeakyBucket {
    private long lastRequestTime;
    private int currentTokens;
    private final long LEAK_RATE;
    private final int BUCKET_SIZE;

    public LeakyBucket(long leakRate, int bucketSize){
        this.LEAK_RATE = leakRate;
        this.BUCKET_SIZE = bucketSize;
        this.currentTokens = bucketSize;
        this.lastRequestTime = System.currentTimeMillis();
    }

    public synchronized boolean apiCanBeProcessed(){
        long currentTime = System.currentTimeMillis();
        double elapsedTime = currentTime - lastRequestTime;

        //Step 1 - Refill tokens based on elapsed time
        int refillTokens = (int) (elapsedTime / LEAK_RATE); // 0.5 / 1 -> 0
        currentTokens = Math.min(currentTokens + refillTokens, BUCKET_SIZE);

        //Step 2 - Update last request time
        lastRequestTime = currentTime;

        //Step 3 - Check if a token is avaialable
        if(currentTokens > 0){
            currentTokens --;
            return true; // Can be processed
        }

        return false; // Can't be processed
    }
}
