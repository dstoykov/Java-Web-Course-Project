package dst.courseproject.config;

import dst.courseproject.interceptor.DenyAdminProfileChangesInterceptor;
import dst.courseproject.interceptor.DenyLoggedInUserPagesInterceptor;
import dst.courseproject.interceptor.TitlePrependInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private static final String INDEX = "/";
    private static final String ADMIN_EDIT_USER = "/admin/users/edit/**";
    private static final String ADMIN_USER_DELETE = "/admin/users/delete/**";
    private static final String ADMIN_USER_MODERATOR = "/admin/users/moderator/**";
    private static final String ADMIN_USER_REVOKE = "/admin/users/moderator-revoke/**";
    private static final String VIDEOS_ADD = "/videos/add";
    private static final String VIDEOS_EDIT = "/videos/edit/**";
    private static final String VIDEOS_DELETE = "videos/delete/**";

    private final TitlePrependInterceptor titlePrependInterceptor;
    private final DenyAdminProfileChangesInterceptor denyAdminProfileChangesInterceptor;
    private final DenyLoggedInUserPagesInterceptor denyLoggedInUserPages;

    @Autowired
    public WebMvcConfiguration(TitlePrependInterceptor titlePrependInterceptor, DenyAdminProfileChangesInterceptor denyAdminProfileChangesInterceptor, DenyLoggedInUserPagesInterceptor denyLoggedInUserPages) {
        this.titlePrependInterceptor = titlePrependInterceptor;
        this.denyAdminProfileChangesInterceptor = denyAdminProfileChangesInterceptor;
        this.denyLoggedInUserPages = denyLoggedInUserPages;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.titlePrependInterceptor).excludePathPatterns(INDEX);
        registry.addInterceptor(this.denyAdminProfileChangesInterceptor).addPathPatterns(ADMIN_EDIT_USER, ADMIN_USER_DELETE, ADMIN_USER_MODERATOR, ADMIN_USER_REVOKE);
        registry.addInterceptor(this.denyLoggedInUserPages).addPathPatterns(VIDEOS_ADD, VIDEOS_EDIT, VIDEOS_DELETE);
    }
}
