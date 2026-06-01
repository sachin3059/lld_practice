package eviction_policies;

public class LFUEvictionPolicy implements EvictionPolicy {
    private int size;
    public LFUEvictionPolicy(int size) {
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
