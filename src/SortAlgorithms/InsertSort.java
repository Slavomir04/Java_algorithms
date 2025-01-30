package SortAlgorithms;

import java.util.Comparator;

public class InsertSort<T> implements Sorter<T>{

    @Override
    public void Sort(T[] array, Comparator<T> comparator) {
        for (int i=1; i<array.length; i++)
        {
            for (int y=i; y>0; y--)
            {
                if(comparator.compare(array[y],array[y-1])<0)
                {
                    T temp=array[y];
                    array[y]=array[y-1];
                    array[y-1]=temp;
                }else break;
            }
        }
    }

    @Override
    public String toString()
    {
        return "InsertSort";
    }

}
