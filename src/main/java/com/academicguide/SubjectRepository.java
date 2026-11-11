
package com.academicguide;

import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubjectRepository {

    private final DataSource dataSource;

    public SubjectRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Add a new subject
    public void save(Subject subject, int studentId) {

        String sql = "INSERT INTO subjects (name, student_id) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, subject.getName());
            statement.setInt(2, studentId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all subjects belonging to a student
    public List<Subject> findAll(int studentId) {

        List<Subject> subjects = new ArrayList<>();

        String sql = "SELECT * FROM subjects WHERE student_id = ? ORDER BY name";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {
                    Subject subject = new Subject(
                            result.getInt("id"),
                            result.getString("name")
                    );

                    subjects.add(subject);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjects;
    }

    // Update subject name
    public void update(Subject subject, int studentId) {

        String sql = "UPDATE subjects SET name = ? WHERE id = ? AND student_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, subject.getName());
            statement.setInt(2, subject.getId());
            statement.setInt(3, studentId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete subject
    public void deleteById(int id, int studentId) {

        String sql = "DELETE FROM subjects WHERE id = ? AND student_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.setInt(2, studentId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Find subject by ID
    public Subject findById(int id, int studentId) {

        String sql = "SELECT * FROM subjects WHERE id = ? AND student_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.setInt(2, studentId);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    return new Subject(
                            result.getInt("id"),
                            result.getString("name")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
