import java.util.List;

public class DistributedCache {
    private List<CacheNode> nodes;
    private DistributionStrategy strategy;
    private Database database;

    public DistributedCache(List<CacheNode> nodes, DistributionStrategy strategy, Database database) {
        this.nodes = nodes;
        this.strategy = strategy;
        this.database = database;
    }

    public String get(String key) {
        int nodeIndex = strategy.getNode(key, nodes.size());
        CacheNode node = nodes.get(nodeIndex);
        String value = node.get(key);

        if (value != null) {
            System.out.println("  Cache HIT on Node " + nodeIndex + " for '" + key + "'");
            return value;
        }

        System.out.println("  Cache MISS on Node " + nodeIndex + " for '" + key + "'");
        value = database.get(key);
        if (value != null) {
            node.put(key, value);
        }
        return value;
    }

    public void put(String key, String value) {
        int nodeIndex = strategy.getNode(key, nodes.size());
        CacheNode node = nodes.get(nodeIndex);
        node.put(key, value);
        database.put(key, value);
        System.out.println("  Stored '" + key + "' on Node " + nodeIndex);
    }
}
