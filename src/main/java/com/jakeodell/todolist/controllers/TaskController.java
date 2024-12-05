package com.jakeodell.todolist.controllers;

import com.jakeodell.todolist.models.Task;
import com.jakeodell.todolist.services.TaskServices;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/task")
public class TaskController {

    private final TaskServices taskServices;

    public TaskController(TaskServices taskServices) {
        this.taskServices = taskServices;
    }

//    @PostMapping
//    public void createTask(@RequestBody Task task) {
//        taskRepository.storeTask(task);
//    }

    @GetMapping
    public Iterable<Task> findTasks() {
        return taskServices.findAll();
    }
}
