import java.util.HashMap;
import java.util.Map;

public class FixedWindowCounter implements RateLimiter {
    private int maxRequests;
    private long windowSizeMs;
    private Map<String, Integer> counts;
    private Map<String, Long> windowStart;

    public FixedWindowCounter(int maxRequests, long windowSizeMs) {
        this.maxRequests = maxRequests;
        this.windowSizeMs = windowSizeMs;
        this.counts = new HashMap<>();
        this.windowStart = new HashMap<>();
    }

    public synchronized boolean allowRequest(String key) {
        long now = System.currentTimeMillis();
        Long start = windowStart.get(key);

        if (start == null || now - start >= windowSizeMs) {
            counts.put(key, 1);
            windowStart.put(key, now);
            return true;
        }

        int count = counts.getOrDefault(key, 0);
        if (count < maxRequests) {
            counts.put(key, count + 1);
            return true;
        }

        return false;
    }
}
