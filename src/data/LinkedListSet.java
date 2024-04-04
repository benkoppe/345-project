package data;

import data.functionals.Condition;
import data.functionals.Runnable;

public class LinkedListSet<T> {
    private Node head;
    private int size;

    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public LinkedListSet() {
        this.head = null;
        this.size = 0;
    }

    // Add a new element to the set if it's not already present
    public void add(T data) {
        if (!contains(data)) {
            Node newNode = new Node(data);
            newNode.next = head;
            head = newNode;
            size++;
        }
    }

    // Checks if an element exists in the set
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

    // Removes an element from the set
    public void remove(T data) {
        if (head == null)
            return; // The list is empty

        // If the node is to be removed from the head
        if (head.data.equals(data)) {
            head = head.next;
            size--;
            return;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next;
                size--;
                return; // Data found and removed
            }
            current = current.next;
        }
    }

    // Print all elements in the set
    public void printSet() {
        Node current = head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }

    public void forEach(Runnable<T> action) {
        Node current = head;
        while (current != null) {
            action.run(current.data);
            current = current.next;
        }
    }

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
}