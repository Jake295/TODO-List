package com.jakeodell.todolist.controllers;

import com.jakeodell.todolist.models.Task;
import com.jakeodell.todolist.services.TaskServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final TaskServices taskServices;

    public TaskController(TaskServices taskServices) {
        this.taskServices = taskServices;
    }


    @GetMapping
    public Iterable<Task> findAllTasks() {
        logger.info("Finding all tasks...");
        Iterable<Task> tasks = taskServices.findAllTasks();
        logger.info("Number of tasks found: {}", (tasks != null) ? tasks.spliterator().estimateSize() : 0);
        return tasks;
    }

    @PostMapping("/new-task")
    public void createTask(@RequestBody Task task) {
        logger.info("Creating new task with title: {}", task.getTitle());
        taskServices.saveTask(task);
        logger.info("Task created successfully.");
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        logger.info("Deleting task with id: {}", id);
        taskServices.deleteTaskById(id);
        logger.info("Task deleted successfully.");
    }

}
