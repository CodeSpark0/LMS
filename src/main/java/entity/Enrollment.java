package entity;

import java.time.LocalDateTime;

public class Enrollment {
    private Long id;
    private User user;
    private Course course;
    private LocalDateTime enrolledAt;

    public Enrollment(){}

    public Enrollment(User user, Course course, LocalDateTime enrolledAt) {
        this.user = user;
        this.course = course;
        this.enrolledAt = enrolledAt;
    }

    public Enrollment(Long id, User user, Course course, LocalDateTime enrolledAt) {
        this.id = id;
        this.user = user;
        this.course = course;
        this.enrolledAt = enrolledAt;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public Course getCourse() { return course; }
    public LocalDateTime getEnrolledAt() { return enrolledAt; }

    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setCourse(Course course) { this.course = course; }
    public void setEnrolledAt(LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; }

    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                ", user=" + user +
                ", course=" + course +
                ", enrolledAt=" + enrolledAt +
                '}';
    }
}
