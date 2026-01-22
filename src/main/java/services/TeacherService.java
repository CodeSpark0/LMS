package services;

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

    public void assignGrade(Long teacherId, Long studentId, Long courseId, int percentage) {
        try {
            if (!enrollmentRepo.isEnrolled(studentId, courseId)) {
                throw new RuntimeException("Student is not enrolled in this course");
            }

            List<Grade> grades = gradeRepo.findByStudentId(studentId);
            boolean updated = false;
            for (Grade g : grades) {
                if (g.getCourseId().equals(courseId)) {
                    g.setPercentage(percentage);
                    gradeRepo.updateGrade(g);
                    updated = true;
                    break;
                }
            }

            if (!updated) {
                Grade grade = new Grade(studentId, courseId, percentage);
                gradeRepo.addGrade(grade);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to assign grade for student " + studentId + " in course " + courseId, e);
        }
    }

    public List<Enrollment> getStudentsInCourse(Long courseId) {
        try {
            return enrollmentRepo.findByCourseId(courseId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get students for course " + courseId, e);
        }
    }
}
