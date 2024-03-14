package org.Dispatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class threadPoolConfig {
    @Bean
    public ThreadPoolTaskScheduler configThreadPool()
    {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10); // Set the pool size
        // Other custom configurations for the task scheduler
        return taskScheduler;
    }
}
