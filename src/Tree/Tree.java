package Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

public class Tree<T>{

    protected Comparator<T> comparator;


        protected class Leaf
        {
            public   Leaf parent;
            public    Leaf nextLeft;  //smaller
            public    Leaf nextRight; //bigger
            public    T value;

            public Leaf(T value) {
                this.value = value;
            }

            public boolean add(Leaf leaf) //return contains
            {
                if(comparator.compare(this.value,leaf.value)>0)
                {
                    if(nextLeft!=null)
                    {
                        return   nextLeft.add(leaf);
                    }
                    else
                    {
                        linkAsLeft(this,leaf);
                        return false;
                    }
                }else if(comparator.compare(this.value,leaf.value)<0)
                {
                    if(nextRight!=null)
                    {
                        return nextRight.add(leaf);
                    }
                    else
                    {
                        linkAsRight(this,leaf);
                        return false;
                    }
                }
                return true;
            }
            public   void linkAsRight(Leaf parent,Leaf Right)
            {
                parent.nextRight=Right;
                Right.parent=parent;
            }
            public   void linkAsLeft(Leaf parent,Leaf Left)
            {
                parent.nextLeft=Left;
                Left.parent=parent;
            }
            public   Leaf findMax()
            {
                if(nextRight!=null)return nextRight.findMax();
                else return this;
            }
            public   Leaf findMin()
            {
                if(nextLeft!=null)return nextLeft.findMin();
                else return this;
            }
            public   Leaf findParentLinkedLeft()
            {
                if(parent==null)return null;
                if(parent.nextLeft!=this)return parent.findParentLinkedLeft();
                else return parent;
            }
            public   Leaf findParentLinkedRight()
            {
                if(parent==null)return this;
                if(parent.nextRight!=this)return parent.nextRight;
                else return parent;
            }
            public   Leaf findNext()
            {
                if(nextRight!=null)return nextRight.findMin();
                else return findParentLinkedLeft();
            }
            public   Leaf findPrevious()
            {
                if(nextLeft!=null)return nextLeft;
                else return findParentLinkedLeft();
            }
            public void placeInArray(T[] array,int index)
            {
                array[index]=value;
                int left=index*2+1;
                int right=left+1;
                if(nextLeft!=null)nextLeft.placeInArray(array,left);
                if(nextRight!=null)nextRight.placeInArray(array,right);
            }
            public void placeInArrayString(String[] array,int index)
            {
                array[index]=value.toString();
                int left=index*2+1;
                int right=left+1;
                if(nextLeft!=null)nextLeft.placeInArrayString(array,left);
                if(nextRight!=null)nextRight.placeInArrayString(array,right);
            }
            public int height()
            {
                int max=0;
                if(nextRight!=null)max=nextRight.height()+1;
                if(nextLeft!=null)
                {
                    int temp=nextLeft.height()+1;
                    if(temp>max)return temp;
                }
                return max;
            }
            public int countOneChild()
            {
                if(nextLeft==null && nextRight!=null)
                {
                    return nextRight.countOneChild()+1;
                }else if(nextLeft!=null && nextRight==null){
                    return nextLeft.countOneChild()+1;
                }
                return 0;
            }
            public void putPreOrder(ArrayList<T> list)
            {
                list.add(value);
                if(nextLeft!=null)nextLeft.putPreOrder(list);
                if(nextRight!=null)nextRight.putPreOrder(list);
            }
            public void putPostOrder(ArrayList<T> list)
            {

                if(nextLeft!=null)nextLeft.putPostOrder(list);
                if(nextRight!=null)nextRight.putPostOrder(list);
                list.add(value);
            }

            public Leaf find(T element)
            {
                if(comparator.compare(this.value,element)==0)return this;
                else
                {
                    if(comparator.compare(this.value,element)>0)
                    {
                        if(nextLeft==null)return null;
                        else return nextLeft.find(element);
                    }else {
                        if(nextRight==null)return null;
                        else return nextRight.find(element);
                    }
                }
            }
            public void printAsTree(String[] array)
            {

            }
            @Override
            public String toString()
            {
                return "{Leaf: value="+value+"}";
            }
        }//////////

        private int SIZE=0;
        public Tree(Comparator<T> comparator)
        {
            this.comparator=comparator;
        }
        protected Leaf root;
        public void generate(T[] array)
        {
            this.root=new Leaf(array[0]);
            for (int i=1; i<array.length; i++)
            {
                root.add(new Leaf(array[i]));
                SIZE++;
            }

        }
        private int counter()
        {
            if(root==null)return 0;
            int counter=1;
            Leaf previous=root.findMin();
            Leaf temp=previous.findNext();

            while (temp!=previous)
            {
                previous=temp;
                temp=temp.findNext();
                counter++;
            }
            return counter;
        }
        private int counter(Predicate<T> predicate)
        {
            if(root==null)return 0;
            int counter=0;
            if(predicate.test(root.value))counter=1;

            Leaf previous=root.findMin();
            Leaf temp=previous.findNext();

            while (temp!=previous)
            {

                previous=temp;
                temp=temp.findNext();
                if(predicate.test(previous.value))counter++;
            }
            return counter;
        }
        public boolean find(T element)
        {
            return root.find(element)!=null;
        }
        public void Sort(T[] array)
        {
            generate(array);
            int index=0;
            Leaf temp=root.findMin();
            for (int i=0; i<array.length; i++)
            {
                array[i]=temp.value;
                temp=temp.findNext();
                if(temp==null)break;
            }
        }

        public T[] getTreeAsArray()
        {
            if(root==null)return null;
            else{
                int size=(int)(Math.pow(2,root.height()+1)-0.5);
                T[] result=((T[])new Object[size]);
                Arrays.fill(result,null);
                root.placeInArray(result,0);
                return result;
            }
        }
    public String[] getTreeAsArrayString()
    {
        if(root==null)return null;
        else{
            int size=(int)(Math.pow(2,root.height()+1)-0.5);
            String[] result=new String[size];
            Arrays.fill(result,null);
            root.placeInArrayString(result,0);
            return result;
        }
    }
    public ArrayList<T> getInOrder()
    {
        if(root==null)return null;
        ArrayList<T> list=new ArrayList<>(SIZE);
        Leaf previous=root.findMin();
        Leaf temp=previous.findNext();

        list.add(previous.value);
        while (previous!=temp)
        {
            previous=temp;
            temp=temp.findNext();
            if(temp==null)break;
            list.add(temp.value);
        }
        return list;
    }
    public ArrayList<T> getPreOrder()
    {
        if(root==null)return null;
        ArrayList<T> result=new ArrayList<>(SIZE);
        root.putPreOrder(result);
        return result;
    }
    public ArrayList<T> getPostOrder()
    {
        if(root==null)return null;
        ArrayList<T> result=new ArrayList<>(SIZE);
        root.putPostOrder(result);
        return result;
    }

    public void info()
    {
        if(root==null)System.out.println("is empty");
        System.out.println("-------");
        System.out.printf("Wysokosc: %s\n",root.height());
        System.out.printf("Liczba wezlow: %s\n",counter());

        if(root.value instanceof Integer) System.out.printf("Liczba wezlow parzysty klucz: %s\n",counter(new Predicate<T>() {
            @Override
            public boolean test(T t) {
                Integer a=(Integer) t;
                return a%2==0;
            }
        }));
        System.out.printf("jedno dziecko: %s\n",root.countOneChild());

        System.out.println("-------");
    }
}
