package com.csc345.data;

import com.csc345.data.functionals.Condition;
import com.csc345.data.functionals.Consumer;

/**
 * A generic linked-list set implementation.
 */
public class LinkedListSet<T> {
    private Node head;
    private int size;

    /**
     * A generic node.
     */
    private class Node {
        T data;
        Node next;

        /**
         * Initialize a new node with data
         * 
         * @param data the node's contained data
         */
        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * Initialize a new, empty LinkedListSet
     */
    public LinkedListSet() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Adds a new element to the set if its not already present.
     * 
     * @param data the new element to add
     */
    public void add(T data) {
        if (!contains(data)) {
            Node newNode = new Node(data);
            newNode.next = head;
            head = newNode;
            size++;
        }
    }

    /**
     * Returns whether a given element is contained within the set.
     * 
     * @param data the element to check for
     * @return whether the element could be found within the set
     */
    public boolean contains(T data) {
        Node current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Removes an element from the set.
     * 
     * @param data the element to remove from the set
     */
    public void remove(T data) {
        if (head == null) {
            return; // catch an empty list
        }

        // for removing the head
        if (head.data.equals(data)) {
            head = head.next;
            size--;
            return;
        }

        // for removing any other vlaue
        Node current = head;
        while (current.next != null) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next;
                size--;
                return; // data found and removed
            }
            current = current.next;
        }
    }

    /**
     * Print all values in the set
     */
    public void printSet() {
        Node current = head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }

    /**
     * Run a consumer for all elements in the set
     * 
     * @param action the consumer to be passed all elements
     */
    public void forEach(Consumer<T> action) {
        Node current = head;
        while (current != null) {
            action.accept(current.data);
            current = current.next;
        }
    }

    /**
     * Filter the set according to a given condition
     * 
     * @param condition the condition to apply to all elements
     * @return the filtered set
     */
    public LinkedListSet<T> filter(Condition<T> condition) {
        LinkedListSet<T> filteredSet = new LinkedListSet<>();
        Node current = head;
        while (current != null) {
            if (condition.test(current.data)) {
                filteredSet.add(current.data);
            }
            current = current.next;
        }
        return filteredSet;
    }

    /**
     * Get a random element from the set. Returns null for empty sets.
     * 
     * @return the random element
     */
    public T randomElement() {
        if (size == 0) {
            return null;
        }
        int randomIndex = (int) (Math.random() * size);
        Node current = head;
        for (int i = 0; i < randomIndex; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Returns whether the set is empty.
     * 
     * @return whether the set is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }
}