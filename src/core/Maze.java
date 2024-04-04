package core;

import data.RowCol;
import data.LinkedListSet;

public class Maze {
    public Node[] nodes;
    private int rows;
    private int cols;

    public Maze(int rows, int cols) {
        this.nodes = createNodes(rows, cols);
    }
    
    private Node[] createNodes(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
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

    public int getRows() {
        return this.rows;
    }

    public int getCols() {
        return this.cols;
    }

    public void printMaze() {
        System.out.println();
        // run through the first row, printing the top walls
        for (int i = 0; i < cols; i++) {
            System.out.print("__");
        }
        // run through the other rows, printing the side and bottom walls
        for (int i = 0; i < rows; i++) {
            System.out.println();
            System.out.print("|");
            for (int j = 0; j < cols; j++) {
                int id = i * cols + j;
                Node current = nodes[id];

                if (current.connections.contains(id + cols)) {
                    System.out.print(" ");
                } else {
                    System.out.print("_");
                }

                if (current.connections.contains(id + 1)) {
                    System.out.print(" ");
                } else {
                    System.out.print("|");
                }
            }
        }
    }
}