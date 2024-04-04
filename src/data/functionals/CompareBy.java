package data.functionals;

@FunctionalInterface
public interface CompareBy<T, C extends Comparable<C>> {
    C getValue(T object);
}
