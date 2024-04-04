package core.solve_algorithms;

import core.Algorithm;
import core.Node;
import core.State;

import data.List;

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
}
