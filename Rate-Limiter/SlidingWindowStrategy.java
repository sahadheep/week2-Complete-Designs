import java.util.concurrent.ConcurrentLinkedQueue;

public class SlidingWindowStrategy implements IRateLimitStrategy {
    private final int maxRequests;
    private final long windowSizeInMs;
    private final ConcurrentLinkedQueue<Long> timestamps = new ConcurrentLinkedQueue<>();

    public SlidingWindowStrategy(int maxRequests, long windowSizeInMs) {
        this.maxRequests = maxRequests;
        this.windowSizeInMs = windowSizeInMs;
    }

    @Override
    public synchronized boolean isAllowed() {
        long currentTime = System.currentTimeMillis();
        
        while (!timestamps.isEmpty() && currentTime - timestamps.peek() > windowSizeInMs) {
            timestamps.poll();
        }

        if (timestamps.size() < maxRequests) {
            timestamps.add(currentTime);
            return true;
        }
        return false;
    }
}