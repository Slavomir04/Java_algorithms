import SortAlgorithms.HeapSort;
import SortAlgorithms.MergeSort;
import SortAlgorithms.QuickSort;
import SortAlgorithms.Sorter;
import Tree.MyTree;
import Tree.TreeRB2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeSet;

public class Main {

    public static <T>void TestSorter(Sorter<T> sorter, Comparator<T> comparator,T[] array) {
        if(array == null || array.length == 0)throw new RuntimeException("bad array");
        T[] array1 = Arrays.copyOf(array, array.length);
        T[] array2 = Arrays.copyOf(array, array.length);

        long t1_java;
        long t2_sorter;

        t1_java = System.currentTimeMillis();
        Arrays.sort(array1, comparator);
        t1_java = System.currentTimeMillis() - t1_java;

        t2_sorter = System.currentTimeMillis();
        sorter.Sort(array2, comparator);
        t2_sorter = System.currentTimeMillis() - t2_sorter;

        System.out.printf("Test for %d, Java: %d ms \t %s: %d ms\n",array.length,t1_java,sorter.toString(),t2_sorter);
    }

    public static <T>void TestTree(MyTree<T> tree, Comparator<T> comparator, T[] array) {
        if(array == null || array.length == 0)throw new RuntimeException("bad array");
        T[] array_queue = Arrays.copyOf(array, array.length);
        T[] array_java = Arrays.copyOf(array, array.length);
        T[] array_my = Arrays.copyOf(array, array.length);

        TreeSet<T> treeSet_java = new TreeSet<>(comparator);
        treeSet_java.addAll(Arrays.asList(array_java));
        for(T t : array_my)tree.add(t);

        int z = array.length/10;
        LinkedList<T> queue = new LinkedList<>();
        for(int i = 0; i < z; i++)queue.add(array_queue[(int)(Math.random()*(array.length-1))]);

        long t1_java= System.currentTimeMillis();
        for(T t : queue)treeSet_java.contains(t);
        t1_java= System.currentTimeMillis() - t1_java;

        long t2_my= System.currentTimeMillis();
        for(T t : queue)tree.contains(t);
        t2_my= System.currentTimeMillis() - t2_my;

        System.out.printf("Test for %d, Java: %d ms \t %s: %d ms\n",array.length,t1_java,tree.toString(),t2_my);
    }


    public static void main(String[] args) {

        Comparator<Integer> comparator = new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
        int size = 1000;
        int range = 1000;
        Integer[] array = new Integer[size];
        for(int i=0; i<size; i++){
            array[i] = (int)(Math.random()*range);
        }

        TestSorter(new HeapSort<>(), comparator, array);
        TestSorter(new QuickSort<>(), comparator, array);
        TestSorter(new MergeSort<>(), comparator, array);
;
    }
}