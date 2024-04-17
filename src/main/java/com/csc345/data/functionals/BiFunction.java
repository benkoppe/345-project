package com.csc345.data.functionals;

/**
 * Represents a function that accepts two arguments and produces a result.
 */
@FunctionalInterface
public interface BiFunction<T, U, R> {
    R apply(T t, U u);
}
