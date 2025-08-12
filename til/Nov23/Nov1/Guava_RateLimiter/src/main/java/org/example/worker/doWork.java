package org.example.worker;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class doWork implements Callable<String> {
    private  RateLimiter rl;
    public doWork(RateLimiter rl)
    {
        this.rl = rl;
    }
    @Override
    public String call() throws Exception {
        //Thread.sleep(10000);
        double bl = rl.acquire(1);
        // use the below instead to control how much of a delay is acceptable in terms of being blocked at the mercy of the lib to get the token
        //boolean result = rl.tryAcquire(1, 1000, TimeUnit.MILLISECONDS);
        //System.out.println("Thread_id"+ Thread.currentThread().getId()+": with token result:"+result);
        System.out.println("Thread_id"+ Thread.currentThread().getId()+":waited for:"+bl);

        while(true)
        {
            // do nothing as i illustrate that some threads will block on rl.acquire due to less tokens per sec than the number of threads that start

        }
    }
}
