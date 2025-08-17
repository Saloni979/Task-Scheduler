package com.scheduler.service;

import com.scheduler.enums.Periodic;
import com.scheduler.model.TaskRequest;
import com.scheduler.util.CronGenerator;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.TaskScheduler;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

@Service
public class TaskServiceImpl implements TaskService {


    private final CronGenerator cronGenerator;
    private String cronValue;
    private final TaskScheduler taskScheduler;
    private ScheduledFuture<?> scheduledFuture;
    private final ReentrantLock reentrantLock=new ReentrantLock();

    @Autowired
    public TaskServiceImpl(final CronGenerator cronGenerator, TaskScheduler taskScheduler) {
        this.cronGenerator = cronGenerator;
        this.taskScheduler = taskScheduler;
    }

    @Override
    public void schedulerTask(TaskRequest request) {
        String type = String.valueOf(Periodic.valueOf(request.getType().toUpperCase()));
        cronValue = cronGenerator.buildCronExpression(type, request.getHour(), request.getMinute(), request.getDayOfWeek(), request.getDayOfMonth(), request.getMonth());
        scheduleDynamicTask(cronValue);
    }

    public String getCronExpression() {
        return cronValue;
    }

    private void scheduleDynamicTask(String cronExpression) {

        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(false); // cancel previous if exists
        }

        scheduledFuture = taskScheduler.schedule(() -> {
            try {
                reentrantLock.lock();
                System.out.println("Running scheduled task at: " + System.currentTimeMillis());
                System.out.println("[TASK RUNNING] Timestamp: " + new java.util.Date());

            } finally {
                reentrantLock.unlock();
            }
        }, new CronTrigger(cronExpression));
    }


}
