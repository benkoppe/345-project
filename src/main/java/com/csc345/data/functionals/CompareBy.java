package com.csc345.data.functionals;

/**
 * Represents a function that accepts one argument and produces a comparable result.
 */
@FunctionalInterface
public interface CompareBy<T, C extends Comparable<C>> {
    C getValue(T object);
}
