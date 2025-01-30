package Graphs;

import java.util.*;

public class Graph<T> {



    public static class Contener<T>
    {
        T key;
        LinkedList<T> outList;
        LinkedList<Integer> valueList;
        public Contener(T key, LinkedList<T> outList,LinkedList<Integer> valueList) {
            this.key = key;
            this.outList = outList;
            this.valueList=valueList;
        }
        @Override
        public String toString()
        {
            StringBuilder builder=new StringBuilder();
            builder.append("Key: ");
            builder.append(key);
            builder.append('\t');
            builder.append('{');
            Iterator<T> iterator1=outList.iterator();
            Iterator<Integer> iterator2=valueList.iterator();
            while (iterator1.hasNext()&&iterator2.hasNext())
            {
                builder.append('(');
                builder.append(iterator2.next());
                builder.append(',');
                builder.append(iterator1.next());
                builder.append(')');
                builder.append(',');
            }
            builder.append('}');
            return builder.toString();
        }
        public LinkedList<T> getOutList()
        {
            return outList;
        }
        public LinkedList<Integer> getValueList()
        {
            return valueList;
        }

    }

    protected HashMap<T,Contener<T>> map;

    public Graph() {
        map=new HashMap<>();
    }

    public void add(T key, Iterator<T> outKeys,Iterator<Integer> values)
    {
        LinkedList<T> list=new LinkedList<>();
        LinkedList<Integer> listV=new LinkedList<>();
        while (outKeys.hasNext() && values.hasNext())
        {
            T tempkey= outKeys.next();
            Integer tempValue= values.next();
            if(!map.containsKey(tempkey))add(tempkey);

            list.add(tempkey);
            listV.add(tempValue);
        }
        map.put(key,new Contener<T>(key,list,listV));
    }
    public void add(T key)
    {
        if(map.containsKey(key))return;
        LinkedList<T> list=new LinkedList<>();
        LinkedList<Integer> listV=new LinkedList<>();
        map.put(key,new Contener<T>(key,list,listV));
    }
    public void addToKey(T key,T point,Integer value)
    {
        if(!map.containsKey(key))return;
        map.get(key).outList.addLast(point);
        map.get(key).valueList.addLast(value);
        if(!map.containsKey(point))add(point);
    }
    public boolean contains(T key)
    {
        return map.containsKey(key);
    }
    public void Clear()
    {
        map=new HashMap<>();
    }
    public void CopyToTarget(Graph<T> target)
    {
        if(target==null)throw new NullPointerException();
        target.Clear();

        Set<T> keySet=map.keySet();
        for (T key : keySet)
        {
            Iterator<T> outListIt=map.get(key).outList.iterator();
            Iterator<Integer> valueListIt=map.get(key).valueList.iterator();
            target.add(key,outListIt,valueListIt);
        }
    }

    public void show()
    {
        if(map.isEmpty()){
            System.out.println("empty");
            return;
        }
        ArrayList<Contener<T>> list = new ArrayList<>(map.size());
        list.addAll(map.values());

        System.out.println("-----------");
        for (Contener<T> contener : list)
        {
            System.out.println(contener);
        }
        System.out.println("-----------");
    }

    public HashMap<T,Contener<T>> getMap()
    {
        return map;
    }

}
