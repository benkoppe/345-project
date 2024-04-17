package com.csc345.core;

import com.csc345.data.LinkedListSet;
import com.csc345.data.List;
import com.csc345.data.functionals.BiFunction;
import com.csc345.data.types.RowCol;

/**
 * Class of static maze-related methods.
 */
public class Maze {

    /**
     * Creates a maze, represented as an array of nodes.
     * Each node stores its neighbors, and no connections are made.
     * 
     * @param rows number of rows in the maze
     * @param cols number of columns in the maze
     * @return array of nodes representing the maze
     */
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

    /**
     * Create a new Node instance at a given id in a maze
     * 
     * @param id id of the node
     * @param rows number of rows in the maze
     * @param cols number of columns in the maze
     * @return new Node instance
     */
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

    /**
     * Creates a heuristic function for a maze of given size, based on the Manhattan distance.
     * Used in solving (A-Star) methods
     * 
     * @param cols number of columns in the maze
     * @return heuristic bifunction, which takes in the start and end node ids and returns the heuristic value
     */
    public static BiFunction<Integer, Integer, Double> createHeuristic(int cols) {
        return (startNodeId, endNodeId) -> {
            RowCol startRowCol = RowCol.idToRowCol(startNodeId, cols);
            RowCol endRowCol = RowCol.idToRowCol(endNodeId, cols);

            return (double) (Math.abs(startRowCol.getRow() - endRowCol.getRow()) + Math.abs(startRowCol.getCol() - endRowCol.getCol()));
        };
    }

    /**
     * Prints a maze that's represented as an array of nodes to the console.
     * 
     * @param nodes array of nodes representing the maze
     * @param cols number of columns in the maze
     */
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

    /**
     * Prints a maze with its solution to the console.
     * 
     * @param path list of node ids in the solution path
     * @param nodes array of nodes representing the maze
     * @param cols number of columns in the maze
     */
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