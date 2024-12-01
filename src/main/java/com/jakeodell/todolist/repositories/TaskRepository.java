package com.jakeodell.todolist.repositories;

import com.jakeodell.todolist.models.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepository {

    private final JdbcTemplate jdbc;

    public TaskRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    //TODO: Create atlerTask, deleteTask

    public void storeTask(Task task) {
        String sql = "INSERT INTO tasks (title, description, complete, due_date) VALUES (?, ?, ?, ?)";
        jdbc.update(sql, task.getTitle(), task.getDescription(), task.isComplete(), task.getDue_date());
    }

    public List<Task> findAllTasks() {
        String sql = "SELECT * FROM tasks";

        RowMapper<Task> taskRowMapper = (r, i) -> {
            Task rowObject = new Task();
            rowObject.setId(r.getInt("id"));
            rowObject.setTitle(r.getString("title"));
            rowObject.setDescription(r.getString("description"));
            rowObject.setComplete(r.getBoolean("complete"));
            rowObject.setDue_date(r.getDate("date"));
            return rowObject;
        };

        return jdbc.query(sql, taskRowMapper);
    }
}
