package com.csc345.core.maze_algorithms.algorithms;

import com.csc345.core.Node;
import com.csc345.core.State;
import com.csc345.core.maze_algorithms.MazeAlgorithm;
import com.csc345.data.LinkedListSet;
import com.csc345.data.ArrayDeque;

public class Wilsons extends MazeAlgorithm {

    LinkedListSet<Integer> visitedNodes;
    LinkedListSet<Integer> unvisitedNodes;
    ArrayDeque currentPath;

    public Wilsons(Node[] nodes) {
        super(nodes);

        visitedNodes = new LinkedListSet<>();
        unvisitedNodes = new LinkedListSet<>();
        currentPath = new ArrayDeque(nodes.length);

        for (Node node : nodes) {
            unvisitedNodes.add(node.id);
        }

        int startId = (int) (Math.random() * nodes.length);

        unvisitedNodes.remove(startId);
        visitedNodes.add(startId);

        changeState(startId, State.VISITED);
    }

    @Override
    protected boolean loopOnceInternal() {
        if (currentPath.isEmpty()) {
            startNewPath();
            return false;
        }

        Node current = nodes[currentPath.getLast()];

        // get a random neighbor, not including the preceding node
        LinkedListSet<Integer> neighborsWithoutPreceding = current.neighbors.filter(neighborId -> {
            if (currentPath.getSize() == 1) {
                return true;
            }
            int precedingId = currentPath.getRelativeToEnd(1);
            return neighborId != precedingId;
        });
        int randomId = neighborsWithoutPreceding.randomElement();

        if (visitedNodes.contains(randomId)) {
            current.connect(nodes[randomId]);
            transferPathToMaze();
        } else if (currentPath.contains(randomId)) {
            deleteLoop(randomId);
        } else {
            currentPath.addLast(randomId);
            current.connect(nodes[randomId]);
            changeState(randomId, State.VISITING);
        }

        return unvisitedNodes.isEmpty();
    }

    private void startNewPath() {
        int randomId = unvisitedNodes.randomElement();
        currentPath.addLast(randomId);
        changeState(randomId, State.VISITING);
    }

    private void transferPathToMaze() {
        currentPath.forEach(neighborId -> {
            visitedNodes.add(neighborId);
            unvisitedNodes.remove(neighborId);
            changeState(neighborId, State.VISITED);
        });
        currentPath.clear();
    }

    private void deleteLoop(int endId) {
        int prevId;
        int currId;

        do {
            prevId = currentPath.removeLast();
            currId = currentPath.getLast();
            changeState(prevId, State.UNVISITED);
            nodes[currId].disconnect(nodes[prevId]);
        } while (currId != endId);
    }
    
}
