import java.util.HashMap;
import java.util.Map;

public class SlidingWindowCounter implements RateLimiter {
    private int maxRequests;
    private long windowSizeMs;
    private Map<String, Integer> counts;

    public SlidingWindowCounter(int maxRequests, long windowSizeMs) {
        this.maxRequests = maxRequests;
        this.windowSizeMs = windowSizeMs;
        this.counts = new HashMap<>();
    }

    public synchronized boolean allowRequest(String key) {
        long now = System.currentTimeMillis();
        long currentWindowStart = (now / windowSizeMs) * windowSizeMs;
        long prevWindowStart = currentWindowStart - windowSizeMs;

        String currentKey = key + ":" + currentWindowStart;
        String prevKey = key + ":" + prevWindowStart;

        int currentCount = counts.getOrDefault(currentKey, 0);
        int prevCount = counts.getOrDefault(prevKey, 0);

        double elapsed = now - currentWindowStart;
        double weight = 1.0 - (elapsed / (double) windowSizeMs);
        double weightedCount = prevCount * weight + currentCount;

        if (weightedCount < maxRequests) {
            counts.put(currentKey, currentCount + 1);
            return true;
        }

        return false;
    }
}
