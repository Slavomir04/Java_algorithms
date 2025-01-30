package SortAlgorithms;

import java.util.Comparator;

public class QuickSort<T> implements Sorter<T>{
    private Comparator<T> comparator;
    @Override
    public void Sort(T[] array, Comparator<T> comparator) {
        this.comparator=comparator;
        Sort(array,0,array.length-1);
    }

    private void Sort(T[] array,int left,int right)
    {
        if(left>=right)return;
        int indexPivot=generatePivot(array,left,right);
        T pivot=array[indexPivot];
        swap(array,indexPivot,right);
        int temp=left-1;
        for (int i=left; i<right; i++)
        {
            if(comparator.compare(array[i],pivot)<=0)
            {
                temp++;
                swap(array,temp,i);
            }
        }
        swap(array,temp+1,right);

        Sort(array,left,temp);
        Sort(array,temp+2,right);

    }
    private void swap(T[] array,int a,int b)
    {
        T temp=array[a];
        array[a]=array[b];
        array[b]=temp;
    }

    private int generatePivot(T[] array,int p,int k)
    {
        if( k-p > 100)
        {
            Integer[] tab=new Integer[]{
                    RandInt(p,k),
                    RandInt(p,k),
                    RandInt(p,k)
            };

            microSort(tab, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return comparator.compare(array[o1],array[o2]);
                }
            });

            return tab[tab.length/2];
        }else
        {
            return RandInt(p,k);
        }
    }

    private int RandInt(int p,int k)
    {
        k=k+1;
        return (int)(Math.abs(p-k)*Math.random()+Math.min(p,k));
    }
    private static   <T>void microSort(T[] array,Comparator<T> comparator)
    {
        for (int i=array.length-2; i>=0; i--)
        {
            for (int y=i; y<array.length-1; y++)
            {
                if(comparator.compare(array[y],array[y+1])>0)
                {
                    T temp=array[y];
                    array[y]=array[y+1];
                    array[y+1]=temp;
                }else break;

            }
        }
    }
    @Override
    public String toString()
    {
        return "QuickSort";
    }
}
