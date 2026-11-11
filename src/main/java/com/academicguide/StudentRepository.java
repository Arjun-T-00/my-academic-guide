
package com.academicguide;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {

    private final DataSource dataSource;

    public StudentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Student student) {

        String sql = "INSERT INTO students (name, email, password) VALUES (?, ?, ?)";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getPassword());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Unable to save student", e);
        }
    }

    public Student findByEmail(String email) {

        String sql = "SELECT * FROM students WHERE email = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Student student = new Student();

                    student.setId(rs.getInt("id"));
                    student.setName(rs.getString("name"));
                    student.setEmail(rs.getString("email"));
                    student.setPassword(rs.getString("password"));

                    return student;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to find student", e);
        }

        return null;
    }
}
