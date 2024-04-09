package com.csc345.data;

import com.csc345.data.HashMap;
import com.csc345.data.LinkedListSet;

public class UnionFind<E> {
    private HashMap<E, E> parent;
    private HashMap<E, Integer> rank;
    private int size;

    public UnionFind(LinkedListSet<E> elements) {
        parent = new HashMap<>();
        rank = new HashMap<>();
        size = 0;

        elements.forEach(element -> {
            addElement(element);
        });
    }

    public void addElement(E element) {
        if (parent.containsKey(element)) {
            throw new IllegalArgumentException("element already exists in the set");
        }
        parent.put(element, element);
        rank.put(element, 0);
        size++;
    }

    public E find(E element) {
        if (!parent.containsKey(element)) {
            throw new IllegalArgumentException("element does not exist in the set");
        }

        E current = element;
        while (true) {
            E parentVal = parent.get(current);
            if (parentVal.equals(current)) {
                break;
            }
            current = parentVal;
        }

        E root = current;
        current = element;

        while (!current.equals(root)) {
            E parentVal = parent.get(current);
            parent.put(current, root);
            current = parentVal;
        }

        return root;
    }

    public void union(E element1, E element2) {
        if (!parent.containsKey(element1) || !parent.containsKey(element2)) {
            throw new IllegalArgumentException("element does not exist in the set");
        }

        E parent1 = find(element1);
        E parent2 = find(element2);

        // check if already in the same set
        if (parent1.equals(parent2)) {
            return;
        }

        int rank1 = rank.get(parent1);
        int rank2 = rank.get(parent2);
        if (rank1 > rank2) {
            parent.put(parent2, parent1);
        } else if (rank1 < rank2) {
            parent.put(parent1, parent2);
        } else {
            parent.put(parent2, parent1);
            rank.put(parent1, rank1 + 1);
        }
        size--;
    }

    public boolean inSameSet(E element1, E element2) {
        return find(element1).equals(find(element2));
    }

    public int numberOfSets() {
        assert size >= 1 && size <= parent.keySet().size();
        return size;
    }
    
    public int size() {
        return parent.size();
    }

    public void reset() {
        parent.keySet().forEach(element -> {
            parent.put(element, element);
            rank.put(element, 0);
        });
        size = parent.size();
    }
}
