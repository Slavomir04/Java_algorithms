package SortAlgorithms;

import java.util.Comparator;

public interface Sorter<T> {

    public void Sort(T[] array, Comparator<T> comparator);

}
