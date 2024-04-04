package core.maze_algorithms.algorithms;

import data.SimpleArrayDeque;
import data.LinkedListSet;

import core.Node;
import core.State;
import core.maze_algorithms.MazeAlgorithm;

public class Backtracking extends MazeAlgorithm {

    // private exploreStack
    private int startId;
    private SimpleArrayDeque exploreStack;

    public Backtracking(Node[] nodes) {
        super(nodes);
        this.exploreStack = new SimpleArrayDeque(nodes.length);
        this.startId = (int) (Math.random() * nodes.length);
        exploreStack.addFirst(startId);
        changeState(startId, State.VISITING);
    }

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

    private Integer findUnvisitedNeighbor(Node node) {
        LinkedListSet<Integer> unvisitedNeighbors = node.neighbors.filter(data -> states[data] == State.UNVISITED);
        return unvisitedNeighbors.randomElement();
    }
    
}
