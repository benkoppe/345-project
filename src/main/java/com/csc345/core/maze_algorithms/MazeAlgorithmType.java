package com.csc345.core.maze_algorithms;

import com.csc345.core.AlgorithmType;
import com.csc345.core.maze_algorithms.algorithms.*;
import com.csc345.core.Node;

/**
 * Enum for all SolveAlgorithm subclasses, called Types.
 */
public enum MazeAlgorithmType implements AlgorithmType {
    BACKTRACKING("Backtracking"),
    PRIMS("Prims"),
    WILSONS("Wilsons"),
    KRUSKALS("Kruskals");
    
    /**
     * Constructs a MazeAlgorithmType with a given name.
     *
     * @param name The name of the algorithm.
     */
    private final String name;

    MazeAlgorithmType(String name) {
        this.name = name;
    }
    
    /**
     * Returns the name of the algorithm type.
     *
     * @return The name of the algorithm.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * This method picks the maze algorithm based on the enum type and creates an instance of it.
     * It uses the provided array of nodes to set up the algorithm, which is cool because it means
     * we can use the same method to initialize any maze algorithm we want just by changing the enum value.
     *
     * @param nodes An array containing the nodes that the chosen algorithm will use to generate the maze.
     * @return An instance of the MazeAlgorithm that matches the enum type. If something goes wrong, it might return null,
     * so watch out for that.
     */
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
    /**
     * Provides the default MazeAlgorithmType which is used when no specific type is requested.
     *
     * @return The default MazeAlgorithmType, which is BACKTRACKING.
     */
    public static MazeAlgorithmType defaultValue() {
        return BACKTRACKING;
    }
}
