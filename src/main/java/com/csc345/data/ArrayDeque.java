package com.csc345.data;

import com.csc345.data.functionals.Consumer;

/**
 * An array-based implementation of a generic deque.
 */
public class ArrayDeque<E> {
    private E[] elements; // Array to store deque elements
    private int size = 0; // Number of elements in the deque
    private int head = 0; // Index of the head element in the array

    /**
     * Initialize a new deque with a given initial capacity.
     * 
     * @param capacity the initial capcity of the deque
     */
    @SuppressWarnings("unchecked")
    public ArrayDeque(int capacity) {
        elements = (E[]) new Object[capacity];
    }

    /**
     * Adds an element to the start of the deque.
     * 
     * @param e element to add
     */
    public void addFirst(E e) {
        if (size == elements.length) {
            // Array is full, resize needed here
            resize(elements.length * 2); // Double the size
        }
        head = (head - 1 + elements.length) % elements.length; // Circular decrement
        elements[head] = e;
        size++;
    }

    /**
     * Adds an element to the end of the deque.
     * 
     * @param e element to add
     */
    public void addLast(E e) {
        if (size == elements.length) {
            // Array is full, resize needed here
            resize(elements.length * 2); // Double the size
        }
        elements[(head + size) % elements.length] = e;
        size++;
    }

    /**
     * Returns the element at the start of the deque.
     * 
     * @return element at start of deque
     */
    public E getFirst() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Deque is empty");
        }
        return elements[head];
    }

    /**
     * Returns the element at the end of the deque.
     * 
     * @return element at end of deque
     */
    public E getLast() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Deque is empty");
        }
        return elements[(head + size - 1) % elements.length];
    }

    /**
     * Returns the size fo the deque.
     * 
     * @return size of the deque
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns whether the deque contains an element.
     * 
     * @param e requested element
     * @return whether the element is in the deque
     */
    public boolean contains(E e) {
        for (int i = 0; i < size; i++) {
            if (elements[(head + i) % elements.length] == e) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a value relative to the end of the deque.
     * 
     * @param displacement desired displacement from the end of the deque
     * @return value at given displacement from the end
     */
    public E getRelativeToEnd(int displacement) {
        if (displacement < 0 || displacement >= size) {
            throw new IllegalArgumentException("Invalid displacement");
        }
        return elements[(head + size - 1 - displacement) % elements.length];
    }

    /**
     * Removes and returns the value at the start of the deque.
     * 
     * @return removed value
     */
    public E removeFirst() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Deque is empty");
        }
        E removed = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length; // Circular increment
        size--;
        if (isEmpty()) {
            head = 0; // Reset pointers if empty
        }
        return removed;
    }

    /**
     * Removes and returns the value at the end of the deque.
     * 
     * @return removed value
     */
    public E removeLast() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Deque is empty");
        }
        E removed = elements[(head + size - 1) % elements.length];
        elements[(head + size - 1) % elements.length] = null;
        size--;
        if (isEmpty()) {
            head = 0; // Reset pointers if empty
        }
        return removed;
    }

    /**
     * Returns whether the deque is empty.
     * 
     * @return whether the deque is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Resizes the deque to a given capacity.
     * 
     * @param capacity new deque capacity
     */
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        E[] temp = (E[]) new Object[capacity];
        for (int i = 0; i < size; i++) { // Copy elements in order, starting from head
            temp[i] = elements[(head + i) % elements.length];
        }
        elements = temp;
        head = 0;
    }

    /**
     * Runs a given consumer action for all values in the deque.
     * 
     * @param action the action to run for all values
     */
    public void forEach(Consumer<E> action) {
        for (int i = 0; i < size; i++) {
            action.accept(elements[(head + i) % elements.length]);
        }
    }

    /**
     * Clears the deque.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[(head + i) % elements.length] = null;
        }
        head = 0;
        size = 0;
    }
}