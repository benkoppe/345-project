package com.csc345.core.solve_algorithms.algorithms;

import com.csc345.core.Node;
import com.csc345.core.State;
import com.csc345.core.solve_algorithms.SolveAlgorithm;

import com.csc345.data.ArrayDeque;
import com.csc345.data.HashMap;

/**
 * This class implements the Breadth-First Search (BFS) algorithm to find the shortest path in a graph.
 * It's like exploring a maze by checking all the rooms (nodes) level by level, starting from the entrance
 * (start node) until we find the exit (end node).
 */
public class Breadth extends SolveAlgorithm {

    private ArrayDeque<Integer> queue;
    private HashMap<Integer, Integer> cameFrom;
    
    /**
     * Sets up the BFS with a start and an end node. It initializes the queue and marks the start node
     * as visiting to kick off the search.
     *
     * @param nodes An array of all nodes in the graph.
     * @param startId The ID of the start node.
     * @param endId The ID of the end node.
     */
    public Breadth(Node[] nodes, int startId, int endId) {
        super(nodes, startId, endId);

        queue = new ArrayDeque<>(nodes.length);
        cameFrom = new HashMap<>();

        changeState(startId, State.VISITING);
        queue.addLast(startId);
    }
    /**
     * Runs one cycle of the BFS algorithm. It processes each node in the queue by checking its neighbors,
     * and if they haven't been visited yet, adds them to the queue for the next cycle.
     *
     * @return true if the end node is reached (path found), false if more nodes need to be processed.
     */
    @Override
    protected boolean loopOnceInternal() {
        ArrayDeque<Integer> nextQueue = new ArrayDeque<>(queue.getSize());

        do {
            int currentId = queue.removeFirst();

            if (currentId == endId) {
                this.path = reconstructPath(cameFrom, endId);
                return true;
            }

            Node current = nodes[currentId];
            current.connections.forEach(connectionId -> {
                if (states[connectionId] == State.VISITED) {
                    cameFrom.put(connectionId, currentId);
                    changeState(connectionId, State.VISITING);
                    nextQueue.addLast(connectionId);
                }
            });
        } while (!queue.isEmpty());

        queue = nextQueue;
        return false;
    }
}
