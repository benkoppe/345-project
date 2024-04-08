package com.csc345.data;

import com.csc345.data.LinkedListSet;

public class HashSet<E> {
    private static final int SIZE = 100; // Fixed size, could be made dynamic
    private LinkedListSet<E>[] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    public HashSet() {
        buckets = (LinkedListSet<E>[]) new Object[SIZE];
        for (int i = 0; i < SIZE; i++) {
            buckets[i] = new LinkedListSet<>();
        }
        size = 0;
    }

    // public boolean add 


    
}
