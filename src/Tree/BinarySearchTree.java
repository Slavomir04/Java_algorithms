package Tree;
import java.util.*;

public class BinarySearchTree<T> implements MyTree<T> {
    private Node<T> root;
    private Comparator<T> comparator;

    public BinarySearchTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    private class Node<T> {
        T value;
        Node<T> left, right;

        Node(T value) {
            this.value = value;
            left = right = null;
        }
        public void placeInArray(ArrayList<T> list)
        {
            list.add(value);
            if(left!=null)left.placeInArray(list);
            if(right!=null)right.placeInArray(list);
        }
        public void placeInArray(ArrayList<T> list,int index)
        {
            list.add(index, value);
           int leftIndex=index*2+1;
           int rightIndex=leftIndex+1;
           if(left!=null)left.placeInArray(list,leftIndex);
           if(right!=null)right.placeInArray(list,rightIndex);
        }
        public void placeInArrayWszerz(ArrayList<T> list,LinkedList<T> queue)
        {
            while (!queue.isEmpty())list.add(queue.removeFirst());
            queue.addFirst(value);
            if(left!=null)left.placeInArrayWszerz(list,queue);
            if(right!=null)right.placeInArrayWszerz(list,queue);
            if(left==null && right==null&&!queue.isEmpty())list.add(queue.removeFirst());
        }
        public int getHeight()
        {
            int max=0;
            if(left!=null)
            {
                max=left.getHeight()+1;
            }
            if(right!=null)
            {
                int temp=right.getHeight()+1;
                if(temp>max)return temp;
            }
            return max;
        }
    }
    @Override
    public T get(T value) {
      return   get(root, value);
    }

    private T get(Node<T> node, T value) {
        if (node == null) {
            return null;
        }
        int compareResult = comparator.compare(value, node.value);
        if (compareResult == 0) {
           return node.value;
        } else if (compareResult < 0) {
           return get(node.left, value);
        } else {
           return get(node.right, value);
        }

    }

    @Override
    public void add(T value) {
        root = add(root, value);
    }

    private Node<T> add(Node<T> node, T value) {
        if (node == null) {
            return new Node<>(value);
        }

        int compareResult = comparator.compare(value, node.value);
        if (compareResult < 0) {
            node.left = add(node.left, value);
        } else if (compareResult > 0) {
            node.right = add(node.right, value);
        }

        return node;
    }

    @Override
    public void remove(T value) {
        root = remove(root, value);
    }

    @Override
    public boolean contains(T value) {
        if(root==null)return false;
        Node<T> temp=root;
        int compared=comparator.compare(value,root.value);
        while (temp!=null)
        {
           compared=comparator.compare(value,temp.value);
           if(compared==0)return true;
           else if(compared<0)temp=temp.left;
           else temp=temp.right;
        }
        return false;
    }

    private Node<T> remove(Node<T> node, T value) {
        if (node == null) {
            return null;
        }

        int compareResult = comparator.compare(value, node.value);
        if (compareResult < 0) {
            node.left = remove(node.left, value);
        } else if (compareResult > 0) {
            node.right = remove(node.right, value);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                node.value = minValue(node.right);
                node.right = remove(node.right, node.value);
            }
        }
        return node;
    }

    private T minValue(Node<T> node) {
        T minv = node.value;
        while (node.left != null) {
            minv = node.left.value;
            node = node.left;
        }
        return minv;
    }
    public int getHeight()
    {
        return root.getHeight();
    }

    @Override
    public ArrayList<T> getArray() {
        if(root==null)return null;
        ArrayList<T> list=new ArrayList<>();
        root.placeInArray(list);
        return list;
    }

    @Override
    public ArrayList<T> getArrayTreeOrder() {
        if(root==null)return null;
        int size=(int)(Math.pow(2,root.getHeight()+1)-0.5);
        ArrayList<T> list=new ArrayList<>();
        for (int i=0; i<size; i++)list.add(null);
        root.placeInArray(list,0);
        return list;
    }

    @Override
    public ArrayList<T> getBroad() {
        if(root==null)return null;
        int size=(int)(Math.pow(2,root.getHeight()+1)-0.5);
        ArrayList<T> result=new ArrayList<>(size);
        root.placeInArrayWszerz(result,new LinkedList<T>());
        return result;
    }
}
