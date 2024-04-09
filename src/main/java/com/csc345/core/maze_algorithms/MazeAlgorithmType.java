package com.csc345.core.maze_algorithms;

import com.csc345.core.AlgorithmType;
import com.csc345.core.maze_algorithms.algorithms.*;
import com.csc345.core.Node;

public enum MazeAlgorithmType implements AlgorithmType {
    BACKTRACKING("Backtracking"),
    PRIMS("Prims"),
    WILSONS("Wilsons"),
    KRUSKALS("Kruskals");

    private final String name;

    MazeAlgorithmType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public MazeAlgorithm getAlgorithm(Node[] nodes) {
        switch (this) {
            case BACKTRACKING:
                return new Backtracking(nodes);
            case PRIMS:
                return new Prims(nodes);
            case WILSONS:
                return new Wilsons(nodes);
            case KRUSKALS:
                return new Kruskals(nodes);
            default:
                return null;
        }
    }

    public static MazeAlgorithmType defaultValue() {
        return BACKTRACKING;
    }
}
