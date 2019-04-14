package dst.courseproject.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DenyLoggedInUserPages extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getUserPrincipal() == null) {
            response.sendRedirect("/users/login");
        }

        return super.preHandle(request, response, handler);
    }
}
