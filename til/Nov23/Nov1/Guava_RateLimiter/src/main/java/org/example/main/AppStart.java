package org.example.main;

import com.google.common.util.concurrent.RateLimiter;
import com.sun.source.doctree.SeeTree;
import org.example.worker.doWork;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppStart {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Stating the app!");
        inner inner_obj = new inner();
     //   Thread.sleep(10000);
        inner_obj.startWork();
    }
    private static class inner{
        RateLimiter rl;
        private inner()
        {
            // 4 tokens per sec and internally guava smoothens this to 1 token per 0.25 sec
          rl = RateLimiter.create(4);
        }
       void startWork()
        {
            ExecutorService es = Executors.newFixedThreadPool(10);
            HashSet<Integer> iset = (HashSet<Integer>) Stream.of(new doWork(rl)).map(eg->new Integer(4)).collect(Collectors.toSet());
            System.out.println(iset.size());
            Stream.of(1,2,3).collect(Collectors.partitioningBy(i->i % 2==0));
            Stream.generate(new Supplier<Integer>() {
                @Override
                public Integer get() {
                    int i=0;
                    return ++i;
                }
            }).limit(5).forEach(e->System.out.println(e));
            for(int i=0;i<10;i++) {
                es.submit(new doWork(rl));
            }
        }
    }
}