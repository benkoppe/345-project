package com.csc345.core;

import com.csc345.data.RowCol;
import com.csc345.data.LinkedListSet;
import com.csc345.data.List;
import com.csc345.data.functionals.BiFunction;

public class Maze {
    public static Node[] createNodes(int rows, int cols) {
        Node[] nodes = new Node[rows * cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int id = i * cols + j;
                nodes[id] = createNode(id, rows, cols);
            }
        }

        return nodes;
    }

    private static Node createNode(int id, int rows, int cols) {
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

    public static BiFunction<Integer, Integer, Double> createHeuristic(int cols) {
        return (startNodeId, endNodeId) -> {
            RowCol startRowCol = RowCol.idToRowCol(startNodeId, cols);
            RowCol endRowCol = RowCol.idToRowCol(endNodeId, cols);

            return (double) (Math.abs(startRowCol.getRow() - endRowCol.getRow()) + Math.abs(startRowCol.getCol() - endRowCol.getCol()));
        };
    }

    public static void printMaze(Node[] nodes, int cols) {
        int rows = nodes.length / cols;

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

    public static void printMazeSolution(List<Integer> path, Node[] nodes, int cols) {
        int rows = nodes.length / cols;

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
                boolean isSolution = path.contains(id);

                if (current.connections.contains(id + cols)) {
                    if (isSolution)
                        System.out.print("+");
                    else
                        System.out.print(" ");
                } else {
                    if (isSolution)
                        System.out.print("±");
                    else
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