package com.csc345.core.maze_algorithms.algorithms;

import com.csc345.core.Node;
import com.csc345.core.State;
import com.csc345.core.maze_algorithms.MazeAlgorithm;
import com.csc345.data.LinkedListSet;

public class Prims extends MazeAlgorithm {

    private LinkedListSet<Integer> fronteirs;

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
