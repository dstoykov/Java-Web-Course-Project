package dst.courseproject.util;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public final class Users {
    public static boolean hasRole(String role, Collection<? extends GrantedAuthority> roles) {
        for (GrantedAuthority aRole : roles) {
            if (aRole.getAuthority().equals(role)) {
                return true;
            }
        }
        return false;
    }
}
