package core;

import data.RowCol;
import data.LinkedListSet;

public class Maze {
    public Node[] nodes;

    public Maze(int rows, int cols) {
        this.nodes = createNodes(rows, cols);
    }
    
    private Node[] createNodes(int rows, int cols) {
        Node[] nodes = new Node[rows * cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int id = i * cols + j;
                nodes[id] = createNode(id, rows, cols);
            }
        }

        return nodes;
    }

    private Node createNode(int id, int rows, int cols) {
        LinkedListSet<Integer> neighbors = new LinkedListSet<>();

        RowCol rowCol = RowCol.idToRowCol(id, cols);

        // add row neighbors
        if (rowCol.getRow() > 0) {
            // add the node above
            neighbors.add(id - cols);
        }
        if (rowCol.getRow() < rows - 1) {
            // add the node below
            neighbors.add(id + cols);
        }

        // add col neighbors
        if (rowCol.getCol() > 0) {
            // add the node to the left
            neighbors.add(id - 1);
        }
        if (rowCol.getCol() < cols - 1) {
            // add the node to the right
            neighbors.add(id + 1);
        }

        return new Node(id, neighbors);
    }
}