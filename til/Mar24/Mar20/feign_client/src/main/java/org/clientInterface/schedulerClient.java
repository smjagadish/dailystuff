package org.clientInterface;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.clientConfig.schedulerConfig;
import org.clientFallback.schedulerFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

// declarative feign client towards the db-scheduler server
// feign client configuration is coded in the schedulerConfig class file
@FeignClient(name= "scheduler-feign-client",url = "http://127.0.0.1:8766",configuration = schedulerConfig.class )
public interface schedulerClient {
    @GetMapping("/schedules")
    public Set<String> getSchedules();
    @PostMapping("/")
    default void defaultfb()
    {
        System.out.println("crashed");
    }

}
