package ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {
        String url = " ";
        String name = " ";
        String password = " ";

        try (Connection conn = DriverManager.getConnection(url, name, password);
             PreparedStatement ps = conn.prepareStatement("SELECT 1");
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                System.out.println("DB IS WORK");
            }
        } catch (Exception e) {
            System.out.println("DM  IS DONT WORK ERROR: " + e.getMessage());
        }
    }
}
