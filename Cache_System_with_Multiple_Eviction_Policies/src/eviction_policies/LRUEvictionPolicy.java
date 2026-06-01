package eviction_policies;

public class LRUEvictionPolicy implements EvictionPolicy {
    private int size;
    public LRUEvictionPolicy(int size) {
        this.size = size;
    }

    @Override
    public void put(String key, Object value) {

    }

    @Override
    public Object get(String key) {
        return null;
    }
}
