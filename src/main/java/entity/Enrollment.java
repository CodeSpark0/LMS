package entity;

import java.time.LocalDateTime;

public class Enrollment {
    private Long id;
    private Long studentId;
    private Long courseId;
    private LocalDateTime enrolledAt;

    public Enrollment() {
    }

    public Enrollment(Long studentId, Long courseId, LocalDateTime enrolledAt) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrolledAt = enrolledAt;
    }

    public Enrollment(Long id, Long studentId, Long courseId, LocalDateTime enrolledAt) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrolledAt = enrolledAt;
    }

    public Long getId() { return id; }
    public Long getStudentId() { return studentId; }
    public Long getCourseId() { return courseId; }
    public LocalDateTime getEnrolledAt() { return enrolledAt; }

    public void setId(Long id) { this.id = id; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public void setEnrolledAt(LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; }
}
