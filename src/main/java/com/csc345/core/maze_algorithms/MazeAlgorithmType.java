package com.csc345.core.maze_algorithms;

import com.csc345.core.AlgorithmType;

public enum MazeAlgorithmType implements AlgorithmType {
    Backtracking("Backtracking");

    private final String name;

    MazeAlgorithmType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MazeAlgorithmType defaultValue() {
        return Backtracking;
    }
}
