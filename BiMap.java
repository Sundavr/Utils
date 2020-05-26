import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Bidirectionnal HashMap, so the values associated to keys also have to be unique.
 * @author Johan Gousset
 * @param <K> type of Map's keys
 * @param <V> type of Map's values
 */
public class BiMap<K, V> implements Map<K, V> {
    /**
     * HashMap that maps keys to values.
     */
    private final HashMap<K, V> valueFromKey;
    /**
     * HashMap that maps values to keys.
     */
    private final HashMap<V, K> keyFromValue;
    
    public BiMap() {
        this.valueFromKey = new HashMap();
        this.keyFromValue = new HashMap();
    }
    
    public BiMap(Map<? extends K,? extends V> m) {
        this.valueFromKey = new HashMap(m);
        this.keyFromValue = new HashMap();
        for (Entry<? extends K, ? extends V> entry : this.valueFromKey.entrySet()) {
            this.keyFromValue.put(entry.getValue(), entry.getKey());
        }
    }
    
    public BiMap(int initialCapacity) {
        this.valueFromKey = new HashMap(initialCapacity);
        this.keyFromValue = new HashMap(initialCapacity);
    }
    
    public BiMap(int initialCapacity, float loadFactor) {
        this.valueFromKey = new HashMap(initialCapacity, loadFactor);
        this.keyFromValue = new HashMap(initialCapacity, loadFactor);
    }
    
    @Override
    public void clear() {
        this.valueFromKey.clear();
        this.keyFromValue.clear();
    }
    
    @Override
    public boolean containsKey(Object key) {
        return this.valueFromKey.containsKey(key);
    }
    
    @Override
    public boolean containsValue(Object value) {
        return this.valueFromKey.containsValue(value);
    }
    
    @Override
    public V get(Object key) {
        return this.valueFromKey.get(key);
    }
    
    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return this.valueFromKey.getOrDefault(key, defaultValue);
    }
    
    @Override
    public boolean isEmpty() {
        return this.valueFromKey.isEmpty();
    }
    
    @Override
    public Set<Entry<K,V>> entrySet() {
        return this.valueFromKey.entrySet();
    }
    
    @Override
    public Set<K> keySet() {
        return this.valueFromKey.keySet();
    }

    @Override
    public V put(K key, V value) {
        if (this.keyFromValue.containsKey(value)) {
            this.valueFromKey.remove(this.keyFromValue.get(value));
        }
        this.keyFromValue.put(value, key);
        return this.valueFromKey.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
            if (this.keyFromValue.containsKey(entry.getValue())) {
                this.valueFromKey.remove(this.keyFromValue.get(entry.getValue()));
            }
            this.keyFromValue.put(entry.getValue(), entry.getKey());
            this.valueFromKey.put(entry.getKey(), entry.getValue());
        }
    }
    
    @Override
    public V putIfAbsent(K key, V value) {
        V oldValue;
        if ((oldValue = this.valueFromKey.putIfAbsent(key, value)) == null) {
            if (this.keyFromValue.containsKey(value)) {
                this.valueFromKey.remove(this.keyFromValue.get(value));
            }
            this.keyFromValue.put(value, key);
        }
        return oldValue;
    }
    
    @Override
    public V remove(Object key) {
        V value = this.valueFromKey.remove(key);
        this.keyFromValue.remove(value);
        return value;
    }
    
    @Override
    public boolean remove(Object key, Object value) {
        if (this.valueFromKey.remove(key, value)) {
            this.keyFromValue.remove((V)value);
            return true;
        }
        return false;
    }
    
    @Override
    public int size() {
        return this.valueFromKey.size();
    }
    
    @Override
    public Collection<V> values() {
        return this.valueFromKey.values();
    }
    
    @Override
    public V replace(K key, V value) {
        V oldValue;
        if ((oldValue = this.valueFromKey.replace(key, value)) != null) {
            this.keyFromValue.remove(oldValue);
            if (this.keyFromValue.containsKey(value)) {
                this.valueFromKey.remove(this.keyFromValue.get(value));
            }
            this.keyFromValue.put(value, key);
        }
        return oldValue;
    }
    
    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        if (this.valueFromKey.replace(key, oldValue, newValue)) {
            this.keyFromValue.remove(oldValue);
            if (this.keyFromValue.containsKey(newValue)) {
                this.valueFromKey.remove(this.keyFromValue.get(newValue));
            }
            this.keyFromValue.put(newValue, key);
            return true;
        }
        return false;
    }
    
    @Override
    public void replaceAll(BiFunction<? super K,? super V,? extends V> function) {
        Set<K> keySet = this.valueFromKey.keySet();
        for (K key : keySet) {
            V oldValue = this.valueFromKey.get(key);
            V newValue = function.apply(key, this.valueFromKey.get(key));
            this.valueFromKey.put(key, newValue);
            this.keyFromValue.remove(oldValue);
            if (this.keyFromValue.containsKey(newValue)) {
                this.valueFromKey.remove(this.keyFromValue.get(newValue));
            }
            this.keyFromValue.put(newValue, key);
        }
    }
    
    @Override
    public V merge(K key, V value, BiFunction<? super V,? super V,? extends V> remappingFunction) {
        V oldValue = this.valueFromKey.get(key);
        V newValue = this.valueFromKey.merge(key, value, remappingFunction);
        this.keyFromValue.remove(oldValue);
        if (newValue != null) {
            if (this.keyFromValue.containsKey(newValue)) {
                this.valueFromKey.remove(this.keyFromValue.get(newValue));
            }
            this.keyFromValue.put(newValue, key);
        }
        return newValue;
    }
    
    @Override
    public V compute(K key, BiFunction<? super K,? super V,? extends V> remappingFunction) {
        V oldValue = this.valueFromKey.get(key);
        V newValue = this.valueFromKey.compute(key, remappingFunction);
        this.keyFromValue.remove(oldValue);
        if (newValue != null) {
            if (this.keyFromValue.containsKey(newValue)) {
                this.valueFromKey.remove(this.keyFromValue.get(newValue));
            }
            this.keyFromValue.put(newValue, key);
        }
        return newValue;
    }
    
    @Override
    public V computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction) {
        V oldValue = this.valueFromKey.get(key);
        V newValue = this.valueFromKey.computeIfAbsent(key, mappingFunction);
        if (newValue != null) {
            this.keyFromValue.remove(oldValue);
            if (this.keyFromValue.containsKey(newValue)) {
                this.valueFromKey.remove(this.keyFromValue.get(newValue));
            }
            this.keyFromValue.put(newValue, key);
        }
        return newValue;
    }
    
    @Override
    public V computeIfPresent(K key, BiFunction<? super K,? super V,? extends V> remappingFunction) {
        V oldValue = this.valueFromKey.get(key);
        V newValue = this.valueFromKey.computeIfPresent(key, remappingFunction);
        this.keyFromValue.remove(oldValue);
        if (newValue != null) {
            if (this.keyFromValue.containsKey(newValue)) {
                this.valueFromKey.remove(this.keyFromValue.get(newValue));
            }
            this.keyFromValue.put(newValue, key);
        }
        return newValue;
    }
    
    @Override
    public void forEach(BiConsumer<? super K,? super V> action) {
        this.valueFromKey.forEach(action);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || !(o instanceof Map)) return false;
        return this.valueFromKey.equals((Map)o);
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.valueFromKey);
        hash = 53 * hash + Objects.hashCode(this.keyFromValue);
        return hash;
    }
    
    @Override
    public String toString() {
        Iterator<Entry<K,V>> i = entrySet().iterator();
        if (!i.hasNext()) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (;;) {
            Entry<K,V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append(key  == this ? "(this Map)" : key);
            sb.append('=');
            sb.append(value == this ? "(this Map)" : value);
            if (!i.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
    }
    
    /**
     * Return a HashMap assiociating each key to a value.
     * @return a HashMap assiociating each key to a value
     */
    public HashMap<K, V> getValueFromKeyMap() {
        return new HashMap(this.valueFromKey);
    }
    
    /**
     * Return a HashMap assiociating each value to a key.
     * @return a HashMap assiociating each value to a key
     */
    public HashMap<V, K> getKeyFromValueMap() {
        return new HashMap(this.keyFromValue);
    }
    
    /**
     * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    public V getValue(K key) {
        return this.valueFromKey.get(key);
    }
    
    /**
     * Returns the key to which the specified value is mapped, or null if this map contains no mapping for the value.
     * @param value the value whose associated key is to be returned
     * @return the key to which the specified value is mapped, or null if this map contains no mapping for the value
     */
    public K getKey(V value) {
        return this.keyFromValue.get(value);
    }
    
    /**
     * Removes the mapping for the specified key from this map if present.
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no mapping for key. (A null return can also indicate that the map previously associated null with key)
     */
    public V removeKey(K key) {
        V value = this.valueFromKey.remove(key);
        this.keyFromValue.remove(value);
        return value;
    }
    
    /**
     * Removes the mapping for the specified value from this map if present.
     * @param value value whose mapping is to be removed from the map
     * @return the previous key associated with value, or null if there was no mapping for value. (A null return can also indicate that the map previously associated null with value)
     */
    public K removeValue(V value) {
        K key = this.keyFromValue.remove(value);
        this.valueFromKey.remove(key);
        return key;
    }
}
