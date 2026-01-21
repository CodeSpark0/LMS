package repository;

import entity.Student;
import util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class StudentRepository {

    public boolean save(Student student) {
        String userSql = """
            INSERT INTO users (email, full_name, password_hash)
            VALUES (?, ?, ?)
            RETURNING id
        """;

        String studentSql = """
            INSERT INTO students (user_id, gpa, year)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            Long userId;

            try (PreparedStatement userStmt = conn.prepareStatement(userSql)) {
                userStmt.setString(1, student.getEmail());
                userStmt.setString(2, student.getFullName());
                userStmt.setString(3, student.getPasswordHash());

                ResultSet rs = userStmt.executeQuery();
                if (!rs.next()) {
                    conn.rollback();
                    return false;
                }
                userId = rs.getLong("id");
            }

            try (PreparedStatement studentStmt = conn.prepareStatement(studentSql)) {
                studentStmt.setLong(1, userId);
                studentStmt.setDouble(2, student.getGpa());
                studentStmt.setInt(3, student.getYear());
                studentStmt.executeUpdate();
            }

            conn.commit();
            student.setId(userId);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Student> findById(Long id) {
        String sql = """
            SELECT u.id, u.email, u.full_name, u.password_hash,
                   s.gpa, s.year
            FROM users u
            JOIN students s ON u.id = s.user_id
            WHERE u.id = ?
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            Student student = new Student(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("full_name"),
                    rs.getString("password_hash"),
                    rs.getDouble("gpa"),
                    rs.getInt("year")
            );

            return Optional.of(student);

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
