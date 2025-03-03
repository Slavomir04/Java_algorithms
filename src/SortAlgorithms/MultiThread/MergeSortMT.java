
package SortAlgorithms.MultiThread;
import SortAlgorithms.Sorter;

import java.util.Comparator;
import java.util.LinkedList;

public class MergeSortMT<T> implements Sorter<T> {
    Comparator<T> comparator;
    final int minimum_window=1000;

    @Override
    public void Sort(T[] array, Comparator<T> comparator) {
        this.comparator = comparator;
        if(array !=null && array.length > 1) {
            int available_threads = Runtime.getRuntime().availableProcessors();
            int window = Math.max(array.length / available_threads, minimum_window);
            int workers = (array.length / window) + (array.length % window == 0 ? 0 : 1);
            System.out.println("workers:"+workers);
            LinkedList<LinkedList<T>> queue = createQueue(array, window, workers);
            LinkedList<T> result = mergeQueue(queue, workers);
            for (int i = 0; i < array.length; i++) array[i] = result.removeFirst();
        }
    }




    LinkedList<LinkedList<T>> createQueue(T[] array, int window, int workers){
        Thread[] threads = new Thread[workers];
        LinkedList<LinkedList<T>> queue = new LinkedList<>();
        int temp=0;
        for(int i = 0; i < workers; i++){
            int start = temp;
            int end = Math.min(start + window, array.length-1);
            threads[i] = new Thread(() -> {
                LinkedList<T> list = getSortedList(array,start,end);
                synchronized (queue) {
                    queue.add(list);
                }
            });
            threads[i].start();
            temp = end+1;
        }
        try {
            for (Thread thread : threads) thread.join();
        }catch (InterruptedException e){throw new RuntimeException(e);}
        return queue;
    }

    LinkedList<T> mergeQueue(LinkedList<LinkedList<T>> queue, int workers){
        Thread[] threads = new Thread[workers];
        while(queue.size()>1){
            int half = workers/2;
            if(half > 0) {
                for (int i = 0; i < half; i++) {
                    threads[i] = new Thread(() -> {
                        LinkedList<T> merged, l1, l2;
                        synchronized (queue) {
                            l1 = queue.removeFirst();
                            l2 = queue.removeFirst();
                        }
                        merged = mergeList(l1, l2);
                        synchronized (queue) {
                            queue.addLast(merged);
                        }
                    });
                    threads[i].start();
                }
                try {
                    for (int i = 0; i < half; i++) threads[i].join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                workers = half;
            }else{
                queue.addLast(mergeList(queue.removeFirst(),queue.removeFirst()));
            }
        }
        return queue.removeFirst();
    }





    private LinkedList<T> getSortedList(T[] array, int start, int end){
        if(start == end){
            LinkedList<T> temp = new LinkedList<>();
            temp.add(array[start]);
            return temp;
        }else {
            int mid = (start + end) / 2;
            LinkedList<T> left = getSortedList(array, start, mid);
            LinkedList<T> right = getSortedList(array, mid + 1, end);
            return mergeList(left, right);
        }
    }

    private LinkedList<T> mergeList(LinkedList<T> l1, LinkedList<T> l2){
        LinkedList<T> merged = new LinkedList<>();
        while (!l1.isEmpty() && !l2.isEmpty()) {
            if (comparator.compare(l1.getFirst(), l2.getFirst()) < 0) merged.add(l1.removeFirst());
            else merged.add(l2.removeFirst());
        }
        while (!l1.isEmpty()) merged.add(l1.removeFirst());
        while (!l2.isEmpty()) merged.add(l2.removeFirst());
        return merged;
    }



}

