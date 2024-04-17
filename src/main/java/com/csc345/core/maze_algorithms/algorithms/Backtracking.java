package com.csc345.core.maze_algorithms.algorithms;

import com.csc345.data.ArrayDeque;
import com.csc345.data.LinkedListSet;

import com.csc345.core.Node;
import com.csc345.core.State;
import com.csc345.core.maze_algorithms.MazeAlgorithm;

/**
 * Backtracking maze generation algorithm.
 * Works by starting at a random node and exploring the maze, making connections when it moves.
 * When it arrives at a Node with no neighbors, it backtracks to the last Node with unvisited neighbors.
 */
public class Backtracking extends MazeAlgorithm {

    private int startId;
    private ArrayDeque<Integer> exploreStack;

    /**
     * Initializes a new Backtracking algorithm on a maze, selecting a random start node.
     * 
     * @param nodes the maze nodes to generate with
     */
    public Backtracking(Node[] nodes) {
        super(nodes);
        this.exploreStack = new ArrayDeque<>(nodes.length);
        this.startId = (int) (Math.random() * nodes.length);
        exploreStack.addFirst(startId);
        changeState(startId, State.VISITING);
    }

    /**
     * Loops once internally, making a single movement in the maze.
     * Either moves forward to a neighbor node and connects it,
     * or backtracks to the last node.
     */
    @Override
    protected boolean loopOnceInternal() {
        Node current = nodes[exploreStack.getFirst()];

        // Find an unvisited neighbor, or null if all neighbors are visited
        Integer neighborId = findUnvisitedNeighbor(current);
        if (neighborId != null) {
            current.connect(nodes[neighborId]);
            changeState(neighborId, State.VISITING);
            exploreStack.addFirst(neighborId);
        } else {
            exploreStack.removeFirst();
            changeState(current.id, State.VISITED);
        }

        return exploreStack.isEmpty();
    }

    /**
     * Finds a random unvisited neighbor of a given node.
     * If all neighbors are visited, returns null.
     * 
     * @param node the node to find an unvisited neighbor of
     * @return the id of an unvisited neighbor, or null if none exist
     */
    private Integer findUnvisitedNeighbor(Node node) {
        LinkedListSet<Integer> unvisitedNeighbors = node.neighbors.filter(data -> states[data] == State.UNVISITED);
        return unvisitedNeighbors.randomElement();
    }
    
}
