package SortAlgorithms;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class MergeSort<T> implements Sorter<T>{

    private Comparator<T> comparator;


    @Override
    public void Sort(T[] array, Comparator<T> comparator) {

        this.comparator=comparator;
        Sort(array);
    }
    private void Sort(T[] array)
    {
        if(array.length<=1)return;
        int size=array.length/2;
        T[] left=(T[]) new Object[size];
        T[] right=(T[]) new Object[array.length-size];
        int i=0;
         while (i<size)
         {
             left[i]=array[i];
             i++;
         }
         while (i-size< right.length)
         {
             right[i-size]=array[i];
             i++;
         }
         Sort(left);
         Sort(right);
         mergeArray(array,left,right);
    }
    private void   mergeArray(T[] array,T[] left,T[] right)
    {
        if(left.length+right.length>array.length)throw new RuntimeException();

        int leftIndex=0;
        int rightIndex=0;
        int i=0;
        while (leftIndex<left.length && rightIndex<right.length)
        {
            if(comparator.compare(left[leftIndex],right[rightIndex])<=0)
            {
                array[i]=left[leftIndex];
                leftIndex++;
            }else
            {
                array[i]=right[rightIndex];
                rightIndex++;
            }
            i++;

        }
        while (rightIndex<right.length)
        {
            array[i]=right[rightIndex];
            rightIndex++;
            i++;
        }
        while (leftIndex<left.length)
        {
            array[i]=left[leftIndex];
            leftIndex++;
            i++;
        }
    }
    @Override
    public String toString()
    {
        return "MergeSort";
    }

}
