package data;

@FunctionalInterface
public interface Runnable<T> {
    void run(T data);
}
