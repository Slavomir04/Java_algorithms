package Tree;

import java.util.ArrayList;

public interface MyTree<T> {

    public T get(T value);
    public void add(T value);
    public void remove(T value);
    public boolean contains(T value);
    public ArrayList<T> getArray();
    public ArrayList<T> getArrayTreeOrder();
    public ArrayList<T> getBroad();
}
