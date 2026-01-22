package controller;

import entity.Course;
import entity.Enrollment;
import services.CourseService;
import services.TeacherService;

import java.util.List;
import java.util.Scanner;

public class TeacherController {

    private final TeacherService teacherService;
    private final CourseService courseService;
    private final Scanner scanner;

    public TeacherController(
            TeacherService teacherService,
            CourseService courseService
    ) {
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.scanner = new Scanner(System.in);
    }

    public void start(Long teacherId) {
        while (true) {
            System.out.println("\n=== TEACHER MENU ===");
            System.out.println("1. View my courses");
            System.out.println("2. View students in course");
            System.out.println("3. Assign grade");
            System.out.println("0. Logout");

            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> showMyCourses(teacherId);
                    case "2" -> showStudentsInCourse(teacherId);
                    case "3" -> assignGrade(teacherId);
                    case "0" -> {
                        System.out.println("Logging out...");
                        return;
                    }
                    default -> System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void showMyCourses(Long teacherId) {
        List<Course> courses = courseService.getCoursesByTeacher(teacherId);

        if (courses.isEmpty()) {
            System.out.println("You have no courses.");
            return;
        }

        System.out.println("\n--- My Courses ---");
        for (Course c : courses) {
            System.out.printf(
                    "ID: %d | Title: %s | Capacity: %d%n",
                    c.getId(), c.getTitle(), c.getCapacity()
            );
        }
    }

    private void showStudentsInCourse(Long teacherId) {
        System.out.print("Enter course ID: ");
        Long courseId = Long.parseLong(scanner.nextLine());

        List<Enrollment> enrollments =
                teacherService.getStudentsInCourse(teacherId, courseId);

        if (enrollments.isEmpty()) {
            System.out.println("No students enrolled.");
            return;
        }

        System.out.println("\n--- Enrolled Students ---");
        for (Enrollment e : enrollments) {
            System.out.println("Student ID: " + e.getStudentId());
        }
    }

    private void assignGrade(Long teacherId) {
        System.out.print("Course ID: ");
        Long courseId = Long.parseLong(scanner.nextLine());

        System.out.print("Student ID: ");
        Long studentId = Long.parseLong(scanner.nextLine());

        System.out.print("Grade (0–100): ");
        int percentage = Integer.parseInt(scanner.nextLine());

        teacherService.assignGrade(
                teacherId,
                studentId,
                courseId,
                percentage
        );

        System.out.println("Grade assigned successfully.");
    }
}
