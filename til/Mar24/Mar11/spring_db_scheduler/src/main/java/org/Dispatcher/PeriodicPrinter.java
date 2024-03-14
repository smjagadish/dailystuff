package org.Dispatcher;

import com.github.kagkarlsson.scheduler.Scheduler;
import com.github.kagkarlsson.scheduler.task.ExecutionContext;
import com.github.kagkarlsson.scheduler.task.TaskInstance;
import com.github.kagkarlsson.scheduler.task.VoidExecutionHandler;
import com.github.kagkarlsson.scheduler.task.helper.RecurringTask;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;
import com.github.kagkarlsson.scheduler.task.schedule.FixedDelay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.xml.crypto.Data;

// Recurring task that runs every 10 minutes with no data
// we use stateless execution , so each run doesn't carry forward state info from previous run

@Configuration
public class PeriodicPrinter {
    private RecurringTask<Void> print_task;
    final Scheduler scheduler;
    private static final Logger logger = LoggerFactory.getLogger(PeriodicPrinter.class);
    public PeriodicPrinter(final DataSource ds)
    {
        this.print_task = Tasks.recurring("print-task", FixedDelay.ofMinutes(10))
                .onDeadExecutionRevive()
                .onFailureReschedule()
                .execute(new VoidExecutionHandler<Void>() {
                    @Override
                    public void execute(TaskInstance<Void> taskInstance, ExecutionContext executionContext) {
                          logger.info("10 minute exec to print i'm alive");
                    }
                });
        this.scheduler = Scheduler
                .create(ds)
                .startTasks(print_task)
                .threads(5)
                .build();
    }
    @PostConstruct
    public void triggerTask()
    {
        this.scheduler.start();
    }
}
