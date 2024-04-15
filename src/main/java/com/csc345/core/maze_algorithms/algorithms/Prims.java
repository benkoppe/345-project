package com.csc345.core.maze_algorithms.algorithms;

import com.csc345.core.Node;
import com.csc345.core.State;
import com.csc345.core.maze_algorithms.MazeAlgorithm;
import com.csc345.data.LinkedListSet;

/**
 * This class uses Prim's algorithm to generate a maze. Starting from a random node,
 * it expands the maze by adding one frontier node at a time. It's a bit like growing a tree
 * where each new branch is chosen randomly from the edges that reach out into unexplored areas.
 */
public class Prims extends MazeAlgorithm {

    private LinkedListSet<Integer> fronteirs;
    /**
     * Constructor that starts the maze generation from a random node and prepares the frontier set.
     *
     * @param nodes Array of nodes in the maze. These are the "rooms" or "cells" of our maze.
     */

    public Prims(Node[] nodes) {
        super(nodes);

        int startId = (int) (Math.random() * nodes.length);
        changeState(startId, State.VISITED);

        fronteirs = new LinkedListSet<>();
        nodes[startId].neighbors.forEach(neighborId -> {
            fronteirs.add(neighborId);
            changeState(neighborId, State.VISITING);
        });
    }
    /**
     * Expands the maze by one node each time it is called. It randomly picks a frontier node,
     * connects it to the maze, and adds its unvisited neighbors to the frontiers.
     *
     * @return true if there are no more frontier nodes to process, false otherwise.
     */
    @Override
    protected boolean loopOnceInternal() {
        Node randomFronteir = nodes[fronteirs.randomElement()];

        fronteirs.remove(randomFronteir.id);
        changeState(randomFronteir.id, State.VISITED);

        // add all unvisited neighbors to the fronteirs
        LinkedListSet<Integer> unvisitedNeighbors = randomFronteir.neighbors.filter(neighborId -> {
            return ((states[neighborId] == State.UNVISITED) && (!fronteirs.contains(neighborId)));
        });
        unvisitedNeighbors.forEach(neighborId -> {
            fronteirs.add(neighborId);
            changeState(neighborId, State.VISITING);
        });

        // connect the random fronteir to a random visited neighbor cell
        LinkedListSet<Integer> visitedNeighbors = randomFronteir.neighbors.filter(neighborId -> {
            return states[neighborId] == State.VISITED;
        });
        Node randomNeighbor = nodes[visitedNeighbors.randomElement()];
        randomFronteir.connect(randomNeighbor);

        return fronteirs.isEmpty();
    }
    
}
