package com.csc345.core.solve_algorithms;

import com.csc345.core.AlgorithmType;

public enum SolveAlgorithmType implements AlgorithmType {
    AStar("A*");

    private final String name;

    SolveAlgorithmType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SolveAlgorithmType defaultValue() {
        return AStar;
    }
}
