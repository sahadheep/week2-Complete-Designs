import java.util.concurrent.atomic.AtomicInteger;

public class FixedWindowStrategy implements IRateLimitStrategy {
    private final int maxRequests;
    private final long windowSizeInMs;
    private final AtomicInteger counter = new AtomicInteger(0);
    private long windowStartTime;

    public FixedWindowStrategy(int maxRequests, long windowSizeInMs) {
        this.maxRequests = maxRequests;
        this.windowSizeInMs = windowSizeInMs;
        this.windowStartTime = System.currentTimeMillis();
    }

    @Override
    public synchronized boolean isAllowed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - windowStartTime >= windowSizeInMs) {
            counter.set(0);
            windowStartTime = currentTime;
        }

        if (counter.get() < maxRequests) {
            counter.incrementAndGet();
            return true;
        }
        return false;
    }
}