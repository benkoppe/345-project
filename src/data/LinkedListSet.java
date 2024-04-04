package data;

public class LinkedListSet<T> {
    private Node head;

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
    }

    // Add a new element to the set if it's not already present
    public void add(T data) {
        if (!contains(data)) {
            Node newNode = new Node(data);
            newNode.next = head;
            head = newNode;
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
            return;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next;
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
}