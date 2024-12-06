package com.jakeodell.todolist.controllers;

import com.jakeodell.todolist.models.Task;
import com.jakeodell.todolist.services.TaskServices;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    private final TaskServices taskServices;

    public TaskController(TaskServices taskServices) {
        this.taskServices = taskServices;
    }


    @GetMapping
    public Iterable<Task> findAllTasks() {
        return taskServices.findAllTasks();
    }

    @PostMapping("/new-task")
    public void createTask(@RequestBody Task task) {
        taskServices.saveTask(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskServices.deleteTaskById(id);
    }

}
