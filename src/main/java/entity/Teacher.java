package entity;

public class Teacher extends User {
    private String department;
    private String academicTitle;

    public Teacher() {
        super();
    }

    public Teacher(Long id, String email, String fullName, String passwordHash,
                   String department, String academicTitle) {
        super(id, email, fullName, passwordHash);
        this.department = department;
        this.academicTitle = academicTitle;
    }

    public String getDepartment() { return department; }
    public String getAcademicTitle() { return academicTitle; }

    public void setDepartment(String department) { this.department = department; }
    public void setAcademicTitle(String academicTitle) { this.academicTitle = academicTitle; }
}
