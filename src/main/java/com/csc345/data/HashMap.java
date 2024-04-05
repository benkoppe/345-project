package com.csc345.data;

public class HashMap<K, V> {
    private static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    
    private Entry<K, V>[] table;
    private int size;
    private static final int INITIAL_CAPAITY = 16;

    @SuppressWarnings("unchecked")
    public HashMap() {
        table = (Entry<K, V>[]) new Entry[INITIAL_CAPAITY];
    }

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

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public V getOrDefault(K key, V defaultValue) {
        V value = get(key);
        return value == null ? defaultValue : value;
    }

    private int getIndex(K key) {
        return key.hashCode() % table.length;
    }
}
