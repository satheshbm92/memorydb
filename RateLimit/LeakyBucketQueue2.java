package comp.Box.RateLimit;


import java.util.LinkedList;
import java.util.Queue;

public class LeakyBucketQueue2 {
    private Queue<Long> requestQueue;
    private volatile long LEAK_RATE;  // Volatile to ensure visibility in multi-threaded environment
    private volatile int BUCKET_SIZE; // Volatile to ensure visibility
    private long lastLeakTime;
    private final Object lock = new Object(); // Lock for synchronization

    public LeakyBucketQueue2(long leakRate, int bucketSize) {
        configure(leakRate, bucketSize);
        this.requestQueue = new LinkedList<>();
        this.lastLeakTime = System.currentTimeMillis();
    }

    public void configure(long leakRate, int bucketSize) {
        synchronized (lock) {
            this.LEAK_RATE = leakRate;
            this.BUCKET_SIZE = bucketSize;
        }
    }

    public boolean apiCanBeProcessed(long requestTime) {
        synchronized (lock) {
            leakRequests(requestTime);
            if (requestQueue.size() < BUCKET_SIZE) {
                requestQueue.add(requestTime);
                return true;
            }
            return false;
        }
    }

    private void leakRequests(long currentTime) {
        synchronized (lock) {
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
    }


    public static void main(String[] args) {
        LeakyBucketQueue2 bucket = new LeakyBucketQueue2(1000, 3); // Initial configuration
        bucket.configure(500, 5); // Re-configuration to faster leak rate and larger bucket

        long startTime = System.currentTimeMillis();
        System.out.println("R1 accepted: " + bucket.apiCanBeProcessed(startTime));
        System.out.println("R2 accepted: " + bucket.apiCanBeProcessed(startTime + 5));
        System.out.println("R3 accepted: " + bucket.apiCanBeProcessed(startTime + 20));
        System.out.println("R4 accepted: " + bucket.apiCanBeProcessed(startTime + 25));
        System.out.println("R5 accepted: " + bucket.apiCanBeProcessed(startTime + 30));
        System.out.println("R6 rejected: " + bucket.apiCanBeProcessed(startTime + 35));
    }
}
