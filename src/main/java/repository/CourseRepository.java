package repository;

import entity.Course;
import entity.CourseDetailsDTO;
import util.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseRepository {
    public List<Course> findAll() {
        String sql = "SELECT id, teacher_id, title, capacity, description, category FROM courses ORDER BY id";
        List<Course> list = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));}
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<Course> findById(Long id) {
        String sql = "SELECT id, teacher_id, title, capacity, description, category FROM courses WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));}
                return Optional.empty();}
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Course> findByTeacherId(Long teacherId) {
        String sql = "SELECT id, teacher_id, title, capacity, description, category FROM courses WHERE teacher_id = ? ORDER BY id";
        List<Course> list = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, teacherId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));}
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void addCourse(Course course) {
        String sql = "INSERT INTO courses (teacher_id, title, capacity, description, category) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, course.getTeacherId());
            stmt.setString(2, course.getTitle());
            stmt.setInt(3, course.getCapacity());
            stmt.setString(4, course.getDescription());
            stmt.setString(5, course.getCategory());
            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void updateCourse(Course course) {
        String sql = "UPDATE courses SET title = ?, capacity = ?, description = ?, category = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course.getTitle());
            stmt.setInt(2, course.getCapacity());
            stmt.setString(3, course.getDescription());
            stmt.setString(4, course.getCategory());
            stmt.setLong(5, course.getId());
            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteById(Long id) {
        String sql = "DELETE FROM courses WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<CourseDetailsDTO> getCourseDetailsWithTeacher(Long courseId) {
        String sql = """
            SELECT c.title, c.description, c.category, u.full_name, u.email
            FROM courses c
            JOIN users u ON c.teacher_id = u.id
            WHERE c.id = ?
            """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    CourseDetailsDTO dto = new CourseDetailsDTO(
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("category"),
                            rs.getString("full_name"),
                            rs.getString("email")
                    );
                    return Optional.of(dto);
                }
                return Optional.empty();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private Course mapRow(ResultSet rs) throws Exception {
        return new Course(
                rs.getLong("id"),
                rs.getLong("teacher_id"),
                rs.getString("title"),
                rs.getInt("capacity"),
                rs.getString("description"),
                rs.getString("category")
        );
    }
}