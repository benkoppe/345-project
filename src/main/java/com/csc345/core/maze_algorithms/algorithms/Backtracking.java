package com.csc345.core.maze_algorithms.algorithms;

import com.csc345.data.ArrayDeque;
import com.csc345.data.LinkedListSet;

import com.csc345.core.Node;
import com.csc345.core.State;
import com.csc345.core.maze_algorithms.MazeAlgorithm;

/**
 * This implements the backtracking algorithm to generate mazes. This class extends the MazeAlgorithm
 * abstract class, providing a specific implementation for maze generation using a stack-based
 * backtracking approach.
 */
public class Backtracking extends MazeAlgorithm {

    private int startId;
    private ArrayDeque exploreStack;

    /**
     * Here we construct a Backtracking maze generator with a specified array of nodes.
     * it initializes an exploration stack and randomly selects a starting node, marking it
     *as visiting.
     *
     * @param nodes Array of nodes used in the maze generation. Each node is initially marked as unvisited.
     */
    public Backtracking(Node[] nodes) {
        super(nodes);
        this.exploreStack = new ArrayDeque(nodes.length);
        this.startId = (int) (Math.random() * nodes.length);
        exploreStack.addFirst(startId);
        changeState(startId, State.VISITING);
    }
    /**
     * It xecutes one iteration of the backtracking algorithm. This method either extends the path by visiting
     * a new unvisited neighbor or backtracks by removing the last node from the stack and marking it as visited.
     *
     * @return true if there are no more nodes to explore otherwise it returns false.
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
     * it finds a random unvisited neighbor of a given node. This is used to extend the path in the maze.
     *
     * @param node The node for which to find an unvisited neighbor.
     * @return The index of an unvisited neighbor node, or null if all neighbors are visited.
     */
    private Integer findUnvisitedNeighbor(Node node) {
        LinkedListSet<Integer> unvisitedNeighbors = node.neighbors.filter(data -> states[data] == State.UNVISITED);
        return unvisitedNeighbors.randomElement();
    }
    
}
