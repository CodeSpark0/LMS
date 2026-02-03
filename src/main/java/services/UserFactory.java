package services;

import entity.Role;
import entity.Student;
import entity.Teacher;
import entity.User;

public class UserFactory {

    // Pattern (2)
    public static User createUser(Role role, String fullName, String email, String password) {
        switch (role) {
            case STUDENT:
                return new Student(null, email, fullName, password, 0.0, 1);
            case TEACHER:
                return new Teacher(null, email, fullName, password, "General", "Instructor");
            default:
                throw new IllegalArgumentException("Unknown role type: " + role);
        }
    }
}