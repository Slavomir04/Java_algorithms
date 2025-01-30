package Graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.lang.reflect.Array;

public class DFS {

    public static <T>T[] getDFS(Graph<T> graph ,T key)
    {
        HashMap<T,Graph.Contener<T>> map=graph.map;
        if(!map.containsKey(key))throw new NoSuchElementException();
        HashMap<T,Integer> colorMap=new HashMap<>();
        for (T e : map.keySet())colorMap.put(e,0);
        LinkedList<T> qList=new LinkedList<>();

        visitDFS(key,qList,colorMap,map);

        T[] newArray = (T[]) Array.newInstance(key.getClass(), qList.size());
        int i=0;
        for (T e : qList)newArray[i++]=e;
        return newArray;
    }
    private static  <T>void visitDFS(T key, LinkedList<T> qList, HashMap<T,Integer> colorMap,HashMap<T,Graph.Contener<T>> map)
    {
        colorMap.put(key,1);
        qList.addLast(key);
        LinkedList<T> outList=map.get(key).outList;
        for (T actualKey : outList)
        {
            if(colorMap.get(actualKey)==0) visitDFS(actualKey,qList,colorMap,map);

        }
    }
}
