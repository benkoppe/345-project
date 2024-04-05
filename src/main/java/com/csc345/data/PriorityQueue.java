package com.csc345.data;

import com.csc345.data.functionals.CompareBy;

public class PriorityQueue<T, C extends Comparable<C>> {
    private T[] heap;
    private int size;
    private CompareBy<T, C> compareBy;

    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public PriorityQueue(CompareBy<T, C> compareBy) {
        this.heap = (T[]) new Object[DEFAULT_CAPACITY];
        this.compareBy = compareBy;
    }

    public void insert(T value) {
        if (size == heap.length)
            resize();
        heap[size] = value;
        siftUp(size++);
    }

    public T peek() {
        return size == 0 ? null : heap[0];
    }

    public T poll() {
        if (size == 0)
            return null;
        T result = heap[0];
        heap[0] = heap[--size];
        siftDown(0);
        return result;
    }

    public boolean contains(T value) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(value))
                return true;
        }
        return false;
    }

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

    @SuppressWarnings("unchecked")
    private void resize() {
        T[] newHeap = (T[]) new Object[heap.length * 2];
        System.arraycopy(heap, 0, newHeap, 0, heap.length);
        heap = newHeap;
    }
}
