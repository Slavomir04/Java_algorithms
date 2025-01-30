package Graphs;


import java.util.*;

public class KruskalAlgorithm<T> {


    private static class LinkPath<T>
    {
        T keyOut;
        T keyIn;
        Integer value;

        public LinkPath(T keyOut, T keyIn,Integer value) {
            this.keyOut = keyOut;
            this.keyIn = keyIn;
            this.value=value;
        }
        @Override
        public String toString()
        {
            return keyOut +"->"+keyIn+" : "+value;
        }
    }
    private static class Forest<T>
    {
        LinkedList<LinkPath<T>> paths;
        HashSet<T> points;

        public Forest(LinkPath<T> path) {
            paths=new LinkedList<>();
            points=new HashSet<>();
            add(path);
        }
        public boolean contains(T key)
        {
            return points.contains(key);
        }
        public void add(LinkPath<T> path)
        {
            paths.addLast(path);
            points.add(path.keyIn);
            points.add(path.keyOut);
        }
        public void merge(Forest<T> forest,LinkPath<T> path)
        {
            for (LinkPath<T> e : forest.paths)add(e);
            add(path);
        }
    }

    private ArrayList<Forest<T>> forests=new ArrayList<>();
    public void generate(Graph<T> graph) {
        ArrayList<LinkPath<T>> queue = new ArrayList<>(graph.getMap().size());
        for (T key : graph.getMap().keySet()) {
            Iterator<T> outIt = graph.getMap().get(key).outList.iterator();
            Iterator<Integer> integerIt = graph.getMap().get(key).valueList.iterator();
            while (outIt.hasNext() && integerIt.hasNext()) {
                queue.add(new LinkPath<>(key, outIt.next(), integerIt.next()));
            }
        }
        queue.sort(new Comparator<LinkPath<T>>() {
            @Override
            public int compare(LinkPath<T> o1, LinkPath<T> o2) {
                return o1.value.compareTo(o2.value);
            }
        });

        for (int index = 0; index < queue.size(); index++)
        {
            LinkPath<T> path=queue.get(index);

            boolean contains=false;
            for (int i=0; i<forests.size(); i++)
            {
                boolean a = forests.get(i).contains(path.keyIn);
                boolean b =forests.get(i).contains(path.keyOut);
                if(a != b)
                {
                    contains=true;
                    boolean merged=false;
                    for (int y=i+1; y<forests.size(); y++)
                    {
                        if(forests.get(y).contains(path.keyIn) || forests.get(y).contains(path.keyOut))
                        {
                            merged=true;
                            forests.get(i).merge(forests.get(y),path);
                            forests.remove(y);
                            break;
                        }
                    }
                    if(!merged)forests.get(i).add(path);
                    break;
                }else if(a)
                {
                    contains=true;
                    break;
                }
            }


            if(!contains)forests.add(new Forest<>(path));
        }
    }

    public ArrayList<Graph<T>> getArray()
    {
        ArrayList<Graph<T>> result=new ArrayList<>();
        for (Forest<T> forest : forests)
        {
            result.add(transform(forest));
        }

        return result;
    }

    private static <T> Graph<T> transform(Forest<T> forest)
    {
        Graph<T> graph=new Graph<>();
        for(LinkPath<T> path : forest.paths)
        {
            graph.add(path.keyOut);
            graph.addToKey(path.keyOut, path.keyIn, path.value);
        }
        return graph;
    }
}
