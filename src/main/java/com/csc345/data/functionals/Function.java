package com.csc345.data.functionals;

/**
 * Represents a function that accepts one argument and produces a result.
 */
@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);
}
