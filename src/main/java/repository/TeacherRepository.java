package repository;

import entity.Teacher;
import util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TeacherRepository {

    public boolean save(Teacher teacher) {
        String userSql = """
            INSERT INTO users (email, full_name, password_hash)
            VALUES (?, ?, ?)
            RETURNING id
        """;

        String teacherSql = """
            INSERT INTO teachers (user_id, department, academic_title)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            Long userId;

            try (PreparedStatement userStmt = conn.prepareStatement(userSql)) {
                userStmt.setString(1, teacher.getEmail());
                userStmt.setString(2, teacher.getFullName());
                userStmt.setString(3, teacher.getPasswordHash());

                ResultSet rs = userStmt.executeQuery();
                if (!rs.next()) {
                    conn.rollback();
                    return false;
                }
                userId = rs.getLong("id");
            }

            try (PreparedStatement teacherStmt = conn.prepareStatement(teacherSql)) {
                teacherStmt.setLong(1, userId);
                teacherStmt.setString(2, teacher.getDepartment());
                teacherStmt.setString(3, teacher.getAcademicTitle());
                teacherStmt.executeUpdate();
            }

            conn.commit();
            teacher.setId(userId);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Teacher> findById(Long id) {
        String sql = """
            SELECT u.id, u.email, u.full_name, u.password_hash,
                   t.department, t.academic_title
            FROM users u
            JOIN teachers t ON u.id = t.user_id
            WHERE u.id = ?
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            Teacher teacher = new Teacher(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("full_name"),
                    rs.getString("password_hash"),
                    rs.getString("department"),
                    rs.getString("academic_title")
            );

            return Optional.of(teacher);

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
