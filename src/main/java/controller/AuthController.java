package controller;

import services.AuthService;
import services.AuthResult;

import java.util.Optional;
import java.util.Scanner;
import entity.Role;
import util.Session;

public class AuthController {

    private final AuthService authService;
    private final Scanner scanner;

    public AuthController() {
        this.authService = new AuthService();
        this.scanner = new Scanner(System.in);
    }

    public void login() {
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        Optional<AuthResult> result = authService.login(email, password);

        if (result.isEmpty()) {
            System.out.println("Invalid email or password.");
            return;
        }

        AuthResult auth = result.get();

        System.out.println("Login successful!");
        System.out.println("Role: " + auth.getRole());

        Session.login(auth.getUserId(), auth.getRole());

        switch (auth.getRole()) {
            case Role.STUDENT -> openStudentMenu(auth.getUserId());
            case Role.TEACHER -> openTeacherMenu(auth.getUserId());
            default -> throw new IllegalStateException("Unknown role");
        }
    }

    private void openStudentMenu(Long studentId) {
        System.out.println("Opening STUDENT menu for user id = " + studentId);
        // later: StudentController
    }

    private void openTeacherMenu(Long teacherId) {
        System.out.println("Opening TEACHER menu for user id = " + teacherId);
        // later: TeacherController
    }
}
