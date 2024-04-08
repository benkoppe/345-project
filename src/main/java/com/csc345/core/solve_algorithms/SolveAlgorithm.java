package com.csc345.core.solve_algorithms;

import com.csc345.core.Algorithm;
import com.csc345.core.Node;
import com.csc345.core.State;

import com.csc345.data.List;

public abstract class SolveAlgorithm extends Algorithm {

    protected int startId;
    protected int endId;
    protected List<Integer> path = new List<>();

    public SolveAlgorithm(Node[] nodes, int startId, int endId) {
        super(nodes);

        this.startId = startId;
        this.endId = endId;

        for (int i = 0; i < nodes.length; i++) {
            changeState(i, State.VISITED);
        }
    }

    public List<Integer> getPath() {
        return path;
    }

    public int getStartId() {
        return startId;
    }

    public int getEndId() {
        return endId;
    }
}
