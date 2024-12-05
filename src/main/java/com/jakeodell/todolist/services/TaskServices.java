package com.jakeodell.todolist.services;

import com.jakeodell.todolist.models.Task;
import com.jakeodell.todolist.repositories.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskServices {

    private final TaskRepository taskRepository;

    public TaskServices(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }
}