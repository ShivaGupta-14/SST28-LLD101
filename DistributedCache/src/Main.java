import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Distributed Cache Demo ===\n");

        Database db = new Database();
        db.put("user:1", "Alice");
        db.put("user:2", "Bob");
        db.put("user:3", "Charlie");
        db.put("user:4", "Dave");
        db.put("user:5", "Eve");

        System.out.println("--- 3 nodes, capacity 2 each, LRU eviction, modulo distribution ---\n");

        List<CacheNode> nodes = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            nodes.add(new CacheNode(i, 2, new LRUEvictionPolicy()));
        }

        DistributedCache cache = new DistributedCache(nodes, new ModuloDistribution(), db);

        System.out.println("--- Putting values into cache ---\n");
        cache.put("user:1", "Alice");
        cache.put("user:2", "Bob");
        cache.put("user:3", "Charlie");

        System.out.println("\n--- Getting cached values (expect cache hits) ---\n");
        System.out.println("  Result: " + cache.get("user:1"));
        System.out.println("  Result: " + cache.get("user:2"));

        System.out.println("\n--- Getting value not in cache (expect miss then DB fetch) ---\n");
        System.out.println("  Result: " + cache.get("user:4"));

        System.out.println("\n--- Adding more values to trigger LRU eviction ---\n");
        cache.put("user:5", "Eve");
        cache.put("user:6", "Frank");
        cache.put("user:7", "Grace");

        System.out.println("\n--- Node contents after evictions ---\n");
        for (CacheNode node : nodes) {
            System.out.println("  " + node);
        }

        System.out.println("\n--- Getting evicted key (expect miss then DB fetch) ---\n");
        System.out.println("  Result: " + cache.get("user:1"));
    }
}
