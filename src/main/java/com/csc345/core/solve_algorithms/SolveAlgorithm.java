package com.csc345.core.solve_algorithms;


import com.csc345.core.Algorithm;
import com.csc345.core.Node;
import com.csc345.core.State;

import com.csc345.data.List;
import com.csc345.data.HashMap;

/**
 * This is an abstract class for solving algorithms like finding paths through a maze or network.
 * It sets up a general framework for any specific pathfinding algorithm, handling common tasks
 * like managing start and end points, and keeping track of the path we've taken.
 */
public abstract class SolveAlgorithm extends Algorithm {

    protected int startId;
    protected int endId;
    protected List<Integer> path = new List<>();

    /**
     * Constructor for solve algorithms. It sets up the nodes array and marks them all as visited
     * to begin with (though you might change this in your specific algorithm).
     *
     * @param nodes An array of all the nodes in the graph or maze.
     * @param startId The ID of the node where we start.
     * @param endId The ID of the node we're trying to reach.
     */
    public SolveAlgorithm(Node[] nodes, int startId, int endId) {
        super(nodes);

        this.startId = startId;
        this.endId = endId;

        for (int i = 0; i < nodes.length; i++) {
            changeState(i, State.VISITED);
        }
    }
    /**
     * Gets the path taken by the solve algorithm from start to finish.
     * 
     * @return A list of node IDs representing the path from start to end.
     */
    public List<Integer> getPath() {
        return path;
    }
    /**
     * Gets the ID of the start node.
     * 
     * @return The start node ID.
     */
    public int getStartId() {
        return startId;
    }

    /**
     * Gets the ID of the end node.
     * 
     * @return The end node ID.
     */
    public int getEndId() {
        return endId;
    }

    /**
     * Helper method to reconstruct the path from a map of 'came from' node IDs.
     * This method is static because it doesn't need access to instance variables.
     *
     * @param cameFrom A map where each key is a node ID, and its value is the node ID from which it came.
     * @param firstId The ID of the first node in the path (usually the end node in the pathfinding context).
     * @return A list representing the reconstructed path.
     */
    protected static List<Integer> reconstructPath(HashMap<Integer, Integer> cameFrom, int firstId) {
        List<Integer> path = new List<>();
        path.append(firstId);

        Integer currentId = firstId;

        do {
            currentId = cameFrom.get(currentId);
            if (currentId == null) break;
            path.append(currentId);
        } while (true);

        // reverse path
        path.reverse();

        return path;
    }
}
