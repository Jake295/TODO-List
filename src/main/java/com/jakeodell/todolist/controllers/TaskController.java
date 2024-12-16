package com.jakeodell.todolist.controllers;

import com.jakeodell.todolist.models.Task;
import com.jakeodell.todolist.services.TaskServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final TaskServices taskServices;

    public TaskController(TaskServices taskServices) {
        this.taskServices = taskServices;
    }


    @GetMapping("/tasks")
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

    @DeleteMapping("/del/{id}")
    public void deleteTask(@PathVariable Long id) {
        logger.info("Deleting task with id: {}", id);
        taskServices.deleteTaskById(id);
        logger.info("Task deleted successfully.");
    }

    //UPDATE COMPLETE STATUS
    @PutMapping("/tasks/{id}")
    public void updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        logger.info("Updating task with id: {}", id);
        Task existingTask = taskServices.findTaskById(id);
        if (existingTask != null) {
            if (updatedTask.getTitle() != null) existingTask.setTitle(updatedTask.getTitle());
            if (updatedTask.getNote() != null) existingTask.setNote(updatedTask.getNote());
            if (updatedTask.getDue_date() != null) existingTask.setDue_date(updatedTask.getDue_date());
            if (updatedTask.isComplete() != existingTask.isComplete()) existingTask.setComplete(updatedTask.isComplete());
            taskServices.saveTask(existingTask);
            logger.info("Task updated successfully.");
        } else {
            logger.warn("Task not found to update - task id: {}", id);
        }
    }

}
