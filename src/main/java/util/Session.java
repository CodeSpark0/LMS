package util;

import entity.Role;


public class Session {
    private static Long userId;
    private static Role role;

    public static void login(Long id, Role r) {
        userId = id;
        role = r;
    }

    public static Long getUserId() { return userId; }
    public static Role getRole() { return role; }

    public static void logout() {
        userId = null;
        role = null;
    }
}
