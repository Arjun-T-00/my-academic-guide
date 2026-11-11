
package com.academicguide;

import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PdfRepository {

    private final DataSource dataSource;

    public PdfRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Save PDF details for a particular student
    public void save(Pdf pdf, int studentId) {

        String sql = """
            INSERT INTO pdfs (title, file_name, subject_id)
            SELECT ?, ?, id FROM subjects
            WHERE id = ? AND student_id = ?
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, pdf.getTitle());
            statement.setString(2, pdf.getFileName());
            statement.setInt(3, pdf.getSubjectId());
            statement.setInt(4, studentId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all PDFs belonging to a particular student
    public List<Pdf> findAll(int studentId) {

        List<Pdf> pdfs = new ArrayList<>();

        String sql = """
            SELECT p.* FROM pdfs p
            JOIN subjects s ON p.subject_id = s.id
            WHERE s.student_id = ?
            ORDER BY p.uploaded_at DESC
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {
                    Pdf pdf = new Pdf(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getString("file_name"),
                        result.getInt("subject_id")
                    );

                    pdfs.add(pdf);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pdfs;
    }

    // Get PDFs belonging to one of the student's subjects
    public List<Pdf> findBySubjectId(int subjectId, int studentId) {

        List<Pdf> pdfs = new ArrayList<>();

        String sql = """
            SELECT p.* FROM pdfs p
            JOIN subjects s ON p.subject_id = s.id
            WHERE p.subject_id = ? AND s.student_id = ?
            ORDER BY p.uploaded_at DESC
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, subjectId);
            statement.setInt(2, studentId);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {
                    Pdf pdf = new Pdf(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getString("file_name"),
                        result.getInt("subject_id")
                    );

                    pdfs.add(pdf);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pdfs;
    }

    // Find a PDF only if it belongs to the student
    public Pdf findById(int id, int studentId) {

        String sql = """
            SELECT p.* FROM pdfs p
            JOIN subjects s ON p.subject_id = s.id
            WHERE p.id = ? AND s.student_id = ?
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.setInt(2, studentId);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    return new Pdf(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getString("file_name"),
                        result.getInt("subject_id")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Delete a PDF only if it belongs to the student
    public void deleteById(int id, int studentId) {

        String sql = """
            DELETE p FROM pdfs p
            JOIN subjects s ON p.subject_id = s.id
            WHERE p.id = ? AND s.student_id = ?
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.setInt(2, studentId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update PDF title only if it belongs to the student
    public void update(Pdf pdf, int studentId) {

        String sql = """
            UPDATE pdfs p
            JOIN subjects s ON p.subject_id = s.id
            SET p.title = ?
            WHERE p.id = ? AND s.student_id = ?
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, pdf.getTitle());
            statement.setInt(2, pdf.getId());
            statement.setInt(3, studentId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
