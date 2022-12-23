package database;

import java.util.ArrayList;

public interface Database<T> {

    T getObject(Condition<T> c);

    ArrayList<T> getObjects(Condition<T> c);

    void addObject(T t);

    boolean removeObject(T t);

    void close();
}
