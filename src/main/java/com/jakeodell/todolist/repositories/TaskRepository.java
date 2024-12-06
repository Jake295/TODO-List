package com.jakeodell.todolist.repositories;

import com.jakeodell.todolist.models.Task;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

    //TODO: Add createTask, atlerTask, deleteTask

    @Query("SELECT * FROM task")
    List<Task> findAllTasks();

}
