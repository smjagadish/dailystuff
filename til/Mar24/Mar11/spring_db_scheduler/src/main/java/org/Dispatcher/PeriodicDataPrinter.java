package org.Dispatcher;

import com.github.kagkarlsson.scheduler.Scheduler;
import com.github.kagkarlsson.scheduler.task.ExecutionContext;
import com.github.kagkarlsson.scheduler.task.StateReturningExecutionHandler;
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

// Recurring task that runs every minute with Integer data
// an initial seed value/data is provided and this is used for the first execution
// we use stateful execution to make each execution return a value which is fed into the next execution
// perfect use case for tracking the success of an execution for example
@Configuration
public class PeriodicDataPrinter {
    // task with integer data passed to it
    private RecurringTask<Integer> data_task;
    // dedicated scheduler instance
    private final Scheduler scheduler;
    private static final Logger logger = LoggerFactory.getLogger(PeriodicDataPrinter.class);
    public PeriodicDataPrinter(final DataSource ds)
    {
        // recurring task with int data and stateful execution

        this.data_task = Tasks.recurring("print-data-task", FixedDelay.ofMinutes(1),Integer.class)
                .initialData(88)
                .executeStateful(new StateReturningExecutionHandler<Integer>() {
                    @Override
                    public Integer execute(TaskInstance<Integer> taskInstance, ExecutionContext executionContext) {
                        logger.info("the value being printed is:"+taskInstance.getData());
                        Integer val = taskInstance.getData();
                        return val+1;
                    }
                });
             /*   .execute(new VoidExecutionHandler<Integer>() {
                    @Override
                    public void execute(TaskInstance<Integer> taskInstance, ExecutionContext executionContext) {
                        logger.info("6 min task with seed data = "+taskInstance.getData());
                    }
                });*/
        // postgres based scheduler that starts the task defined above. this scheduler is set with just 1 thread
        this.scheduler = Scheduler
                .create(ds)
                .threads(1)
                .startTasks(data_task)
                .build();
    }
    @PostConstruct
    public void doTask()
    {
        scheduler.start();
    }

}
