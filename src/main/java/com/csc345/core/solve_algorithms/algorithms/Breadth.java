package com.csc345.core.solve_algorithms.algorithms;

import com.csc345.core.Node;
import com.csc345.core.State;
import com.csc345.core.solve_algorithms.SolveAlgorithm;

import com.csc345.data.ArrayDeque;
import com.csc345.data.HashMap;

public class Breadth extends SolveAlgorithm {

    private ArrayDeque queue;
    private HashMap<Integer, Integer> cameFrom;
    

    public Breadth(Node[] nodes, int startId, int endId) {
        super(nodes, startId, endId);

        queue = new ArrayDeque(nodes.length);
        cameFrom = new HashMap<>();

        changeState(startId, State.VISITING);
        queue.addLast(startId);
    }

    @Override
    protected boolean loopOnceInternal() {
        ArrayDeque nextQueue = new ArrayDeque(queue.getSize());

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
