package entity;

public class CourseDetailsDTO {
    private String title;
    private String description;
    private String category;
    private String teacherName;
    private String teacherEmail;

    public CourseDetailsDTO(String title, String description, String category, String teacherName, String teacherEmail) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.teacherName = teacherName;
        this.teacherEmail = teacherEmail;
    }

    @Override
    public String toString() {
        return "Course: " + title + " [" + category + "]\n" +
                "Description: " + description + "\n" +
                "Teacher: " + teacherName + " (" + teacherEmail + ")";
    }
}