package dst.courseproject.util;

import dst.courseproject.models.service.UserServiceModel;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public final class UserUtils {
    public static boolean hasRole(String role, Collection<? extends GrantedAuthority> roles) {
        for (GrantedAuthority aRole : roles) {
            if (aRole.getAuthority().equals(role)) {
                return true;
            }
        }
        return false;
    }

    public static String getUserFullName(UserServiceModel userServiceModel) {
        return userServiceModel.getFirstName() + " " + userServiceModel.getLastName();
    }
}
