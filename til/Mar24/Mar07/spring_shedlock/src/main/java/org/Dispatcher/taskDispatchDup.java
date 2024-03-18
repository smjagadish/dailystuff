package org.Dispatcher;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import static java.lang.Thread.sleep;

@Configuration
public class taskDispatchDup {
    private static final Logger logger = LoggerFactory.getLogger(taskDispatchDup.class);
    // using shed lock to acquire redis based lock for task exclusivity
    @Scheduled(fixedRate = 1000 , initialDelay = 20000)
    @SchedulerLock(name="mylock")
    public void taskSend()  {
           logger.info("madhumitha doing math");

    }
}
