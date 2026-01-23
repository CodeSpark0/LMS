package services;

import entity.Course;
import entity.Enrollment;
import entity.Grade;
import repository.GradeRepository;

import java.util.List;

public class StudentService {

    private final EnrollmentService enrollmentService;
    private final GradeRepository gradeRepo;

    public StudentService(
            EnrollmentService enrollmentService,
            GradeRepository gradeRepo
    ) {
        this.enrollmentService = enrollmentService;
        this.gradeRepo = gradeRepo;
    }

    public void enrollInCourse(Long studentId, Long courseId) {
        enrollmentService.enroll(studentId, courseId);
    }

    public void unenrollFromCourse(Long studentId, Long courseId) {
        enrollmentService.unenroll(studentId, courseId);
    }

    public List<Course> getMyEnrollments(Long studentId) {
        return enrollmentService.getByStudent(studentId);
    }

    public List<Grade> getMyGrades(Long studentId) {
        try {
            return gradeRepo.findByStudentId(studentId);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to get grades for student " + studentId, e
            );
        }
    }
}
