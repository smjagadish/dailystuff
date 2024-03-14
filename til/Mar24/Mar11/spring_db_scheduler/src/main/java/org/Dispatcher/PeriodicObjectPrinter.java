package org.Dispatcher;

import com.github.kagkarlsson.scheduler.Scheduler;
import com.github.kagkarlsson.scheduler.serializer.JacksonSerializer;
import com.github.kagkarlsson.scheduler.task.ExecutionContext;
import com.github.kagkarlsson.scheduler.task.TaskInstance;
import com.github.kagkarlsson.scheduler.task.VoidExecutionHandler;
import com.github.kagkarlsson.scheduler.task.helper.RecurringTask;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;
import com.github.kagkarlsson.scheduler.task.schedule.FixedDelay;
import org.Data.clientData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

// Recurring task that runs every 5 minutes with custom data (user defined object)
// an initial seed value/data is provided and this is used for the first execution
// we use stateless execution , so each run doesn't carry forward state info from previous run
// jackson serializer is used. also possible to use native java serializer (in which case implement the serializable interface)
@Configuration
public class PeriodicObjectPrinter {
    // recurring task with custom object
    private RecurringTask<clientData> task;
    // dedicated scheduler instance
    private final Scheduler scheduler;
    private static final Logger logger = LoggerFactory.getLogger(PeriodicObjectPrinter.class);
    public PeriodicObjectPrinter(final DataSource ds)
    {
        this.task = Tasks.recurring("print-object-task", FixedDelay.ofMinutes(5), clientData.class)
                .initialData(new clientData(10,"first_client"))
                .execute(new VoidExecutionHandler<clientData>() {
                    @Override
                    public void execute(TaskInstance<clientData> taskInstance, ExecutionContext executionContext) {
                        logger.info("printing object content every 5 mins with id:"+taskInstance.getData().getClient_id() + "and client name:"+taskInstance.getData().getClient_name());
                    }
                });
        // using jackson serializer
        this.scheduler = Scheduler
                .create(ds)
                .threads(1)
                .startTasks(task)
                .serializer(new JacksonSerializer())
                .build();
    }
    @PostConstruct
    public void doTask()
    {
        scheduler.start();
    }
}
