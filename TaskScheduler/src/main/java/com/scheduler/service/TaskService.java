package com.scheduler.service;

import com.scheduler.model.TaskRequest;

public interface TaskService {
    void schedulerTask(TaskRequest request);
}
