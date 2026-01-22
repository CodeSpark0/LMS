package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class DatabaseManager {

    private static final String URL = "jdbc:postgresql://localhost:5432/lms";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Mows2323@";

    private DatabaseManager() {}

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean testConnection() {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT 1");
             ResultSet rs = ps.executeQuery()) {

            return rs.next();
        } catch (Exception e) {
            System.out.println("DB ERROR: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        if (testConnection()) {
            System.out.println("DB IS WORK");
        } else {
            System.out.println("DB IS DONT WORK");
        }
    }
}