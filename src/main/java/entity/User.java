package entity;

public abstract class User {
    protected Long id;
    protected String email;
    protected String fullName;
    protected String passwordHash;

    protected User() {
    }

    protected User(Long id, String email, String fullName, String passwordHash) {
        this.id = id;
        setEmail(email); // Используем сеттер с проверкой
        this.fullName = fullName;
        setPasswordHash(passwordHash); // Используем сеттер с проверкой
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public String getPasswordHash() { return passwordHash; }

    public void setId(Long id) { this.id = id; }

// ВАЛИДАЦИЯ дял емайл и пароля
    public void setEmail(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email format");}
        this.email = email;
    }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public void setPasswordHash(String passwordHash) {
        if (passwordHash == null || passwordHash.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");}
        this.passwordHash = passwordHash;
    }
}