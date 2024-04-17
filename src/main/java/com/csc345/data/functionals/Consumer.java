package com.csc345.data.functionals;

/**
 * Represents a function that consumes one argument.
 */
@FunctionalInterface
public interface Consumer<T> {
    void accept(T data);
}
