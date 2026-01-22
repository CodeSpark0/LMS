package repository;

import entity.Course;
import util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseRepository {

    public Optional<Course> findById(Long id) {
        String sql = """
            SELECT id, title, capacity, description, teacher_id
            FROM courses
            WHERE id = ?
            """;

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to find course by id: " + id, e);
        }
    }

    public List<Course> findByTeacherId(Long teacherId) {
        String sql = """
            SELECT id, title, capacity, description, teacher_id
            FROM courses
            WHERE teacher_id = ?
            """;

        List<Course> courses = new ArrayList<>();

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, teacherId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    courses.add(mapRow(rs));
                }
            }

            return courses;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to find courses for teacher id: " + teacherId, e
            );
        }
    }

    public void addCourse(Course course) {
        String sql = """
            INSERT INTO courses (title, capacity, description, teacher_id)
            VALUES (?, ?, ?, ?)
            """;

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, course.getTitle());
            stmt.setInt(2, course.getCapacity());
            stmt.setString(3, course.getDescription());
            stmt.setLong(4, course.getTeacherId());

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to add course", e);
        }
    }

    public void updateCourse(Course course) {
        String sql = """
            UPDATE courses
            SET title = ?, capacity = ?, description = ?, teacher_id = ?
            WHERE id = ?
            """;

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, course.getTitle());
            stmt.setInt(2, course.getCapacity());
            stmt.setString(3, course.getDescription());
            stmt.setLong(4, course.getTeacherId());
            stmt.setLong(5, course.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to update course with id: " + course.getId(), e
            );
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM courses WHERE id = ?";

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to delete course with id: " + id, e
            );
        }
    }

    // ───────────────────────────────
    // Mapper (single source of truth)
    // ───────────────────────────────
    private Course mapRow(ResultSet rs) throws Exception {
        return new Course(
                rs.getLong("id"),
                rs.getLong("teacher_id"),
                rs.getString("title"),
                rs.getInt("capacity"),
                rs.getString("description")
        );
    }
}
