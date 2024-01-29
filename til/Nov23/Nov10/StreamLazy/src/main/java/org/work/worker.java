package org.work;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class worker {
    public void doWork()
    {
      // simple stream from an array list
        List<Integer> iList = new ArrayList<>();
        for (int i=0;i<10;i++)
            iList.add(i);
        System.out.println(iList);
        // returns a stream , but original collection is still unmodified
        Stream<Integer> istream = iList.stream().map(e->++e);
        // no change to original collection
        iList.stream().forEach(e->System.out.println(e));
        //istream.forEach(e->System.out.println(e));
        // create a map from the returned stream . the value will be +1 incr for each element
       Map<String ,Integer> mmap= istream.collect(Collectors.toMap(k->"key"+k, v->v));
       // the above is a terminal ops so istream can't be used further
       // mmap can't be used further as well since we enforce a terminal ops
       mmap.entrySet().forEach(e->System.out.println(e.getKey()+":"+e.getValue()));
       // an example of creating an immutable list from the stream
       // there are multiple ways to do this - pre-java8 , using guava , native api in java11+ etc. below is native java11+
      final List<Integer> unmodif_list =  iList.stream().collect(Collectors.toUnmodifiableList());
      // below will fail , so commenting it
      // unmodif_list.add(7);
      // creating a synchronized list
      // this list will implicitly provided synchronized access to mutating methods. more useful when there is concurrent access
        List<Integer> sync_list = Collections.synchronizedList(new ArrayList<>());
        sync_list.add(1);
        Queue<Integer> q = new LinkedList<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        BlockingQueue<Integer> bq = new LinkedBlockingQueue<>();
        Stack<Integer> st = new Stack<>();
        SortedSet<Integer> ss ;
    }
}
