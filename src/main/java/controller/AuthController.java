package controller;

import entity.Course;
import repository.CourseRepository;
import repository.EnrollmentRepository;
import repository.GradeRepository;
import services.*;


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
        CourseRepository courseRepo = new CourseRepository();
        EnrollmentRepository enrollmentRepo = new EnrollmentRepository();
        GradeRepository gradeRepo = new GradeRepository();

        CourseService courseService = new CourseService(courseRepo);

        EnrollmentService enrollmentService = new EnrollmentService(enrollmentRepo, courseRepo);
        StudentService studentService = new StudentService(enrollmentService, gradeRepo);

        StudentController studentController = new StudentController(studentService, courseService);

        studentController.start(studentId);
    }

    private void openTeacherMenu(Long teacherId) {
        CourseRepository courseRepo = new CourseRepository();
        EnrollmentRepository enrollmentRepo = new EnrollmentRepository();
        GradeRepository gradeRepo = new GradeRepository();

        CourseService courseService = new CourseService(courseRepo);
        TeacherService teacherService =
                new TeacherService(courseRepo, enrollmentRepo, gradeRepo);

        TeacherController teacherController =
                new TeacherController(teacherService, courseService);

        teacherController.start(teacherId);
    }

}
