package com.taskmanger.app.tm.services.servicesimpl;

import com.taskmanger.app.tm.entity.Task;
import com.taskmanger.app.tm.entity.User;
import com.taskmanger.app.tm.payload.TaskDto;
import com.taskmanger.app.tm.repository.TaskRepository;
import com.taskmanger.app.tm.repository.UserRepository;
import com.taskmanger.app.tm.security.ExtendUser;
import com.taskmanger.app.tm.services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TaskServicesImpl implements TaskServices {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Cacheable("user")
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ExtendUser extendUser = (ExtendUser) authentication.getPrincipal();
        return extendUser.getUser();
    }

    @Autowired
    public TaskServicesImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public TaskDto addNewTask(TaskDto taskDto) {

        User loggedUser = entityManager.merge(getAuthenticatedUser());
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setUser(loggedUser);
        task = taskRepository.save(task);
        return  mapToTaskDto(task);

    }

    @Override
    @Transactional
    public List<TaskDto> viewTask() {

        List<Task> tasks = taskRepository.findByUser(entityManager.merge(getAuthenticatedUser()));
        return tasks.stream().map(task -> mapToTaskDto(task)).collect(Collectors.toList());

    }

    @Override
    @Transactional
    public boolean toggleCompletion(long id) {

        User loggedUser = entityManager.merge(getAuthenticatedUser());
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Task doesn't exists")
        );
        if (!task.getUser().equals(loggedUser))
            throw new RuntimeException("Bad Request");
        task.setComplete(!task.isComplete());
        taskRepository.save(task);
        return task.isComplete();


    }

    private TaskDto mapToTaskDto(Task task){
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setUser(task.getUser().getName());
        return taskDto;

    }
}


