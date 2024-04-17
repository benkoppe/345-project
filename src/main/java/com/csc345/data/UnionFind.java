package com.csc345.data;

/**
 * A generic disjoint set that uses the union-find algorithm.
 */
public class UnionFind<E> {
    private HashMap<E, E> parent;
    private HashMap<E, Integer> rank;
    private int size;

    /**
     * Initializes a new UnionFind with initial elements
     * 
     * @param elements initial elements to add to the set, or null
     */
    public UnionFind(LinkedListSet<E> elements) {
        parent = new HashMap<>();
        rank = new HashMap<>();
        size = 0;

        if (elements != null) {
            elements.forEach(element -> {
                addElement(element);
            });
        }
    }

    /**
     * Adds an element to the set as a new set. Element must be unique.
     * 
     * @param element new element
     */
    public void addElement(E element) {
        if (parent.containsKey(element)) {
            throw new IllegalArgumentException("element already exists in the set");
        }
        parent.put(element, element);
        rank.put(element, 0);
        size++;
    }

    /**
     * Finds the root of the set containing a given element.
     * Also applies path compression to flatten the structure for future elements.
     * 
     * @param element
     * @return
     */
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

    /**
     * Creates a union between the sets containing two given elements.
     * 
     * @param element1 first element
     * @param element2 second element
     */
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

    /**
     * Returns whether two elements are in the same set.
     * 
     * @param element1 first element
     * @param element2 second element
     * @return
     */
    public boolean inSameSet(E element1, E element2) {
        return find(element1).equals(find(element2));
    }

    /**
     * Returns the number of sets in the structure.
     * 
     * @return the number of sets
     */
    public int numberOfSets() {
        assert size >= 1 && size <= parent.keySet().size();
        return size;
    }
    
    /**
     * Returns the number of elements in the structure.
     * 
     * @return the size of the structure
     */
    public int size() {
        return parent.size();
    }

    /**
     * Resets the structure, removing all unions.
     */
    public void reset() {
        parent.keySet().forEach(element -> {
            parent.put(element, element);
            rank.put(element, 0);
        });
        size = parent.size();
    }
}
