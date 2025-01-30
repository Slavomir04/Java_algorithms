package Tree;

import java.util.Comparator;

public class TreeRB<T> {

   private final boolean RED=true;
   private final boolean BLACK=false;
   private final Node _null=new Node();

   private class Node
   {
      public Node right;
      public Node left;
      public Node parent;
      boolean Color; //true czerwony, false czarny
      private T value;

      public Node(T value)
      {
         Color=RED;
         this.value=value;
         left=_null;
         right=_null;
         parent=_null;
      }
      public Node()//_null konstruktor
      {
         Color=BLACK;
         value=null;
         right=this;
         left=this;
         parent=this;
      }
      public boolean add(Node element)
      {
         int compared=comparator.compare(this.value,element.value);
         if(compared>0)
         {
            if(right!=_null)return right.add(element);
            else{
               right=element;
               element.parent=this;
               return true;
            }
         }else if(compared<0)
         {
            if(left!=_null)return left.add(element);
            else{
               left=element;
               element.parent=this;
               return true;
            }
         }else return false;
      }
      public Node find(T value)
      {
         int compared=comparator.compare(this.value,value);
         if(compared<0)
         {
            if(right!=_null)return right.find(value);
            else return _null;
         }else if(compared>0)
         {
            if(left!=_null)return left.find(value);
            else return _null;
         }else
         {
            return this;
         }
      }
      public void placeInArrayString(String[] array, int index)
      {
         array[index]=toString();
         int leftIndex=index*2+1;
         int rightIndex=leftIndex+1;

         if(left!=_null)left.placeInArrayString(array,leftIndex);
         if(right!=_null)right.placeInArrayString(array,rightIndex);
      }
      public int Height()
      {
         int max=0;
         if(left!=_null)max=left.Height()+1;
         if(right!=_null)
         {
            int temp= right.Height()+1;
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
   private Comparator<T> comparator;
   private Node root;
   public TreeRB(Comparator<T> comparator) {
      this.comparator = comparator;
   }
   private void RightRotate(Node x)
   {
      Node y = x.right;
      x.right = y.left;
      if (y.left != _null) {
         y.left.parent = x;
      }
      y.parent = x.parent;
      if (x.parent == null) {
         this.root = y;
      } else if (x == x.parent.left) {
         x.parent.left = y;
      } else {
         x.parent.right = y;
      }
      y.left = x;
      x.parent = y;
      if(x==root)root=y;
   }
   private void LeftRotate(Node y)
   {
      Node x = y.left;
      y.left = x.right;
      if (x.right != _null) {
         x.right.parent = y;
      }
      x.parent = y.parent;
      if (y.parent == null) {
         this.root = x;
      } else if (y == y.parent.right) {
         y.parent.right = x;
      } else {
         y.parent.left = x;
      }
      x.right = y;
      y.parent = x;

      if(y==root)root=x;
   }

   public void add(T value)
   {
      if(root==null)
      {
         root=new Node(value);
         root.Color=false;
         return;
      }
      Node element=new Node(value);

      if(!root.add(element))return;

      Node uncle;

      while (element.parent.Color==RED)
      {
         if(element.parent == element.parent.parent.right) // ojciec prawy
         {
            uncle=element.parent.parent.left;

            if(uncle.Color==RED)
            {
               uncle.Color=BLACK;
               element.parent.Color=BLACK;

               element.parent.parent.Color=RED;
               element=element.parent.parent; //wyjscie
            }else
            {
               if(element==element.parent.left)
               {
                  element=element.parent;
                  LeftRotate(element);
               }
               element.parent.Color=BLACK;
               element.parent.parent.Color=RED;
               RightRotate(element.parent.parent);
            }
         }else{//ojciec lewy
            uncle=element.parent.parent.right;

            if(uncle.Color==RED)
            {
               uncle.Color=BLACK;
               element.parent.Color=BLACK;

               element.parent.parent.Color=RED;
               element=element.parent.parent;
            }else //wujek czarny
            {
               if(element==element.parent.right)
               {
                  element=element.parent;
                  RightRotate(element);
               }
               element.parent.Color=BLACK;
               element.parent.parent.Color=RED;
               LeftRotate(element.parent.parent);
            }

         }
         if(element==root)break;
      }
      root.Color=BLACK;
   }
   public void TestRotate(T value,boolean direction)
   {
      Node temp=root.find(value);
      if(temp==_null)return;

      if(direction) LeftRotate(temp);
      else RightRotate(temp);
   }
   public String[] getString()
   {
      if(root==_null || root==null)return null;
      else {
         int size=(int)(Math.pow(2,root.Height()+1));
         String[] result=new String[size];
         root.placeInArrayString(result,0);
         return result;
      }
   }

}
