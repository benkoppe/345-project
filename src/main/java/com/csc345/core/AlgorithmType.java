package com.csc345.core;

public interface AlgorithmType {
    public String getName();

    public static AlgorithmType defaultValue() {
        return null;
    };
}
