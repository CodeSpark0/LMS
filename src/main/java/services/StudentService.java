package services;

import entity.Course;
import entity.Enrollment;
import entity.Grade;
import repository.CourseRepository;
import repository.EnrollmentRepository;
import repository.GradeRepository;

import java.util.List;

public class StudentService {

    private final EnrollmentRepository enrollmentRepo;
    private final CourseRepository courseRepo;
    private final GradeRepository gradeRepo;

    public StudentService(
            EnrollmentRepository enrollmentRepo,
            CourseRepository courseRepo,
            GradeRepository gradeRepo
    ) {
        this.enrollmentRepo = enrollmentRepo;
        this.courseRepo = courseRepo;
        this.gradeRepo = gradeRepo;
    }

    public void enrollInCourse(Long studentId, Long courseId) {
        try {
            if (enrollmentRepo.isEnrolled(studentId, courseId)) {
                throw new RuntimeException("Student already enrolled in this course");
            }

            Course course = courseRepo.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            if (course.getCapacity() <= 0) {
                throw new RuntimeException("Course is full");
            }

            enrollmentRepo.enroll(studentId, courseId);

            course.setCapacity(course.getCapacity() - 1);
            courseRepo.updateCourse(course);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to enroll student " + studentId + " in course " + courseId, e
            );
        }
    }


    public List<Enrollment> getMyEnrollments(Long studentId) {
        try {
            return enrollmentRepo.findByStudentId(studentId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get enrollments for student " + studentId, e);
        }
    }

    public List<Grade> getMyGrades(Long studentId) {
        try {
            return gradeRepo.findByStudentId(studentId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get grades for student " + studentId, e);
        }
    }
}
