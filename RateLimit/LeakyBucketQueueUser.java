package comp.Box.RateLimit;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implements a Leaky Bucket rate limiter using a queue to manage request timestamps.
 * This class supports synchronized access to process API requests, ensuring that
 * the rate of processing does not exceed a set threshold defined by a leak rate.
 *
 *
 *   UserRateLimiter
 *   └── userBuckets: ConcurrentHashMap<String, LeakyBucketQueue>
 *        ├─ "user1" ───> LeakyBucketQueue
 *        │                ├─ requestQueue: Queue<Long>
 *        │                │    ├─ 12:00:00.000 (R1)
 *        │                │    ├─ 12:00:00.005 (R2)
 *        │                │    └─ 12:00:00.020 (R3)
 *        │                └─ LEAK_RATE: 1000 ms
 *        │                └─ BUCKET_SIZE: 3
 *        │                └─ lastLeakTime: Time when last request was leaked
 *        │
 *        └─ "user2" ───> LeakyBucketQueue
 *                         ├─ requestQueue: Queue<Long>
 *                         │    ├─ 12:00:00.000 (R1)
 *                         │    ├─ 12:00:00.100 (R2)
 *                         │    └─ 12:00:00.200 (R3)
 *                         └─ LEAK_RATE: 1000 ms
 *                         └─ BUCKET_SIZE: 3
 *                         └─ lastLeakTime: Time when last request was leaked
 */
public class LeakyBucketQueueUser {
    private final Queue<Long> requestQueue; // Queue to hold the timestamps of incoming requests
    private final long LEAK_RATE;           // Milliseconds required to leak one request
    private final int BUCKET_SIZE;          // Maximum number of requests the bucket can hold
    private long lastLeakTime;              // Timestamp when the last request was leaked

    public LeakyBucketQueueUser(long leakRate, int bucketSize) {
        this.LEAK_RATE = leakRate;
        this.BUCKET_SIZE = bucketSize;
        this.requestQueue = new LinkedList<>();
        this.lastLeakTime = System.currentTimeMillis(); // Initialize the last leak time to the current time
    }

    public synchronized boolean apiCanBeProcessed(long requestTime) {
        leakRequests(requestTime);
        if (requestQueue.size() < BUCKET_SIZE) {
            requestQueue.add(requestTime);
            return true;
        }
        return false;
    }

    private void leakRequests(long currentTime) {
        long elapsedTime = currentTime - lastLeakTime;
        int requestsToProcess = (int) (elapsedTime / LEAK_RATE);
        int processedRequests = 0;

        while (requestsToProcess > 0 && !requestQueue.isEmpty()) {
            requestQueue.poll();
            requestsToProcess--;
            processedRequests++;
        }

        if (processedRequests > 0) {
            lastLeakTime += processedRequests * LEAK_RATE;
        }
    }

    public static void main(String[] args) {
        UserRateLimiter rateLimiter = new UserRateLimiter(1000, 3); // 1 second leak rate, size 3
        long startTime = System.currentTimeMillis();
        String user1 = "user1";
        String user2 = "user2";

        // Test for User 1
        System.out.println("User1 R1 accepted: " + rateLimiter.allowRequest(user1, startTime));
        System.out.println("User1 R2 accepted: " + rateLimiter.allowRequest(user1, startTime + 5));
        System.out.println("User1 R3 accepted: " + rateLimiter.allowRequest(user1, startTime + 20));
        System.out.println("User1 R4 rejected: " + !rateLimiter.allowRequest(user1, startTime + 50));

        // Test for User 2
        System.out.println("User2 R1 accepted: " + rateLimiter.allowRequest(user2, startTime));
        System.out.println("User2 R2 accepted: " + rateLimiter.allowRequest(user2, startTime + 100));
        System.out.println("User2 R3 accepted: " + rateLimiter.allowRequest(user2, startTime + 200));
        System.out.println("User2 R4 accepted: " + rateLimiter.allowRequest(user2, startTime + 1000));
    }
}

/**
 * Manages individual rate limiting buckets for each user.
 */
class UserRateLimiter {
    private Map<String, LeakyBucketQueueUser> userBuckets;
    private long defaultLeakRate;
    private int defaultBucketSize;

    public UserRateLimiter(long leakRate, int bucketSize) {
        this.defaultLeakRate = leakRate;
        this.defaultBucketSize = bucketSize;
        this.userBuckets = new ConcurrentHashMap<>();
    }

    public boolean allowRequest(String userId, long requestTime) {
        LeakyBucketQueueUser bucket = userBuckets.get(userId);
        if (bucket == null) {
            bucket = new LeakyBucketQueueUser(defaultLeakRate, defaultBucketSize);
            userBuckets.put(userId, bucket);
        }
        return bucket.apiCanBeProcessed(requestTime);
    }
}

