package SortAlgorithms;

import java.util.Comparator;

public class MyHeapSort<T> implements Sorter<T>{


    private Comparator<T> comparator;
    @Override
    public void Sort(T[] array, Comparator<T> comparator) {
        this.comparator=comparator;


        for (int i=0; i<array.length; i++)
        {
            generate(array,array.length-1-i);
            swap(array,0,array.length-1-i);
        }
    }
    private void generate(T[] array,int length)
    {
        for (int i=(length/2)+1; i>=0; i--)
        {
            Heapify(array,length,i);
        }
    }

    private void Heapify(T[] array,int length,int index)
    {
        int max=index;
        int leftIndex=index*2+1;
        int rightIndex=index*2+2;

        if(leftIndex<length&&comparator.compare(array[leftIndex],array[max])>0)
        {
            max=leftIndex;
        }
        if(rightIndex<length&&comparator.compare(array[rightIndex],array[max])>0)
        {
            max=rightIndex;
        }
        if(max!=index)
        {
            swap(array,max,index);
            Heapify(array,length,max);
        }
    }
    private void swap(T[] array,int a,int b)
    {
        T temp=array[a];
        array[a]=array[b];
        array[b]=temp;
    }
}
