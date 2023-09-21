package com.taskmanger.app.tm.services;

import com.taskmanger.app.tm.payload.TaskDto;

import java.util.List;

public interface TaskServices {
    TaskDto addNewTask(TaskDto task);
    List<TaskDto> viewTask();

    boolean toggleCompletion(long id);
}
