package com.jakeodell.todolist.controllers;

import com.jakeodell.todolist.models.Task;
import com.jakeodell.todolist.repositories.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostMapping
    public void createTask(@RequestBody Task task) {
        taskRepository.storeTask(task);
    }

    @GetMapping
    public List<Task> findTasks() {
        return taskRepository.findAllTasks();
    }
}
