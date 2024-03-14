package org.taskDefinition;

import com.github.kagkarlsson.scheduler.task.*;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;
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

        Task<?>[] task_array = new Task<?>[2];

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

        System.out.println("bean is created");
        return task_array;
    }
}
