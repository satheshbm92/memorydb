package comp.Box;

public class Counter3Sync {
    // A regular integer field
    private static int count = 0;

    // Increments the count in a synchronized method to prevent thread interference
    public synchronized int incrementAndGet() {
        return ++count;
    }

    // Gets the current value in a synchronized method to ensure visibility
    public synchronized int getCount() {
        return count;
    }

    public static void main(String[] args) {
        Counter3Sync counter = new Counter3Sync();

        // Simulate 5 threads each incrementing the counter
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println("Incremented count: " + counter.incrementAndGet());
            }).start();
        }
    }
}
