package com.csc345.data.functionals;

@FunctionalInterface
public interface Consumer<T> {
    void accept(T data);
}
