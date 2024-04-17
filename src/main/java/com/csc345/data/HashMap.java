package com.csc345.data;

/**
 * A generic hash map implementation that uses separate chaining to handle collisions.
 */
public class HashMap<K, V> {
    
    /**
     * A generic Entry in the HashMap.
     */
    private static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;

        /**
         * Constructs a new Entry with the given key, value, and next Entry.
         * 
         * @param key the Entry's key
         * @param value the Entry's value
         * @param next the next Entry in the chain
         */
        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    
    private Entry<K, V>[] table;
    private int size;
    private static final int INITIAL_CAPAITY = 500;

    /**
     * Constructs a new HashMap with an initial capacity of 500.
     */
    @SuppressWarnings("unchecked")
    public HashMap() {
        table = (Entry<K, V>[]) new Entry[INITIAL_CAPAITY];
    }

    /**
     * Puts a new key and value in the map.
     * 
     * @param key the new key
     * @param value the new value
     */
    public void put(K key, V value) {
        int index = getIndex(key);
        Entry<K, V> newEntry = new Entry<>(key, value, null);

        if (table[index] == null) {
            table[index] = newEntry;
        } else {
            Entry<K, V> current = table[index];
            Entry<K, V> prev = null;
            while (current != null) {
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                prev = current;
                current = current.next;
            }
            prev.next = newEntry;
        }
        size++;
    }

    /**
     * Gets a key's value.
     * 
     * @param key requested key
     * @return requested value
     */
    public V get(K key) {
        int index = getIndex(key);
        Entry<K, V> entry = table[index];
        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    /**
     * Returns whether a key is contained within the map.
     * 
     * @param key requested key
     * @return whether requested key has a value
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Returns the size of the map.
     * 
     * @return the size of the map
     */
    public int size() {
        return size;
    }

    /**
     * Returns a set of all keys within the map in a List
     * 
     * @return a list of all keys in the map
     */
    public List<K> keySet() {
        List<K> keys = new List<>();
        for (Entry<K, V> entry : table) {
            while (entry != null) {
                keys.append(entry.key);
                entry = entry.next;
            }
        }
        return keys;
    }

    /**
     * Returns the requested key's value, or a given default value
     * 
     * @param key requested key
     * @param defaultValue default value in case requested key doesn't exist
     * @return requested value, or default value
     */
    public V getOrDefault(K key, V defaultValue) {
        V value = get(key);
        return value == null ? defaultValue : value;
    }

    /**
     * Gets the index in the hashmap that's associated with the key.
     * 
     * @param key requested key
     * @return requested index
     */
    private int getIndex(K key) {
        return key.hashCode() % table.length;
    }
}
