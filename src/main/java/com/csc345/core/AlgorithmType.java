package com.csc345.core;

/**
 * Represents the different types of algorithms that can be used to either solve or generate a maze.
 */
public interface AlgorithmType {
    
    /**
     * Returns the name of the algorithm type.
     * 
     * @return A {@code String} representing the name of the algorithm type.
     */
    public String getName();
    /**
     * it returns a default value for the AlgorithmType. This method can be used to provide
     * a default algorithm type when no specific implementation is required.
     * 
     * @return An {@code AlgorithmType} representing the default algorithm type. Can be
     *         {@code null} if no default type is specified.
     */
    public static AlgorithmType defaultValue() {
        return null;
    };
}
