package org.App;

import com.github.kagkarlsson.scheduler.Scheduler;
import com.github.kagkarlsson.scheduler.task.ExecutionContext;
import com.github.kagkarlsson.scheduler.task.ExecutionHandler;
import com.github.kagkarlsson.scheduler.task.TaskInstance;
import com.github.kagkarlsson.scheduler.task.VoidExecutionHandler;
import com.github.kagkarlsson.scheduler.task.helper.RecurringTask;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;
import com.github.kagkarlsson.scheduler.task.schedule.FixedDelay;
import org.Data.clientData;
import org.Dispatcher.taskDispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.util.concurrent.RecursiveTask;

@SpringBootApplication
@EnableScheduling
@ComponentScan("org.Dispatcher")
@ComponentScan("org.taskDefinition")
@ComponentScan("org.taskExecutor")
public class appStart {
    private static Logger logger = LoggerFactory.getLogger(appStart.class);
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(appStart.class,args);
        logger.info("application started");
        taskDispatch td = ctx.getBean(taskDispatch.class);
        td.doTask();

    }
}