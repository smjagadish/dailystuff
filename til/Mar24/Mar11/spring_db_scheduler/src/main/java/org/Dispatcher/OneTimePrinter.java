package org.Dispatcher;

import com.github.kagkarlsson.scheduler.Scheduler;
import com.github.kagkarlsson.scheduler.SchedulerName;
import com.github.kagkarlsson.scheduler.task.ExecutionContext;
import com.github.kagkarlsson.scheduler.task.TaskInstance;
import com.github.kagkarlsson.scheduler.task.VoidExecutionHandler;
import com.github.kagkarlsson.scheduler.task.helper.OneTimeTask;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.time.Instant;

@Configuration
public class OneTimePrinter {
    private OneTimeTask<Void> oneTimeTask;
    private final Scheduler scheduler;
    private static final Logger logger = LoggerFactory.getLogger(OneTimePrinter.class);
    public OneTimePrinter(final DataSource ds)
    {
        this.oneTimeTask = Tasks.oneTime("one-time-print")
                .onFailureRetryLater()
                .execute(new VoidExecutionHandler<Void>() {
                    @Override
                    public void execute(TaskInstance<Void> taskInstance, ExecutionContext executionContext) {
                        logger.info("i'm a one time task without any data");
                    }
                });
        this.scheduler = Scheduler
                .create(ds,oneTimeTask)
                .threads(1)
                .build();
    }
    @PostConstruct
    public void doTask()
    {
        scheduler.start();
        scheduler.schedule(oneTimeTask.instance("1800"), Instant.now().plusSeconds(120));
    }
}
