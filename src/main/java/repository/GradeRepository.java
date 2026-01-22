package repository;

import entity.Grade;
import util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GradeRepository {

    public void addGrade(Grade grade) {
        String sql = "INSERT INTO grades (student_id, course_id, percentage) VALUES (?, ?, ?)";

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, grade.getStudentId());
            stmt.setLong(2, grade.getCourseId());
            stmt.setInt(3, grade.getPercentage());

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to add grade for student " + grade.getStudentId() + " in course " + grade.getCourseId(), e
            );
        }
    }

    public void updateGrade(Grade grade) {
        String sql = "UPDATE grades SET percentage = ? WHERE student_id = ? AND course_id = ?";

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, grade.getPercentage());
            stmt.setLong(2, grade.getStudentId());
            stmt.setLong(3, grade.getCourseId());

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to update grade for student " + grade.getStudentId() + " in course " + grade.getCourseId(), e
            );
        }
    }

    public Optional<Grade> findById(Long id) {
        String sql = "SELECT id, student_id, course_id, percentage FROM grades WHERE id = ?";

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
            throw new RuntimeException("Failed to find grade by id " + id, e);
        }
    }

    public List<Grade> findByStudentId(Long studentId) {
        String sql = "SELECT id, student_id, course_id, percentage FROM grades WHERE student_id = ?";
        List<Grade> result = new ArrayList<>();

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
            throw new RuntimeException("Failed to find grades for student " + studentId, e);
        }
    }

    public List<Grade> findByCourseId(Long courseId) {
        String sql = "SELECT id, student_id, course_id, percentage FROM grades WHERE course_id = ?";
        List<Grade> result = new ArrayList<>();

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
            throw new RuntimeException("Failed to find grades for course " + courseId, e);
        }
    }

    public void deleteGrade(Long studentId, Long courseId) {
        String sql = "DELETE FROM grades WHERE student_id = ? AND course_id = ?";

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, studentId);
            stmt.setLong(2, courseId);

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete grade for student " + studentId + " in course " + courseId, e);
        }
    }

    public Optional<Grade> findByStudentAndCourse(Long studentId, Long courseId) {
        String sql = "SELECT id, student_id, course_id, percentage FROM grades WHERE student_id = ? AND course_id = ?";

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, studentId);
            stmt.setLong(2, courseId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to find grade for student " + studentId + " in course " + courseId,
                    e
            );
        }
    }


    private Grade mapRow(ResultSet rs) throws Exception {
        return new Grade(
                rs.getLong("id"),
                rs.getLong("student_id"),
                rs.getLong("course_id"),
                rs.getInt("percentage")
        );
    }
}
