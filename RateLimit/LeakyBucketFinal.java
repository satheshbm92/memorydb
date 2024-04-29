package comp.Box.RateLimit;

public class LeakyBucketFinal {
    private final long capacity;
    private final long leakRateInMillis;
    private long availableTokens;
    private long lastLeakTimestamp;

    public LeakyBucketFinal(long capacity, long leakRateInMillis) {
        this.capacity = capacity;
        this.leakRateInMillis = leakRateInMillis;
        this.availableTokens = capacity;
        this.lastLeakTimestamp = System.currentTimeMillis();
    }

    public synchronized boolean allowRequest() {
        long currentTime = System.currentTimeMillis();
        long timeSinceLastLeak = currentTime - lastLeakTimestamp;
        long tokensToLeak = timeSinceLastLeak / leakRateInMillis;

        if (tokensToLeak > 0) {
            availableTokens = Math.min(capacity, availableTokens + tokensToLeak);
            lastLeakTimestamp = currentTime;
        }
        if (availableTokens > 0) {
            availableTokens--;
            return true;
        }
        return false;
    }
}