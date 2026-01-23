package repository;

import entity.Enrollment;
import entity.Course;
import util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnrollmentRepository {

    public void enroll(Long studentId, Long courseId) {
        String sql = """
            INSERT INTO enrollments (student_id, course_id, enrolled_at)
            VALUES (?, ?, ?)
            """;

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, studentId);
            stmt.setLong(2, courseId);
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to enroll student " + studentId + " to course " + courseId, e
            );
        }
    }

    public boolean isEnrolled(Long studentId, Long courseId) {
        String sql = """
            SELECT 1 FROM enrollments
            WHERE student_id = ? AND course_id = ?
            """;

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, studentId);
            stmt.setLong(2, courseId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to check enrollment", e);
        }
    }

    public Optional<Enrollment> findById(Long id) {
        String sql = """
            SELECT id, student_id, course_id, enrolled_at
            FROM enrollments
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
            throw new RuntimeException("Failed to find enrollment by id " + id, e);
        }
    }

    public List<Enrollment> findByStudentId(Long studentId) {
        String sql = """
            SELECT id, student_id, course_id, enrolled_at
            FROM enrollments
            WHERE student_id = ?
            """;

        List<Enrollment> result = new ArrayList<>();

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, studentId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Failed to find enrollments for student " + studentId, e);
        }
    }

    public List<Enrollment> findByCourseId(Long courseId) {
        String sql = """
            SELECT id, student_id, course_id, enrolled_at
            FROM enrollments
            WHERE course_id = ?
            """;

        List<Enrollment> result = new ArrayList<>();

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, courseId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Failed to find enrollments for course " + courseId, e);
        }
    }

    public void removeEnrollment(Long studentId, Long courseId) {
        String sql = """
            DELETE FROM enrollments
            WHERE student_id = ? AND course_id = ?
            """;

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, studentId);
            stmt.setLong(2, courseId);

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to remove enrollment", e);
        }
    }

    private Enrollment mapRow(ResultSet rs) throws Exception {
        return new Enrollment(
                rs.getLong("id"),
                rs.getLong("student_id"),
                rs.getLong("course_id"),
                rs.getTimestamp("enrolled_at").toLocalDateTime()
        );
    }

    public List<Course> findCoursesByStudentId(Long studentId) {
        String sql = """
            SELECT c.id, c.teacher_id, c.capacity, c.title, c.description,
            FROM enrollments e
            JOIN courses c ON c.id = e.course_id
            WHERE e.student_id = ?
            ORDER BY e.enrolled_at DESC
            """;

        List<Course> result = new ArrayList<>();

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, studentId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(mapCourse(rs));
                }
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Failed to find courses for student " + studentId, e);
        }
    }

    private Course mapCourse(ResultSet rs) throws Exception {
        return new Course(
                rs.getLong("id"),
                rs.getLong("teacher_id"),
                rs.getString("title"),
                rs.getInt("capacity"),
                rs.getString("description")
        );
    }
}
