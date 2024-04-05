package com.csc345.data.functionals;

@FunctionalInterface
public interface CompareBy<T, C extends Comparable<C>> {
    C getValue(T object);
}
