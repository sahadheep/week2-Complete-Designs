import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimitManager {
    private final Map<String, IRateLimitStrategy> userStrategies = new ConcurrentHashMap<>();

    public boolean checkLimit(String key, IRateLimitStrategy strategy) {
        // If a strategy doesn't exist for this key, register the one provided
        userStrategies.putIfAbsent(key, strategy);
        return userStrategies.get(key).isAllowed();
    }
}