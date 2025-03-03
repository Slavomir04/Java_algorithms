package SortAlgorithms;

import SortAlgorithms.MultiThread.MergeSortMT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SortingTest {

    static<T> boolean areSorted(T[] array, Comparator<T> comparator) {
        if(array.length < 2)return true;
        else{
            for(int i=1;i<array.length;i++){
                if(comparator.compare(array[i-1],array[i])>0)return false;
            }
        }
        return true;
    }
    static Integer[] createIntegerArray(int size,int range){
        Integer[] array = new Integer[size];
        for(int i=0;i<size;i++){
            array[i] = (int)(Math.random()*range);
        }
        return array;
    }
    static boolean testSorting(Sorter<Integer> sorter,int arraySize,int numberRange){
        Integer[] array = createIntegerArray(arraySize,numberRange);
        Comparator<Integer> comparator = new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
        sorter.Sort(array,comparator);
        return areSorted(array,comparator);
    }
    final int sortingSize=5000;
    final int numberRange=1000;

    @Test
    void BubbleSort() {
        assertTrue(testSorting(new Bubble<>(),sortingSize,numberRange));
    }
    @Test
    void HeapSort() {
        assertTrue(testSorting(new HeapSort<>(),sortingSize,numberRange));
    }
    @Test
    void InsertSort() {
        assertTrue(testSorting(new InsertSort<>(),sortingSize,numberRange));
    }
    @Test
    void MergeSort() {
        assertTrue(testSorting(new MergeSort<>(),sortingSize,numberRange));
    }
    @Test
    void QuickSort() {
        assertTrue(testSorting(new QuickSort<>(),sortingSize,numberRange));
    }
    @Test
    void SelectionSort() {
        assertTrue(testSorting(new SelectionSort<>(),sortingSize,numberRange));
    }
    @Test
    void MultiThreadedSort() {
        assertTrue(testSorting(new MergeSortMT<>(),sortingSize,numberRange));
    }


}