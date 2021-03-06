package dst.courseproject.interceptor;

import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DenyAdminProfileChangesInterceptor extends HandlerInterceptorAdapter {
    private static final String ADMIN_EMAIL = "admin@admin.bg";
    private static final String INDEX = "/";
    private static final String SLASH = "/";

    private final UserService userService;

    @Autowired
    public DenyAdminProfileChangesInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String[] uriSplit = request.getRequestURI().split(SLASH);
        String userId = uriSplit[uriSplit.length - 1];
        UserServiceModel userServiceModel = this.userService.getUserServiceModelById(userId);
        if (userServiceModel.getEmail().equals(ADMIN_EMAIL)) {
            response.sendRedirect(INDEX);
        }

        return super.preHandle(request, response, handler);
    }
}
