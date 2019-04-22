package dst.courseproject.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DenyLoggedInUserPagesInterceptor extends HandlerInterceptorAdapter {
    private static final String LOGIN = "/users/login";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getUserPrincipal() == null) {
            response.sendRedirect(LOGIN);
        }

        return super.preHandle(request, response, handler);
    }
}
