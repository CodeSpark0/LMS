package entity;

public class User {
    private Long id;
    private String name;
    private String email;
    private double gpa;
    private int age;
    private boolean gender;

    public User() {}

    public User(String name,String email,boolean gender,int age,double gpa) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.gpa = gpa;
    }

    public User(Long id,String name,String email,boolean gender,int age,double gpa) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.gpa = gpa;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public double getGpa() { return gpa; }
    public int getAge() { return age; }
    public boolean isGender() { return gender; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    public void setAge(int age) { this.age = age; }
    public void setGender(boolean gender) { this.gender = gender; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", gpa=" + gpa +
                '}';
    }
}
