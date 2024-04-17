package com.csc345.data.functionals;

/**
 * Represents a function that accepts one argument and produces a boolean result.
 */
@FunctionalInterface
public interface Condition<T> {
    boolean test(T data);
}
