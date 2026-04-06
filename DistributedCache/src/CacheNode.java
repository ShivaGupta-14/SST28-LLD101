import java.util.HashMap;
import java.util.Map;

public class CacheNode {
    private int id;
    private int capacity;
    private Map<String, String> store;
    private EvictionPolicy evictionPolicy;

    public CacheNode(int id, int capacity, EvictionPolicy evictionPolicy) {
        this.id = id;
        this.capacity = capacity;
        this.store = new HashMap<>();
        this.evictionPolicy = evictionPolicy;
    }

    public String get(String key) {
        if (!store.containsKey(key)) return null;
        evictionPolicy.keyAccessed(key);
        return store.get(key);
    }

    public void put(String key, String value) {
        if (store.containsKey(key)) {
            store.put(key, value);
            evictionPolicy.keyAccessed(key);
            return;
        }

        if (store.size() >= capacity) {
            String evictKey = evictionPolicy.evict();
            if (evictKey != null) {
                store.remove(evictKey);
                System.out.println("    Node " + id + ": Evicted key '" + evictKey + "'");
            }
        }

        store.put(key, value);
        evictionPolicy.keyAccessed(key);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Node " + id + " " + store.keySet();
    }
}
