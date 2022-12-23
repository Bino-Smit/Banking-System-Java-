package database;

import java.util.ArrayList;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileDatabase<T> implements Database<T> {

    final private String path;
    private ArrayList<T> objects;

    public FileDatabase(String path) {
        this.path = path;
        objects = new ArrayList<>();
        ObjectInputStream oin = null;
        try {
            oin = new ObjectInputStream(new FileInputStream(path));
            while (true) {
                objects.add((T) oin.readObject());
            }
        } catch (EOFException e) {

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            if (oin != null) {
                try {
                    oin.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        }
    }

    @Override
    public T getObject(Condition<T> c) {
        for (T t : objects) {
            if (c.satisfiesCondition(t)) {
                return t;
            }
        }
        return null;
    }

    @Override
    public ArrayList<T> getObjects(Condition<T> c) {
        ArrayList<T> res = new ArrayList<>();
        for (T t : objects) {
            if (c.satisfiesCondition(t)) {
                res.add(t);
            }
        }
        return res;
    }

    @Override
    public void addObject(T t) {
        objects.add(t);
    }

    @Override
    public boolean removeObject(T t) {
        return objects.remove(t);
    }

    @Override
    public void close() {
        try {
            var oout = new ObjectOutputStream(new FileOutputStream(path));
            for (T t : objects) {
                oout.writeObject(t);
            }
            oout.close();
        } catch (Exception e) {
            System.out.println("Could not close database");
            e.printStackTrace();
            System.exit(0);
        }
    }
}
