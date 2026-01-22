package services;

import util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import entity.Role;

public class AuthService {

    public Optional<AuthResult> login(String email, String password) {

        String userSql = """
            SELECT id, password_hash
            FROM users
            WHERE email = ?
        """;

        String studentCheck = "SELECT 1 FROM students WHERE user_id = ?";
        String teacherCheck = "SELECT 1 FROM teachers WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement userStmt = conn.prepareStatement(userSql)) {

            userStmt.setString(1, email);
            ResultSet rs = userStmt.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            Long userId = rs.getLong("id");
            String storedPassword = rs.getString("password_hash");

            if (!storedPassword.equals(password)) {
                return Optional.empty();
            }

            if (exists(conn, studentCheck, userId)) {
                return Optional.of(new AuthResult(userId, Role.STUDENT));
            }

            if (exists(conn, teacherCheck, userId)) {
                return Optional.of(new AuthResult(userId, Role.TEACHER));
            }

            throw new IllegalStateException("User exists without role");

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private boolean exists(Connection conn, String sql, Long userId) throws Exception {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            return stmt.executeQuery().next();
        }
    }
}
