package com.csc345.data.functionals;

@FunctionalInterface
public interface Condition<T> {
    boolean test(T data);
}
