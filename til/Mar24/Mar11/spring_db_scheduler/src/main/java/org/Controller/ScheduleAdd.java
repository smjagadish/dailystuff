package org.Controller;

import com.github.kagkarlsson.scheduler.ScheduledExecutionsFilter;
import com.github.kagkarlsson.scheduler.Scheduler;
import com.github.kagkarlsson.scheduler.SchedulerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.taskExecutor.scheduleCreate;

import java.util.HashSet;
import java.util.Set;

@RestController
public class ScheduleAdd {
    @Autowired
    scheduleCreate sc;

    @GetMapping("/schedules")
    @ResponseStatus(value = HttpStatus.OK,code = HttpStatus.OK)
    public Set<String> getAllSchedules()
    {
         Set<String> task_set = new HashSet<>();
         sc.getScheduler()
                         .fetchScheduledExecutions(ScheduledExecutionsFilter.all(),e-> {
                            task_set.add(e.getTaskInstance().getTaskName());
                         });
        return task_set;

    }
}
