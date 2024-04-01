package core;

import java.util.ArrayList;
import java.util.HashSet;

public class Maze {
    public ArrayList<Node> nodes;

    public Maze(int rows, int cols) {
        this.nodes = createNodes(rows, cols);
    }
    
    private ArrayList<Node> createNodes(int rows, int cols) {
        ArrayList<Node> nodes = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int id = i * cols + j;
                nodes.add(createNode(id, rows, cols));
            }
        }

        return nodes;
    }

    private Node createNode(int id, int rows, int cols) {
        HashSet<Integer> neighbors = new HashSet<>();

        RowCol rowCol = idToRowCol(id, cols);

        // add row neighbors
        if (rowCol.row > 0) {
            // add the node above
            neighbors.add(id - cols);
        }
        if (rowCol.row < rows - 1) {
            // add the node below
            neighbors.add(id + cols);
        }

        // add col neighbors
        if (rowCol.col > 0) {
            // add the node to the left
            neighbors.add(id - 1);
        }
        if (rowCol.col < cols - 1) {
            // add the node to the right
            neighbors.add(id + 1);
        }

        return new Node(id, neighbors);
    }

    // converts an id to a row-column pair
    private RowCol idToRowCol(int id, int cols) {
        return new RowCol(id / cols, id % cols);
    }

    // represenets a row-column pair
    private class RowCol {
        int row;
        int col;

        public RowCol(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}