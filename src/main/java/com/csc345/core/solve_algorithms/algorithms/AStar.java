package com.csc345.core.solve_algorithms.algorithms;

import com.csc345.core.solve_algorithms.SolveAlgorithm;
import com.csc345.data.PriorityQueue;
import com.csc345.data.HashMap;
import com.csc345.data.functionals.BiFunction;
import com.csc345.core.Node;
import com.csc345.core.State;

/**
 * This class uses the A* algorithm to find the shortest path from a start node to an end node.
 * It’s pretty cool because it uses a heuristic to guess how close we are to the goal, making it
 * smarter and faster than some other pathfinding algorithms.
 */
public class AStar extends SolveAlgorithm {
    
    private HashMap<Integer, Integer> cameFrom = new HashMap<>();
    private HashMap<Integer, Double> gScores = new HashMap<>();
    private HashMap<Integer, Double> fScores = new HashMap<>();

    private PriorityQueue<Integer, Double> openQueue;
    private BiFunction<Integer, Integer, Double> heuristic;
    
    /**
     * Initializese a new A* algorithm with a specific start node, end node, and heuristic.
     *
     * @param nodes An array of all nodes in the graph.
     * @param startId The ID of the start node.
     * @param endId The ID of the end node.
     * @param heuristic A function that estimates the cost from any node to the end node.
     */
    public AStar(Node[] nodes, int startId, int endId, BiFunction<Integer, Integer, Double> heuristic) {
        super(nodes, startId, endId);
        this.heuristic = heuristic;

        this.openQueue = new PriorityQueue<>(id -> fScores.getOrDefault(id, Double.POSITIVE_INFINITY));

        openQueue.insert(startId);
        gScores.put(startId, 0.0);
        fScores.put(startId, heuristic.apply(startId, endId));
        changeState(startId, State.VISITING);
    }
    /**
     * Runs one iteration of the A* algorithm. It picks the node with the lowest estimated cost,
     * checks if it’s the end node, and if not, it processes its neighbors and updates their scores.
     *
     * @return true if the end node is reached or the open queue is empty (no path found), false otherwise.
     */
    @Override
    protected boolean loopOnceInternal() {
        if (openQueue.isEmpty()) {
            // no path found
            System.out.println("No path found");
            return true;
        }

        int currentId = openQueue.poll();

        if (currentId == endId) {
            // end is found
            this.path = reconstructPath(cameFrom, endId);
            return true;
        }

        Node current = nodes[currentId];

        current.connections.forEach(connectionId -> {
            double tentativeGScore = gScores.get(currentId) + 1; // 1 is the distance between two nodes

            if (!gScores.containsKey(connectionId) || tentativeGScore < gScores.get(connectionId)) {
                cameFrom.put(connectionId, currentId);
                gScores.put(connectionId, tentativeGScore);
                double fScore = tentativeGScore + heuristic.apply(connectionId, endId);
                fScores.put(connectionId, fScore);

                if (!openQueue.contains(connectionId)) {
                    openQueue.insert(connectionId);
                    changeState(connectionId, State.VISITING);
                }
            }
        });

        return false;
    }
}
