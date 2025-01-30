package SortAlgorithms;

import java.util.Comparator;

public class SelectionSort<T> implements Sorter<T>{

    @Override
    public void Sort(T[] array, Comparator<T> comparator) {
        for (int i=0; i<array.length; i++)
        {
            int indexMin=i;
            for (int y=i; y<array.length; y++)
            {
                if(comparator.compare(array[y],array[indexMin])<0)
                {
                    indexMin=y;
                }
            }
            T temp=array[indexMin];
            array[indexMin]=array[i];
            array[i]=temp;
        }
    }
    @Override
    public String toString()
    {
        return "SelectSort";
    }
}
