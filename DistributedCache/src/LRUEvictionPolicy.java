import java.util.LinkedList;

public class LRUEvictionPolicy implements EvictionPolicy {
    private LinkedList<String> order;

    public LRUEvictionPolicy() {
        this.order = new LinkedList<>();
    }

    public void keyAccessed(String key) {
        order.remove(key);
        order.addLast(key);
    }

    public String evict() {
        if (order.isEmpty()) return null;
        return order.removeFirst();
    }

    public void remove(String key) {
        order.remove(key);
    }
}
