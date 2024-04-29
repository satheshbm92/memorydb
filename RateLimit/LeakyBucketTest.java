package comp.Box.RateLimit;

public class LeakyBucketTest {
    public static void main(String[] args) throws InterruptedException {
        LeakyBucket bucket = new LeakyBucket(1000, 3);

        // Simulate a series of API calls
        System.out.println("Request 1: " + bucket.apiCanBeProcessed()); // Should be true
        System.out.println("Request 2: " + bucket.apiCanBeProcessed()); // Should be true
        System.out.println("Request 3: " + bucket.apiCanBeProcessed()); // Should be true
        System.out.println("Request 4: " + bucket.apiCanBeProcessed()); // False ?
        System.out.println("Request 5: " + bucket.apiCanBeProcessed()); // Should be false, bucket empty


        // Wait for 1.5 seconds to allow some tokens to refill
        Thread.sleep(1500);

        System.out.println("Request 6: " + bucket.apiCanBeProcessed()); // Should be true, 1 token refilled
        System.out.println("Request 7: " + bucket.apiCanBeProcessed()); // Should be false, no more tokens
    }
}
