package entity;

public class Student extends User {
    private double gpa;
    private int year;

    public Student() {
        super();
    }

    public Student(Long id, String email, String fullName, String passwordHash, double gpa, int year) {
        super(id, email, fullName, passwordHash);
        this.gpa = gpa;
        this.year = year;
    }

    public double getGpa() { return gpa; }
    public int getYear() { return year; }

    public void setGpa(double gpa) { this.gpa = gpa; }
    public void setYear(int year) { this.year = year; }
}
