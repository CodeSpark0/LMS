package dostup;
import entity.Role;
import java.util.Map;
import java.util.Set;

public class RoleManager {
    private static final Map<Role, Set<String>> rolePermissions = Map.of(
            Role.TEACHER, Set.of("ADD_GRADE", "VIEW_GRADES", "EDIT_GRADES", "DELETE_GRADES"),
            Role.STUDENT, Set.of("VIEW_GRADES")
    );

    public static boolean hasAccess(Role role, String action) {
        return rolePermissions.getOrDefault(role, Set.of()).contains(action);
    }
}
