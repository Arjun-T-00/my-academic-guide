
package com.academicguide;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

@Repository
public class NoteRepository {

    private final DataSource dataSource;

    public NoteRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Note note, int studentId) throws Exception {

        String sql = "INSERT INTO notes (title, content, student_id) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, note.getTitle());
            statement.setString(2, note.getContent());
            statement.setInt(3, studentId);

            statement.executeUpdate();
        }
    }

    public List<Note> findAll(int studentId) throws Exception {

        List<Note> notes = new ArrayList<>();

        String sql = "SELECT id, title, content FROM notes WHERE student_id = ? ORDER BY id DESC";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    Note note = new Note();

                    note.setId(result.getInt("id"));
                    note.setTitle(result.getString("title"));
                    note.setContent(result.getString("content"));

                    notes.add(note);
                }
            }
        }

        return notes;
    }

    public void update(Note note, int studentId) throws Exception {

        String sql = "UPDATE notes SET title = ?, content = ? WHERE id = ? AND student_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, note.getTitle());
            statement.setString(2, note.getContent());
            statement.setInt(3, note.getId());
            statement.setInt(4, studentId);

            statement.executeUpdate();
        }
    }

    public Note findById(int id, int studentId) throws Exception {

        String sql = "SELECT id, title, content FROM notes WHERE id = ? AND student_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.setInt(2, studentId);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    Note note = new Note();

                    note.setId(result.getInt("id"));
                    note.setTitle(result.getString("title"));
                    note.setContent(result.getString("content"));

                    return note;
                }
            }
        }

        return null;
    }

    public void deleteById(int id, int studentId) throws Exception {

        String sql = "DELETE FROM notes WHERE id = ? AND student_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.setInt(2, studentId);

            statement.executeUpdate();
        }
    }
}
