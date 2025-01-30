package Tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TreeRB2<T> implements MyTree<T>{
    private final boolean RED=true;
    private final boolean BLACK=false;
    private final Node _null=new Node();



    private class Node
    {
        public Node right;      //wieksze
        public Node left;       //mniejsze
        public Node parent;
        boolean Color; //true czerwony, false czarny
        private T value;

        public Node(T value)
        {
            Color=true;
            this.value=value;
            left=_null;
            right=_null;
            parent=_null;
        }
        public Node()//_null konstruktor
        {
            Color=false;
            value=null;
            right=this;
            left=this;
            parent=this;
        }
        public boolean add(Node element)
        {
            int compared=comparator.compare(element.value,this.value);
            if(compared>0)
            {
                if(right!=_null)return right.add(element);
                else{
                    right=element;
                    right.parent=this;
                    return true;
                }
            }else if(compared<0)
            {
                if(left!=_null)return left.add(element);
                else{
                    left=element;
                    left.parent=this;
                    return true;
                }
            }else return false;
        }
        public void linkAsLeft(Node element)
        {
            left=element;
            element.parent=this;
        }
        public void linkAsRight(Node element)
        {
            right=element;
            element.parent=this;
        }
        public void placeInArray(ArrayList<T> array,int index)
        {
            array.add(index,value);
            int leftIndex=index*2+1;
            int rightIndex=leftIndex+1;

            if(left!=_null)left.placeInArray(array,leftIndex);
            if(right!=_null)right.placeInArray(array,rightIndex);
        }
        public void addToList(ArrayList<T> list)
        {
            list.add(value);
            if(left!=_null)left.addToList(list);
            if(right!=_null)right.addToList(list);
        }
        public void addToListString(ArrayList<String> list)
        {
            list.add(toString());
            if(left!=_null)left.addToListString(list);
            if(right!=_null)right.addToListString(list);
        }
        public void placeInArrayString(String[] array, int index)
        {
            array[index]=toString();
            int leftIndex=index*2+1;
            int rightIndex=index*2+2;

            if(left!=_null)left.placeInArrayString(array,leftIndex);
            if(right!=_null)right.placeInArrayString(array,rightIndex);
        }
        public void placeInArrayWszerz(ArrayList<T> list,LinkedList<T> queue)
        {
            while (!queue.isEmpty())list.add(queue.removeFirst());
            queue.addFirst(value);
            if(left!=_null)left.placeInArrayWszerz(list,queue);
            if(right!=_null)right.placeInArrayWszerz(list,queue);
            if(left==_null && right==_null&&!queue.isEmpty())list.add(queue.removeFirst());
        }

        public int getHeight()
        {
            int max=0;
            if(left!=_null)max=left.getHeight()+1;
            if(right!=_null)
            {
                int temp= right.getHeight()+1;
                if(temp>max)return temp;
            }
            return max;
        }
        @Override
        public String toString()
        {
            String ColorS="BLACK";
            if(Color)ColorS="RED";
            return String.format("%s %s",value,ColorS);
        }
    }
    //////////////////////

    private Node root;
    private Comparator<T> comparator;
    public TreeRB2(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    private void transplant(Node previous, Node x) { //przeszczep rodzica
        if (previous.parent == _null || previous.parent==null) {
            root = x;
        } else if (previous == previous.parent.left) {
            previous.parent.left = x;
        } else {
            previous.parent.right = x;
        }
        x.parent = previous.parent;
    }

    private void LeftRotate(Node node)
    {
        Node y = node.right;
        node.right = y.left;
        if (y.left != _null) {
            y.left.parent = node;
        }
        y.parent = node.parent;
        if (node.parent == null || node.parent==_null) {
            this.root = y;
        } else if (node == node.parent.left) {
            node.parent.left = y;
        } else {
            node.parent.right = y;
        }
        y.left = node;
        node.parent = y;
     //   if(x==root)root=y;
    }
    private void RightRotate(Node node)
    {
        Node x = node.left;
        node.left = x.right;
        if (x.right != _null) {
            x.right.parent = node;
        }
        x.parent = node.parent;
        if (node.parent == null || node.parent==_null) {
            this.root = x;
        } else if (node == node.parent.right) {
            node.parent.right = x;
        } else {
            node.parent.left = x;
        }
        x.right = node;
        node.parent = x;

       // if(y==root)root=x;
    }

    @Override
    public T get(T value) {
        if(root==null || root==_null)
        {
            return null;
        }
        Node temp=root;
        while (temp!=_null)
        {
            int compared=comparator.compare(value,temp.value);
            if(compared>0)temp=temp.right;
            else if(compared<0)temp=temp.left;
            else return temp.value;
        }
        return null;
    }

    public void add(T value)
    {
        if(value==null)throw new IllegalArgumentException();
        if(root==null)
        {
            root=new Node(value);
            root.Color=BLACK;
            return;
        }
        Node node=new Node(value);

        if(!root.add(node))return;

        Node uncle;
        while (node.parent.Color==RED) //true jest czerwone
        {

            if(node.parent.parent.left == node.parent)//sytuacje gdzie ojciec jest z lewej od dziadka
            {
                uncle=node.parent.parent.right;
                if(uncle.Color) //czerwony wujaszek
                {
                    node.parent.Color=false; //ojciec czarny
                    uncle.Color=false;  //wujek czarny
                    node.parent.parent.Color=true; //dziadek czerwony
                    node=node.parent.parent; //node jest teraz dziadkiem i idzie dalej petla...
                }else //czarny wujaszek
                {
                    if(node.parent.right == node)//trojkat  //zawsze w trojkacie pojawia sie pozniej linia
                    {
                        node=node.parent;
                        LeftRotate(node.parent);
                          //wyjscie
                    } //linia   //zawsze w trojkacie pojawia sie pozniej linia


                        node.parent.Color=false;
                        node.parent.parent.Color=true;
                        RightRotate(node.parent.parent);  //tu sie konczy naprawianie wyjsciem pierwotny node

                }
            }else//sytuacje gdzie ojciec jest z prawej od dziadka //zalozenie ze to samo tylko right na left
            {
                uncle = node.parent.parent.left;
                if (uncle.Color==RED) //czerwony wujaszek
                {
                    node.parent.Color = false; //ojciec czarny
                    uncle.Color = false;  //wujek czarny
                    node.parent.parent.Color = true; //dziadek czerwony
                    node = node.parent.parent; //node jest teraz dziadkiem i idzie dalej petla...

                } else //czarny wujaszek
                {
                    if(node.parent.left == node)//trojkat
                    {
                        RightRotate(node.parent);
                        node=node.parent;    //wyjscie
                    } //linia   //zawsze w trojkacie pojawia sie pozniej linia

                    node.parent.parent.Color=true;
                    node.parent.Color=false;
                    LeftRotate(node.parent.parent);    //tu sie konczy naprawianie

                }
            }
        }
        root.Color=false; //false jest czarne

    }

    @Override
    public void remove(T value) { //bug with removind root ;(
        if (root == null || root == _null) throw new NullPointerException();

        Node node = root;
        while (node != _null) {
            int compared = comparator.compare(value, node.value);
            if (compared < 0) {
                node = node.left;
            } else if (compared > 0) {
                node = node.right;
            } else {
                break;
            }
        }//szukanie node(value)

        if (node == _null) throw new NoSuchElementException();

        Node y = node;
        Node x;
        boolean originalColorY = y.Color;

        if (node.left == _null) {
            x = node.right;
            transplant(node, node.right);
        } else if (node.right == _null) {
            x = node.left;
            transplant(node, node.left);
        } else {
            y = minimum(node.right);//szukanie nastepnika
            originalColorY = y.Color; // zapis koloru nastepnika
            x = y.right;

            if (y.parent == node) {
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = node.right;
                y.right.parent = y;
            }

            transplant(node, y);
            y.left = node.left;
            y.left.parent = y;
            y.Color = node.Color;


        }

        if (originalColorY == BLACK) {
            delete_fix(x);
        }
    }

    private Node minimum(Node node) {
        while (node.left != _null) {
            node = node.left;
        }
        return node;
    }

    private void delete_fix(Node x) {
        while (x != root && x.Color == BLACK) { // x musi byc czarny
            if (x == x.parent.left) { //x jest lewy od ojca
                Node w = x.parent.right; // brat jest prawy

                if (w.Color == RED) {//brat jest czerwony
                    w.Color = BLACK; //brat jest czarny
                    x.parent.Color = RED; //ojciec jest czerwony
                    RightRotate(x.parent); //zamiana brata z ojcem
                    w = x.parent.right; //ojciec jest bratem
                }

                if (w.left.Color == BLACK && w.right.Color == BLACK) { //brat czarny, bratankowie czarni
                    w.Color = RED; //brat zmienia kolor
                    x = x.parent;   // x zmienia sie w ojca
                } else {
                    if (w.right.Color == BLACK) {//lewy bratanek red  TROJKAT
                        w.left.Color = BLACK; //lewy bratanek czarny
                        w.Color = RED; //brat czerwony
                        LeftRotate(w); //zamiana bratank z bratem
                        w = x.parent.right; // brat=bratanek ojciec
                    }
                    //LINIA
                    w.Color = x.parent.Color;   //color brata == color ojca
                    x.parent.Color = BLACK; //color ojca = czarany
                    w.right.Color = BLACK; //
                    RightRotate(x.parent);
                    x = root; //koniec naprawiania
                }
            } else {//x jest prawy od ojca
                Node w = x.parent.left;

                if (w.Color == RED) {
                    w.Color = BLACK;
                    x.parent.Color = RED;
                    LeftRotate(x.parent);
                    w = x.parent.left;
                }

                if (w.right.Color == BLACK && w.left.Color == BLACK) {
                    w.Color = RED;
                    x = x.parent;
                } else {
                    if (w.left.Color == BLACK) {
                        w.right.Color = BLACK;
                        w.Color = RED;
                        RightRotate(w);
                        w = x.parent.left;
                    }

                    w.Color = x.parent.Color;
                    x.parent.Color = BLACK;
                    w.left.Color = BLACK;
                    LeftRotate(x.parent);
                    x = root;
                }
            }
        }
        x.Color = BLACK;
    }

    @Override
    public boolean contains(T value) {
        if(root==null || root==_null)
        {
            return false;
        }
        Node temp=root;
        while (temp!=_null)
        {
            int compared=comparator.compare(value,temp.value);
            if(compared>0)temp=temp.right;
            else if(compared<0)temp=temp.left;
            else return true;
        }
        return false;
    }

    public String[] getString()
    {
        if(root==_null)return null;
        ArrayList<String> list=new ArrayList<>();
        root.addToListString(list);
        return list.toArray(new String[0]);
    }
    public ArrayList<T> getArray()
    {
        if(root==_null || root==null)return null;
        else {
            ArrayList<T> result=new ArrayList<>();
            root.addToList(result);
            return result;
        }
    }
    public String[] getStringTreeOrder()
    {
        if(root==_null || root==null)return null;
        else {
            int size=(int)(Math.pow(2,root.getHeight()+1)-0.5);
            String[] result=new String[size];
            root.placeInArrayString(result,0);
            return result;
        }
    }


    @Override
    public ArrayList<T> getArrayTreeOrder() {
        if(root==_null || root==null)return null;
        else {
            int size=(int)(Math.pow(2,root.getHeight()+1)-0.5);
            ArrayList<T> list=new ArrayList<>(size);
            for (int i=0; i<size; i++)list.add(null);
            root.placeInArray(list,0);
            return list;
        }
    }

    @Override
    public ArrayList<T> getBroad() {
        if(root==null || root==_null)return null;
        int size=(int)(Math.pow(2,root.getHeight()+1)-0.5);
        ArrayList<T> result=new ArrayList<>(size);
        root.placeInArrayWszerz(result,new LinkedList<T>());
        return result;
    }

}
