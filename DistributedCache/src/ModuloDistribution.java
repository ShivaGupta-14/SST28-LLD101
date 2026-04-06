public class ModuloDistribution implements DistributionStrategy {

    public int getNode(String key, int nodeCount) {
        return Math.abs(key.hashCode()) % nodeCount;
    }
}
