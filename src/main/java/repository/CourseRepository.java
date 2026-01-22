package repository;

import entity.Course;
import util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CourseRepository {

    public Optional<Course> findById(Long id) {
        String sql = "SELECT id, title, capacity, description FROM courses WHERE id = ?";

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Course course = new Course(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getInt("capacity"),
                            rs.getString("description")
                    );
                    return Optional.of(course);
                }
                return Optional.empty();
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to find course by id: " + id, e);
        }
    }

    public void addCourse(Course course) {
        String sql = "INSERT INTO courses (title, capacity, description) VALUES (?, ?, ?)";

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, course.getTitle());
            stmt.setInt(2, course.getCapacity());
            stmt.setString(3, course.getDescription());

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to add course", e);
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
            throw new RuntimeException("Failed to delete course with id: " + id, e);
        }
    }

    public void updateCourse(Course course) {
        String sql = """
            UPDATE courses
            SET title = ?, capacity = ?, description = ?
            WHERE id = ?
            """;

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, course.getTitle());
            stmt.setInt(2, course.getCapacity());
            stmt.setString(3, course.getDescription());
            stmt.setLong(4, course.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to update course with id: " + course.getId(), e);
        }
    }
}
