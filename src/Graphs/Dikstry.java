package Graphs;

import java.util.*;

public class Dikstry {
    public static  <T>HashMap<T,Integer> getDikstry(Graph<T> graph,T key)
    {
        HashMap<T,Graph.Contener<T>> map=graph.map;
        if(!map.containsKey(key))throw new NoSuchElementException("uknown key");
        HashMap<T,Integer> pathMap=new HashMap<>();
        HashSet<T> visited=new HashSet<>();
        HashMap<T,Integer> priority=new HashMap<>();
        LinkedList<T> visitingQueue=new LinkedList<>();
        for (T keys : map.keySet())//dodawanie kluczy do pathmap
        {
            pathMap.put(keys,-1);
        }

        pathMap.put(key,0);
        visitingQueue.addFirst(key);

        while (!visitingQueue.isEmpty())
        {
            T removed=visitingQueue.removeFirst();
            if(visited.contains(removed))continue;
            visited.add(removed);
            visitDikstry(removed,priority,visited,visitingQueue,pathMap,map);
        }

        return pathMap;

    }
    private static <T>void visitDikstry(T startKey,HashMap<T,Integer>priority,HashSet<T> visited,LinkedList<T> visitingQueue,HashMap<T,Integer> pathMap,HashMap<T,Graph.Contener<T>> map)
    {
        Iterator<T> keyOutIterator=map.get(startKey).getOutList().iterator();
        Iterator<Integer> valueOutIterator=map.get(startKey).getValueList().iterator();

        visited.add(startKey);

        int toStartKeyPath=Math.max(pathMap.get(startKey),0);
        while (keyOutIterator.hasNext() && valueOutIterator.hasNext() )
        {
            T key=keyOutIterator.next();
            int value=valueOutIterator.next();

            if(pathMap.get(key)<0 || pathMap.get(key)>(value+toStartKeyPath))
            {
                pathMap.put(key,value+toStartKeyPath);
            }
            if(!visited.contains(key))
            {
                priority.put(key,value+toStartKeyPath);
                addToList(key,visitingQueue,priority);
            }
        }
    }
    private static <T>void addToList(T key,LinkedList<T> list,HashMap<T,Integer> priority)
    {
        if(!priority.containsKey(key))throw new RuntimeException("priority problem");
        int index=0;
        for (T actualList : list)
        {
            if(priority.containsKey(actualList))
            {
                if(priority.get(key).compareTo(priority.get(actualList))<0)break;
            }else break;
            index++;
        }
        list.add(index,key);
    }
}
