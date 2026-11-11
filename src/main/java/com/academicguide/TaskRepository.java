
package com.academicguide;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository {

    private final DataSource dataSource;

    public TaskRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Save a new task
    public void save(Task task, int studentId) throws Exception {

        String sql = "INSERT INTO tasks (title, description, student_id) VALUES (?, ?, ?)";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setInt(3, studentId);

            ps.executeUpdate();
        }
    }

    // Retrieve tasks belonging to a student
    public List<Task> findAll(int studentId) throws Exception {

        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT * FROM tasks WHERE student_id = ? ORDER BY id DESC";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Task task = new Task();

                    task.setId(rs.getInt("id"));
                    task.setTitle(rs.getString("title"));
                    task.setDescription(rs.getString("description"));
                    task.setCompleted(rs.getBoolean("completed"));

                    tasks.add(task);
                }
            }
        }

        return tasks;
    }

    // Delete a student's task
    public void deleteById(int id, int studentId) throws Exception {

        String sql = "DELETE FROM tasks WHERE id = ? AND student_id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, studentId);

            ps.executeUpdate();
        }
    }

    // Mark a task as completed or pending
    public void toggleCompleted(int id, int studentId) throws Exception {

        String sql = "UPDATE tasks SET completed = NOT completed WHERE id = ? AND student_id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, studentId);

            ps.executeUpdate();
        }
    }

    // Find a task using its ID
    public Task findById(int id, int studentId) throws Exception {

        String sql = "SELECT * FROM tasks WHERE id = ? AND student_id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, studentId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Task task = new Task();

                    task.setId(rs.getInt("id"));
                    task.setTitle(rs.getString("title"));
                    task.setDescription(rs.getString("description"));
                    task.setCompleted(rs.getBoolean("completed"));

                    return task;
                }
            }
        }

        return null;
    }

    // Update an existing task
    public void update(Task task, int studentId) throws Exception {

        String sql = "UPDATE tasks SET title = ?, description = ? WHERE id = ? AND student_id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setInt(3, task.getId());
            ps.setInt(4, studentId);

            ps.executeUpdate();
        }
    }
}
