package entity;

public class Grade {
    private Long id;
    private Long studentId;
    private Long courseId;
    private int percentage;

    public Grade(Long studentId, Long courseId, int percentage) {
        this.studentId = studentId;
        this.courseId = courseId;
        setPercentage(percentage);
    }

    public Grade(Long id, Long studentId, Long courseId, int percentage) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        setPercentage(percentage);
    }

    public Long getId() { return id; }
    public Long getStudentId() { return studentId; }
    public Long getCourseId() { return courseId; }
    public int getPercentage() { return percentage; }

    public void setId(Long id) { this.id = id; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public void setPercentage(int percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("0 and 100");
        }
        this.percentage = percentage;
    }
}