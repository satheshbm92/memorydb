package comp.Box.RateLimit;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implements a Leaky Bucket rate limiter using a queue to manage request timestamps.
 * This class supports synchronized access to process API requests, ensuring that
 * the rate of processing does not exceed a set threshold defined by a leak rate.
 */
public class LeakyBucketQueue {
    private final Queue<Long> requestQueue; // Queue to hold the timestamps of incoming requests
    private final long LEAK_RATE;           // Milliseconds required to leak one request
    private final int BUCKET_SIZE;          // Maximum number of requests the bucket can hold
    private long lastLeakTime;              // Timestamp when the last request was leaked

    /**
     * Constructs a Leaky Bucket rate limiter.
     * @param leakRate The time in milliseconds that one request takes to leak out of the bucket.
     * @param bucketSize The maximum number of requests the bucket can hold.
     */
    public LeakyBucketQueue(long leakRate, int bucketSize) {
        this.LEAK_RATE = leakRate;
        this.BUCKET_SIZE = bucketSize;
        this.requestQueue = new LinkedList<>();
        this.lastLeakTime = System.currentTimeMillis(); // Initialize the last leak time to the current time
    }

    /**
     * Processes an incoming API request.
     * @param requestTime The timestamp of the incoming request.
     * @return true if the request is accepted, false if the bucket is full and the request is rejected.
     */
    public synchronized boolean apiCanBeProcessed(long requestTime) {
        leakRequests(requestTime); // Attempt to leak requests based on the elapsed time
        if (requestQueue.size() < BUCKET_SIZE) {
            requestQueue.add(requestTime); // Add the new request time to the queue
            return true; // Request is accepted
        }
        return false; // Request is rejected due to full bucket
    }

    /**
     * Leaks requests from the queue based on the elapsed time since the last leak.
     * @param currentTime The current time at which the check is being made.
     */
    private void leakRequests(long currentTime) {
        long elapsedTime = currentTime - lastLeakTime;
        int requestsToProcess = (int) (elapsedTime / LEAK_RATE);
        int processedRequests = 0;  // Count the actual number of requests processed

        while (requestsToProcess > 0 && !requestQueue.isEmpty()) {
            requestQueue.poll();  // Remove the oldest request from the queue
            requestsToProcess--;
            processedRequests++;
        }

        if (processedRequests > 0) {
            lastLeakTime += processedRequests * LEAK_RATE;  // Update the last leak time based on actual leaks
        }
    }

/**
 * Simulation class to test the LeakyBucket functionality.
 */
    public static void main(String[] args) {
        LeakyBucketQueue bucket = new LeakyBucketQueue(1000, 3); // 1 second leak rate, size 3

        long startTime = System.currentTimeMillis();
        System.out.println("R1 accepted: " + bucket.apiCanBeProcessed(startTime));      // R1 at t=0s
        System.out.println("R2 accepted: " + bucket.apiCanBeProcessed(startTime + 5));   // R2 at t=0.005s
        System.out.println("R3 accepted: " + bucket.apiCanBeProcessed(startTime + 20));  // R3 at t=0.02s
        System.out.println("R4 rejected: " + bucket.apiCanBeProcessed(startTime + 50)); // R4 at t=0.05s
        System.out.println("R5 accepted: " + bucket.apiCanBeProcessed(startTime + 2000));// R5 at t=2s
    }
}
