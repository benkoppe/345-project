package com.csc345.core.solve_algorithms.algorithms;

import com.csc345.core.Node;
import com.csc345.core.State;
import com.csc345.core.solve_algorithms.SolveAlgorithm;
import com.csc345.data.LinkedListSet;

public class Tremaux extends SolveAlgorithm {
    

    public Tremaux(Node[] nodes, int startId, int endId) {
        super(nodes, startId, endId);

        path.add(startId);
        changeState(startId, State.VISITING);
    }

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
