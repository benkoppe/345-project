package com.csc345.data;

import java.util.NoSuchElementException; // import only used for an exception

public class SimpleArrayDeque {
    private int[] elements; // Array to store deque elements
    private int size = 0; // Number of elements in the deque
    private int head = 0; // Index of the head element in the array

    public SimpleArrayDeque(int capacity) {
        elements = new int[capacity];
    }

    public void addFirst(int e) {
        if (size == elements.length) {
            // Array is full, resize needed here
            resize(elements.length * 2); // Double the size
        }
        head = (head - 1 + elements.length) % elements.length; // Circular decrement
        elements[head] = e;
        size++;
    }

    public int getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return elements[head];
    }

    public int removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        int removed = elements[head];
        elements[head] = 0; // Optional: nullify for garbage collection in a non-primitive version
        head = (head + 1) % elements.length; // Circular increment
        size--;
        if (isEmpty()) {
            head = 0; // Reset pointers if empty
        }
        return removed;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // Optional: A resize method for expanding the array when it gets full
    // This would copy the old array into a new, larger array and adjust head and tail indexes appropriately.
    private void resize(int capacity) {
        int[] temp = new int[capacity];
        for (int i = 0; i < size; i++) { // Copy elements in order, starting from head
            temp[i] = elements[(head + i) % elements.length];
        }
        elements = temp;
        head = 0;
    }
}