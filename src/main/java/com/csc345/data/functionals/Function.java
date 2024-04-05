package com.csc345.data.functionals;

@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);
}
