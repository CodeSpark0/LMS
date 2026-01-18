package entity;

import java.time.LocalDateTime;

public class Enrollment {
    private Long id;
    private User user;
    private Course course;
    private LocalDateTime enrolledAt;

    public Long getId(){return id;}
    public User getUser(){return user;}
    public Course getCourse(){return course;}
    public LocalDateTime getEnrolledAt(){return enrolledAt;}

    public void setId(Long id){this.id = id;}
    public void setUser(User user){this.user= user;}
    public void setCourse(Course course){this.course = course;}
    public void setEnrolledAt(LocalDateTime enrolledAt){this.enrolledAt = enrolledAt;}

}

