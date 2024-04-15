package com.csc345.core.solve_algorithms.algorithms;

import com.csc345.core.Node;
import com.csc345.core.State;
import com.csc345.core.solve_algorithms.SolveAlgorithm;
import com.csc345.data.LinkedListSet;

/**
 * This class implements Trémaux's algorithm, a cool old-school maze-solving method.
 * It works kind of like leaving breadcrumbs: you mark where you've been so you don't
 * go down the same path twice unless you need to backtrack.
 */
public class Tremaux extends SolveAlgorithm {
    
    /**
     * Sets up the maze with the starting point marked as visiting. This is where
     * the journey through the maze begins.
     *
     * @param nodes An array of all nodes in the maze.
     * @param startId The ID of the node where we start.
     * @param endId The ID of the node where we hope to end up.
     */

    public Tremaux(Node[] nodes, int startId, int endId) {
        super(nodes, startId, endId);

        path.add(startId);
        changeState(startId, State.VISITING);
    }
    /**
     * Does one step of Trémaux's algorithm. It checks for unvisited paths from the
     * current position and chooses one to move forward. If no unvisited paths are left,
     * it backtracks to a previous node.
     *
     * @return true if we reach the end node, false if the journey must continue.
     */
    @Override
    protected boolean loopOnceInternal() {
        int currentId = path.getLast();

        LinkedListSet<Integer> unvisitedConnections = nodes[currentId].connections.filter(connectionId -> {
            if (path.size() > 1) {
                int previousId = path.get(path.size() - 2);
                return (states[connectionId] == State.VISITED) && (connectionId != previousId);
            } else {
                return states[connectionId] == State.VISITED;
            }
        });

        if (!unvisitedConnections.isEmpty()) {
            int randomId = unvisitedConnections.randomElement();
            path.add(randomId);
            changeState(randomId, State.VISITING);
            return randomId == endId;
        } else {
            path.removeLast();
            return false;
        }
    }
}
