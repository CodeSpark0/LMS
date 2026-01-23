package services;

import entity.Course;
import entity.Enrollment;
import repository.CourseRepository;
import repository.EnrollmentRepository;

import java.util.List;

public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepo;
    private final CourseRepository courseRepo;

    public EnrollmentService(
            EnrollmentRepository enrollmentRepo,
            CourseRepository courseRepo
    ) {
        this.enrollmentRepo = enrollmentRepo;
        this.courseRepo = courseRepo;
    }

    public void enroll(Long studentId, Long courseId) {
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

    public void unenroll(Long studentId, Long courseId) {
        try {
            if (!enrollmentRepo.isEnrolled(studentId, courseId)) {
                throw new RuntimeException("Student is not enrolled in this course");
            }

            enrollmentRepo.removeEnrollment(studentId, courseId);

            Course course = courseRepo.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            course.setCapacity(course.getCapacity() + 1);
            courseRepo.updateCourse(course);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to unenroll student " + studentId + " from course " + courseId, e
            );
        }
    }

    public List<Course> getByStudent(Long studentId) {
        try {
            return enrollmentRepo.findCoursesByStudentId(studentId);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to get enrollments for student " + studentId, e
            );
        }
    }

    public List<Enrollment> getByCourse(Long courseId) {
        try {
            return enrollmentRepo.findByCourseId(courseId);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to get enrollments for course " + courseId, e
            );
        }
    }
}
