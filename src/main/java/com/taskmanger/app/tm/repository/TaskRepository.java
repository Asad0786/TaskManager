package com.taskmanger.app.tm.repository;

import com.taskmanger.app.tm.entity.Task;
import com.taskmanger.app.tm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User merge);
}
