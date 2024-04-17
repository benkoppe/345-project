package com.csc345.core.solve_algorithms;

import com.csc345.core.Node;
import com.csc345.core.AlgorithmType;
import com.csc345.core.solve_algorithms.algorithms.*;

import com.csc345.data.functionals.BiFunction;

/**
 * Enum for all SolveAlgorithm subclasses, called Types.
 */
public enum SolveAlgorithmType implements AlgorithmType {
    ASTAR("A-Star"),
    DIJKSTRA("Dijkstra"),
    BREADTH("BFS"),
    TREMAUX("Tremaux");

    private final String name;

    /**
     * Constructor for SolveAlgorithmType. Requires a name.
     * 
     * @param name name of the algorithm
     */
    SolveAlgorithmType(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the algorithm.
     * 
     * @return the name of the algorithm
     */
    public String getName() {
        return name;
    }

    /**
     * Converts the enum to a string.
     * 
     * @return the name of the algorithm
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Converts the enum to an initialized SolveAlgorithm
     * 
     * @param nodes the maze nodes to solve
     * @param startId the start node id
     * @param endId the end node id
     * @param heuristicFunction the heuristic function (for A-Star)
     * @return the initialized SolveAlgorithm
     */
    public SolveAlgorithm getAlgorithm(Node[] nodes, int startId, int endId, BiFunction<Integer, Integer, Double> heuristicFunction) {
        switch (this) {
            case ASTAR:
                return new AStar(nodes, startId, endId, heuristicFunction);
            case DIJKSTRA:
                return new AStar(nodes, startId, endId, (a, b) -> 0.0);
            case BREADTH:
                return new Breadth(nodes, startId, endId);
            case TREMAUX:
                return new Tremaux(nodes, startId, endId);
            default:
                return null;
        }
    }

    /**
     * Returns the default SolveAlgorithmType.
     * 
     * @return the default SolveAlgorithmType
     */
    public static SolveAlgorithmType defaultValue() {
        return ASTAR;
    }
}
