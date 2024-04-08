package com.csc345.core.maze_algorithms;

import com.csc345.core.AlgorithmType;
import com.csc345.core.maze_algorithms.algorithms.Backtracking;
import com.csc345.core.maze_algorithms.algorithms.Prims;
import com.csc345.core.maze_algorithms.algorithms.Wilsons;
import com.csc345.core.Node;

public enum MazeAlgorithmType implements AlgorithmType {
    BACKTRACKING("Backtracking"),
    PRIMS("Prims"),
    WILSONS("Wilsons");

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
            default:
                return null;
        }
    }

    public static MazeAlgorithmType defaultValue() {
        return BACKTRACKING;
    }
}
