package controller;

import entity.Course;
import entity.Enrollment;
import entity.Grade;
import services.CourseService;
import services.StudentService;

import java.util.List;
import java.util.Scanner;

public class StudentController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final Scanner scanner;

    public StudentController(
            StudentService studentService,
            CourseService courseService
    ) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.scanner = new Scanner(System.in);
    }

    public void start(Long studentId) {
        while (true) {
            System.out.println("\n=== STUDENT MENU ===");
            System.out.println("1. View all courses");
            System.out.println("2. Enroll in course");
            System.out.println("3. View my enrollments");
            System.out.println("4. View my grades");
            System.out.println("0. Logout");

            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> showAllCourses();
                    case "2" -> enrollInCourse(studentId);
                    case "3" -> showMyEnrollments(studentId);
                    case "4" -> showMyGrades(studentId);
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

    private void showAllCourses() {
        List<Course> courses = courseService.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        System.out.println("\n--- Available Courses ---");
        for (Course c : courses) {
            System.out.printf(
                    "ID: %d | Title: %s | Capacity: %d%n",
                    c.getId(), c.getTitle(), c.getCapacity()
            );
        }
    }

    private void enrollInCourse(Long studentId) {
        System.out.print("Enter course ID: ");
        Long courseId = Long.parseLong(scanner.nextLine());

        studentService.enrollInCourse(studentId, courseId);

        System.out.println("Successfully enrolled.");
    }

    private void showMyEnrollments(Long studentId) {
        List<Enrollment> enrollments =
                studentService.getMyEnrollments(studentId);

        if (enrollments.isEmpty()) {
            System.out.println("You are not enrolled in any courses.");
            return;
        }

        System.out.println("\n--- My Enrollments ---");
        for (Enrollment e : enrollments) {
            System.out.println("Course ID: " + e.getCourseId());
        }
    }

    private void showMyGrades(Long studentId) {
        List<Grade> grades = studentService.getMyGrades(studentId);

        if (grades.isEmpty()) {
            System.out.println("No grades available.");
            return;
        }

        System.out.println("\n--- My Grades ---");
        for (Grade g : grades) {
            System.out.printf(
                    "Course ID: %d | Grade: %d%%%n",
                    g.getCourseId(), g.getPercentage()
            );
        }
    }
}
