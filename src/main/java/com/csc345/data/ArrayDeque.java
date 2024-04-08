package com.csc345.data;

import com.csc345.data.functionals.Consumer;

public class ArrayDeque {
    private int[] elements; // Array to store deque elements
    private int size = 0; // Number of elements in the deque
    private int head = 0; // Index of the head element in the array

    public ArrayDeque(int capacity) {
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

    public void addLast(int e) {
        if (size == elements.length) {
            // Array is full, resize needed here
            resize(elements.length * 2); // Double the size
        }
        elements[(head + size) % elements.length] = e;
        size++;
    }

    public int getFirst() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Deque is empty");
        }
        return elements[head];
    }

    public int getLast() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Deque is empty");
        }
        return elements[(head + size - 1) % elements.length];
    }

    public int getSize() {
        return size;
    }

    public boolean contains(int e) {
        for (int i = 0; i < size; i++) {
            if (elements[(head + i) % elements.length] == e) {
                return true;
            }
        }
        return false;
    }

    public int getRelativeToEnd(int displacement) {
        if (displacement < 0 || displacement >= size) {
            throw new IllegalArgumentException("Invalid displacement");
        }
        return elements[(head + size - 1 - displacement) % elements.length];
    }

    public int removeFirst() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Deque is empty");
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

    public int removeLast() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Deque is empty");
        }
        int removed = elements[(head + size - 1) % elements.length];
        elements[(head + size - 1) % elements.length] = 0; // Optional: nullify for garbage collection in a non-primitive version
        size--;
        if (isEmpty()) {
            head = 0; // Reset pointers if empty
        }
        return removed;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize(int capacity) {
        int[] temp = new int[capacity];
        for (int i = 0; i < size; i++) { // Copy elements in order, starting from head
            temp[i] = elements[(head + i) % elements.length];
        }
        elements = temp;
        head = 0;
    }

    public void forEach(Consumer<Integer> action) {
        for (int i = 0; i < size; i++) {
            action.accept(elements[(head + i) % elements.length]);
        }
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[(head + i) % elements.length] = 0; // nullify for garbage collection if made generic
        }
        head = 0;
        size = 0;
    }
}