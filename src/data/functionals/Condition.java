package data.functionals;

@FunctionalInterface
public interface Condition<T> {
    boolean test(T data);
}
