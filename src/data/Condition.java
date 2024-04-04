package data;

@FunctionalInterface
public interface Condition<T> {
    boolean test(T data);
}
