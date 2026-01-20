package entity;

public class Course {
    private Long id;
    private String title;
    private int capacity;
    private String description;

    public Course(String title,int capacity, String description) {
        this.title = title;
        setCapacity(capacity);

        this.description = description;}

    public Course(Long id,String title,int capacity,String description) {
        this.id = id;
        this.title = title;
        setCapacity(capacity);
        this.description = description;
    }
    public Long getId() {return id;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public int getCapacity() {return capacity;}

    public void setId(Long id) {this.id = id;}
    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {this.description = description;}
    public void setCapacity(int capacity) {
        if (capacity < 0) {throw new IllegalArgumentException("capacity must be >= 0");}
        this.capacity = capacity;
    }
}



