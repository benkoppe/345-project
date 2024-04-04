package data;

public class List<E> {
    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    
    @SuppressWarnings("unchecked")
    public List() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public void add(E e) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = e;
    }

    public E get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return elements[index];
    }

    public void reverse() {
        for (int i = 0; i < size / 2; i++) {
            E temp = elements[i];
            elements[i] = elements[size - i - 1];
            elements[size - i - 1] = temp;
        }
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        int newSize = elements.length * 2;
        E[] newElements = (E[]) new Object[newSize];

        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }

        elements = newElements;
    }

    public int size() {
        return size;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.print(elements[i] + " ");
        }
        System.out.println();
    }
}
