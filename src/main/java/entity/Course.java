package entity;

public class Course {
    private Long id;
    private Long teacherId;
    private String title;
    private int capacity;
    private String description;
    private String category;

    public Course() {
    }
    public Course(Long teacherId, String title, int capacity, String description ,String category) {
        this.teacherId = teacherId;
        this.title = title;
        setCapacity(capacity);
        this.description = description;
        setCategory(category);
    }
    public Course(Long id, Long teacherId, String title, int capacity, String description,String category) {
        this.id = id;
        this.teacherId = teacherId;
        this.title = title;
        setCapacity(capacity);
        this.description = description;
        setCategory(category);
    }
    public Long getId() { return id; }
    public Long getTeacherId() { return teacherId; }
    public String getTitle() { return title; }
    public int getCapacity() { return capacity; }
    public String getDescription() { return description; }
    public String getCategory(){ return category; }

    public void setId(Long id) { this.id = id; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }

    public void setCategory (String category) {
        if(category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category is empty");
        }
        this.category = category;

    }

    public void setCapacity(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("must be>= 0");
        this.capacity = capacity;
    }
}