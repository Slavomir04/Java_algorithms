package SortAlgorithms;

import java.util.Comparator;

public class HeapSort<T> implements Sorter<T>{

    @Override
    public void Sort(T[] array, Comparator<T> comparator) {
        int length = array.length;

        for (int i = length / 2 - 1; i >= 0; i--) //twprzenie
            heapify(array, length, i, comparator);

        for (int i = length - 1; i > 0; i--) { //finalnesortowanie
            T temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            heapify(array, i, 0, comparator);
        }
    }


    void heapify(T[] array, int length, int index, Comparator<T> comparator) { //naprawa
        int largest = index;
        int leftChildIdx = 2 * index + 1;
        int rightChildIdx = 2 * index + 2;


        if (leftChildIdx < length && comparator.compare(array[leftChildIdx], array[largest]) > 0)
            largest = leftChildIdx;


        if (rightChildIdx < length && comparator.compare(array[rightChildIdx], array[largest]) > 0)
            largest = rightChildIdx;


        if (largest != index) {
            T swap = array[index];
            array[index] = array[largest];
            array[largest] = swap;


            heapify(array, length, largest, comparator);
        }
    }
    @Override
    public String toString()
    {
        return "HeapSort";
    }

}
