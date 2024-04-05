package com.csc345.data.functionals;

@FunctionalInterface
public interface Runnable<T> {
    void run(T data);
}
