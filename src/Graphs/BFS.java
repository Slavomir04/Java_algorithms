package Graphs;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.LinkedList;

public class BFS {

    public static <T> T[] getBfs(Graph<T> graph,T key)
    {
        HashMap<T,Graph.Contener<T>> map=graph.map;
        HashMap<T,Boolean> visitedMap=new HashMap<>();
        LinkedList<T> qList=new LinkedList<>();
        qList.addLast(key);
        LinkedList<T> list=new LinkedList<>();
        for (T e : map.keySet())visitedMap.put(e, false);
        while (!qList.isEmpty())
        {
            T k = qList.removeFirst();
            if(!visitedMap.get(k))
            {
              visitedMap.put(k,true);
              list.addLast(k);
              for (T e : map.get(k).outList)list.addLast(e);
            }
        }

        T[] newArray = (T[]) Array.newInstance(key.getClass(), list.size());
        int i=0;
        for (T e : list)newArray[i++]=e;
        return newArray;
    }
}
