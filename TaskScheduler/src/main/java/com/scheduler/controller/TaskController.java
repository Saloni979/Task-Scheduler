package com.scheduler.controller;

import com.scheduler.model.TaskRequest;
import com.scheduler.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(final TaskService taskService){
        this.taskService=taskService;
    }


    @PostMapping
    public ResponseEntity<?> scheduleTask(@RequestBody TaskRequest request){
        this.taskService.schedulerTask(request);
        return ResponseEntity.ok().build();

    }



}
