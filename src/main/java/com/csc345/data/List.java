package com.csc345.data;

import com.csc345.data.functionals.Consumer;

/**
 * A generic list implementation..
 */
public class List<E> {
    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    
    /**
     * Initialize a new List with size 10.
     */
    @SuppressWarnings("unchecked")
    public List() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * Appends a new element to the end of the list.
     * 
     * @param e the element to add
     */
    public void append(E e) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = e;
    }

    /**
     * Gets the element at a given index of the list.
     * 
     * @param index the index of the desired element
     * @return the element at the given index
     */
    public E get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return elements[index];
    }

    /**
     * Gets the value at the end of the list.
     * 
     * @return the last value in the list
     */
    public E getLast() {
        return get(size - 1);
    }

    /**
     * Removes the value of the list at a given index.
     * 
     * @param index the index at which to remove the value
     * @return the removed value
     */
    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        E removedElement = elements[index];
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        size--;
        return removedElement;
    }

    /**
     * Removes the value at the end of the list.
     * 
     * @return the removed value
     */
    public E removeLast() {
        return remove(size - 1);
    }

    /**
     * Returns whether the list contains a given element.
     * 
     * @param e requested element
     * @return whether the element was found in the list
     */
    public boolean contains(E e) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reverses the order of the list.
     */
    public void reverse() {
        for (int i = 0; i < size / 2; i++) {
            E temp = elements[i];
            elements[i] = elements[size - i - 1];
            elements[size - i - 1] = temp;
        }
    }

    /**
     * Randomly shuffles the order of the list.
     */
    public void shuffle() {
        for (int i = 0; i < size; i++) {
            int randomIndex = (int) (Math.random() * size);
            E temp = elements[i];
            elements[i] = elements[randomIndex];
            elements[randomIndex] = temp;
        }
    }

    /**
     * Ensure that the list has the capacity to hold its element, and resize otherwise.
     */
    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        int newSize = elements.length * 2;
        E[] newElements = (E[]) new Object[newSize];

        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }

        elements = newElements;
    }

    /**
     * Returns the size of the list.
     * 
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Returns whether the list is empty.
     * 
     * @return whether the list is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Prints all values in the list.
     */
    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.print(elements[i] + " ");
        }
        System.out.println();
    }

    /**
     * Runs a action consumer for all elements in the list.
     * 
     * @param action the consumer to run for all values
     */
    public void forEach(Consumer<E> action) {
        for (int i = 0; i < size; i++) {
            action.accept(elements[i]);
        }
    }

    /**
     * Returns a sublist of the list's elements between two indices.
     * The start index is inclusive, the end is exclusive.
     * 
     * @param fromIndex start index (inclusive)
     * @param toIndex end index (exclusive)
     * @return the desired sublist
     */
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }

        List<E> subList = new List<>();
        for (int i = fromIndex; i < toIndex; i++) {
            subList.append(elements[i]);
        }

        return subList;
    }
}
