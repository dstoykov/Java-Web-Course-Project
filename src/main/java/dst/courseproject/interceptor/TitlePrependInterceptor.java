package dst.courseproject.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TitlePrependInterceptor extends HandlerInterceptorAdapter {
    private static final String TITLE = "title";
    private static final String IN_THE_BOX = " | In The Box";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        if (modelAndView == null || modelAndView.getModelMap() == null) {
            return;
        }
        ModelMap map = modelAndView.getModelMap();
        if (map.containsAttribute(TITLE)) {
            String title = (String) map.get(TITLE);
            map.put(TITLE, title + IN_THE_BOX);
        }
    }
}
