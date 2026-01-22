package services;

import entity.Course;
import entity.Enrollment;
import entity.Grade;
import repository.CourseRepository;
import repository.EnrollmentRepository;
import repository.GradeRepository;

import java.util.List;

public class TeacherService {

    private final CourseRepository courseRepo;
    private final EnrollmentRepository enrollmentRepo;
    private final GradeRepository gradeRepo;

    public TeacherService(
            CourseRepository courseRepo,
            EnrollmentRepository enrollmentRepo,
            GradeRepository gradeRepo
    ) {
        this.courseRepo = courseRepo;
        this.enrollmentRepo = enrollmentRepo;
        this.gradeRepo = gradeRepo;
    }

    // Assign or update a student's grade
    public void assignGrade(Long teacherId, Long studentId, Long courseId, int percentage) {
        try {
            Course course = courseRepo.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            // Ownership check (CRITICAL)
            if (!course.getTeacherId().equals(teacherId)) {
                throw new RuntimeException("Teacher does not own this course");
            }

            // Enrollment check
            if (!enrollmentRepo.isEnrolled(studentId, courseId)) {
                throw new RuntimeException("Student is not enrolled in this course");
            }

            // Check if grade already exists
            Grade existingGrade = gradeRepo
                    .findByStudentAndCourse(studentId, courseId)
                    .orElse(null);

            if (existingGrade == null) {
                Grade grade = new Grade(studentId, courseId, percentage);
                gradeRepo.addGrade(grade);
            } else {
                existingGrade.setPercentage(percentage);
                gradeRepo.updateGrade(existingGrade);
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to assign grade for student " + studentId + " in course " + courseId,
                    e
            );
        }
    }

    // View students enrolled in teacher's course
    public List<Enrollment> getStudentsInCourse(Long teacherId, Long courseId) {
        try {
            Course course = courseRepo.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            if (!course.getTeacherId().equals(teacherId)) {
                throw new RuntimeException("Teacher does not own this course");
            }

            return enrollmentRepo.findByCourseId(courseId);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to get students for course " + courseId,
                    e
            );
        }
    }
}
