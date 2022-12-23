package database;

public interface Condition<T> {
    boolean satisfiesCondition(T t);
}
