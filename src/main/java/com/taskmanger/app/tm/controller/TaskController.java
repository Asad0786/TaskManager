package com.taskmanger.app.tm.controller;


import com.taskmanger.app.tm.entity.Task;
import com.taskmanger.app.tm.payload.TaskDto;
import com.taskmanger.app.tm.services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-manager")
public class TaskController {

    private final TaskServices taskServices;

    @Autowired
    public TaskController(TaskServices taskServices) {
        this.taskServices = taskServices;
    }

    @PostMapping("")
    public ResponseEntity<TaskDto> addNewTask(@RequestBody TaskDto task){
        return ResponseEntity.ok(taskServices.addNewTask(task));
    }

    @GetMapping("")
    public ResponseEntity<List<?>> viewAllTasks(){
        return ResponseEntity.ok(taskServices.viewTask());
    }


}
