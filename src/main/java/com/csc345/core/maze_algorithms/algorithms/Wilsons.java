package com.csc345.core.maze_algorithms.algorithms;

import com.csc345.core.Node;
import com.csc345.core.State;
import com.csc345.core.maze_algorithms.MazeAlgorithm;
import com.csc345.data.LinkedListSet;
import com.csc345.data.ArrayDeque;

/**
 * This class implements Wilson's algorithm for maze generation. The algorithm ensures a uniform spanning tree
 * by using a loop-erased random walk. It starts with a set of unvisited nodes and gradually adds them to the maze
 * using a path that eventually connects back to the already visited nodes.
 */
public class Wilsons extends MazeAlgorithm {

    LinkedListSet<Integer> visitedNodes;
    LinkedListSet<Integer> unvisitedNodes;
    ArrayDeque currentPath;

    /**
     * it initializes the maze generation with a random starting node marked as visited.
     * All other nodes are initially unvisited.
     *
     * @param nodes Array of nodes used for generating the maze.
     */
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

    /**
     * Executes one iteration of the maze generation. It either extends the current path,
     * closes a loop, or finishes the path if it connects to the visited area.
     *
     * @return true if all nodes are connected (i.e., there are no unvisited nodes left), false otherwise.
     */
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

    /**
     * Starts a new random walk from a random unvisited node.
     */
    private void startNewPath() {
        int randomId = unvisitedNodes.randomElement();
        currentPath.addLast(randomId);
        changeState(randomId, State.VISITING);
    }

    /**
     * Transfers the current path to the maze, marking all nodes in the path as visited
     * and removing them from the unvisited set.
     */
    private void transferPathToMaze() {
        currentPath.forEach(neighborId -> {
            visitedNodes.add(neighborId);
            unvisitedNodes.remove(neighborId);
            changeState(neighborId, State.VISITED);
        });
        currentPath.clear();
    }

    /**
     * Removes a loop in the current path if the current node is already in the path.
     * This is necessary to maintain the acyclic property of the spanning tree.
     *
     * @param endId The ID of the node where the loop closes.
     */
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
