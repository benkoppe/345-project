package com.csc345.core.solve_algorithms;

import com.csc345.core.Node;
import com.csc345.core.AlgorithmType;
import com.csc345.core.solve_algorithms.algorithms.AStar;
import com.csc345.core.solve_algorithms.algorithms.Breadth;

import com.csc345.data.functionals.BiFunction;

public enum SolveAlgorithmType implements AlgorithmType {
    ASTAR("A-Star"),
    DIJKSTRA("Dijkstra"),
    BREADTH("BFS");

    private final String name;

    SolveAlgorithmType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public SolveAlgorithm getAlgorithm(Node[] nodes, int startId, int endId, BiFunction<Integer, Integer, Double> heuristicFunction) {
        switch (this) {
            case ASTAR:
                return new AStar(nodes, startId, endId, heuristicFunction);
            case DIJKSTRA:
                return new AStar(nodes, startId, endId, (a, b) -> 0.0);
            case BREADTH:
                return new Breadth(nodes, startId, endId);
            default:
                return null;
        }
    }

    public static SolveAlgorithmType defaultValue() {
        return ASTAR;
    }
}
