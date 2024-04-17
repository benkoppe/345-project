package com.csc345.data;

import com.csc345.data.functionals.CompareBy;

/**
 * A generic max priority queue implementation that requires a comparison function.
 */
public class PriorityQueue<T, C extends Comparable<C>> {
    private T[] heap;
    private int size;
    private CompareBy<T, C> compareBy;

    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Creates a new max priority queue that sorts elements depending on the compareBy function.
     * 
     * @param compareBy the comparison function
     */
    @SuppressWarnings("unchecked")
    public PriorityQueue(CompareBy<T, C> compareBy) {
        this.heap = (T[]) new Object[DEFAULT_CAPACITY];
        this.compareBy = compareBy;
    }

    /**
     * Inserts a new value into the queue.
     * 
     * @param value the value to insert
     */
    public void insert(T value) {
        if (size == heap.length)
            resize();
        heap[size] = value;
        siftUp(size++);
    }

    /**
     * Returns the top value in the queue.
     * 
     * @return the top value in the queue
     */
    public T peek() {
        return size == 0 ? null : heap[0];
    }

    /**
     * Removes and returns the top value of the queue.
     * 
     * @return the (former) top value of the queue
     */
    public T poll() {
        if (size == 0)
            return null;
        T result = heap[0];
        heap[0] = heap[--size];
        siftDown(0);
        return result;
    }

    /**
     * Returns whether a desired value is contained in the queue.
     * 
     * @param value requested value
     * @return whether the value is in the queue
     */
    public boolean contains(T value) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(value))
                return true;
        }
        return false;
    }

    /**
     * Returns whether the queue is empty.
     * 
     * @return whether the queue is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Sifts up from a given index to retain the heap property.
     * 
     * @param idx the index to sift up from
     */
    private void siftUp(int idx) {
        T val = heap[idx];
        while (idx > 0) {
            int parentIdx = (idx - 1) / 2;
            T parent = heap[parentIdx];

            if (compareBy.getValue(val).compareTo(compareBy.getValue(parent)) >= 0)
                break;

            heap[idx] = parent;
            idx = parentIdx;
        }
        heap[idx] = val;
    }

    /**
     * Sifts down from a given index to retain the heap property.
     * 
     * @param idx the index to sift down from
     */
    private void siftDown(int idx) {
        T val = heap[idx];
        while (idx < size / 2) {
            int leftChildIdx = 2 * idx + 1;
            int rightChildIdx = leftChildIdx + 1;
            T leftChild = heap[leftChildIdx];
            T rightChild = rightChildIdx < size ? heap[rightChildIdx] : null;

            boolean rightIsSmaller = (rightChild != null
                    && compareBy.getValue(rightChild).compareTo(compareBy.getValue(leftChild)) < 0);
            int smallerChildIdx = rightIsSmaller ? rightChildIdx : leftChildIdx;
            T smallerChild = heap[smallerChildIdx];

            if (compareBy.getValue(val).compareTo(compareBy.getValue(smallerChild)) <= 0)
                break;
            heap[idx] = smallerChild;
            idx = smallerChildIdx;
        }
        heap[idx] = val;
    }

    /**
     * Resize the internal heap to double the old size.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        T[] newHeap = (T[]) new Object[heap.length * 2];
        for (int i = 0; i < heap.length; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }
}
