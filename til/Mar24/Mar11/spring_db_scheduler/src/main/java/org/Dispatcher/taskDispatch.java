package org.Dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class taskDispatch {
    private final TaskScheduler ts;
    private static final Logger logger = LoggerFactory.getLogger(taskDispatch.class);
    public taskDispatch(final TaskScheduler ts)
    {
        this.ts = ts;
        logger.info(ts.toString());
    }
    public void doTask()
    {
        ts.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                logger.info("running a print task");
            }
        },1500);
    }

}
