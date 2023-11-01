package org.example.main;

import com.google.common.util.concurrent.RateLimiter;
import org.example.worker.doWork;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
            for(int i=0;i<10;i++) {
                es.submit(new doWork(rl));
            }
        }
    }
}