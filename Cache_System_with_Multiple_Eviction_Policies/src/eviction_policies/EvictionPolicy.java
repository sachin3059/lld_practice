package eviction_policies;

public interface EvictionPolicy {
    void put(String key, Object value);
    Object get(String key);
}
