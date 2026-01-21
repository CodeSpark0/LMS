package services;
import entity.Role;
public class AuthResult {
    private final Long userId;
    private final Role role;

    public AuthResult(Long userId, Role role) {
        this.userId = userId;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public Role getRole() {
        return role;
    }
}
