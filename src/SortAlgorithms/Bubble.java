package SortAlgorithms;
import java.util.Comparator;
public class Bubble<T> implements Sorter<T>{

    private boolean show;

    public Bubble(boolean show) {
        this.show = show;
    }
    public Bubble()
    {
        show=false;
    }

    @Override
    public void Sort(T[] array, Comparator<T> comparator) {
        for (int i=0; i<array.length; i++)
        {
            for (int y=0; y<array.length; y++)
            {
                if(i==y)continue;
                if(comparator.compare(array[i],array[y])<0)
                {
                    T temp=array[i];
                    array[i]=array[y];
                    array[y]=temp;
                    if(show)
                    {
                        show(array);
                    }
                }
            }
        }
    }
    private void show(T[] array)
    {
        System.out.print("[");
        for (T element : array)
        {
            System.out.print(element+", ");
        }
        System.out.print("]");
        System.out.println();
    }
    @Override
    public String toString()
    {
        return "BubbleSort";
    }
}
