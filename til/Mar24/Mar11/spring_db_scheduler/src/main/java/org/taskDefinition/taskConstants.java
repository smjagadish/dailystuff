package org.taskDefinition;

import com.github.kagkarlsson.scheduler.task.*;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;
import com.github.kagkarlsson.scheduler.task.schedule.FixedDelay;
import com.github.kagkarlsson.scheduler.task.schedule.Schedule;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@Data
public class taskConstants {


    public taskConstants()
    {

    }
    @Bean("taskarray")
    public Task<?>[] returnMap()
    {

        Task<?>[] task_array = new Task<?>[4];

            task_array[0] = Tasks.oneTime("one_time_task_nd")
                    .execute(new VoidExecutionHandler<Void>() {
                        @Override
                        public void execute(TaskInstance<Void> taskInstance, ExecutionContext executionContext) {
                            System.out.println("logging data");
                        }
                    });
            task_array[1] = Tasks.oneTime("one_time_with_data",Integer.class)
                            .execute(new VoidExecutionHandler<Integer>() {
                                @Override
                                public void execute(TaskInstance<Integer> taskInstance, ExecutionContext executionContext) {
                                   System.out.println("logging the data with seed value:"+taskInstance.getData());
                                }
                            });
            task_array[2] = Tasks.recurring("recurring_common_nd", FixedDelay.ofMinutes(4))
                            .execute(new VoidExecutionHandler<Void>() {
                                @Override
                                public void execute(TaskInstance<Void> taskInstance, ExecutionContext executionContext) {
                                   System.out.println("logs from a common recur task with no data");
                                }
                            });
            task_array[3] = Tasks.recurring("recurring_with_int_data",FixedDelay.ofMinutes(15),Integer.class)
                            .initialData(6)
                    .executeStateful(new StateReturningExecutionHandler<Integer>() {
                        @Override
                        public Integer execute(TaskInstance<Integer> taskInstance, ExecutionContext executionContext) {
                            int val = taskInstance.getData();
                            System.out.println("seed value from current exec:"+val);
                            return val+1;
                        }
                    });

        System.out.println("bean is created");
        return task_array;
    }
}
