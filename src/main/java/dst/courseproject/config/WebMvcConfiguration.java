package dst.courseproject.config;

import dst.courseproject.interceptor.DenyAdminProfileChangesInterceptor;
import dst.courseproject.interceptor.DenyLoggedInUserPages;
import dst.courseproject.interceptor.TitlePrependInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final TitlePrependInterceptor titlePrependInterceptor;
    private final DenyAdminProfileChangesInterceptor denyAdminProfileChangesInterceptor;
    private final DenyLoggedInUserPages denyLoggedInUserPages;

    @Autowired
    public WebMvcConfiguration(TitlePrependInterceptor titlePrependInterceptor, DenyAdminProfileChangesInterceptor denyAdminProfileChangesInterceptor, DenyLoggedInUserPages denyLoggedInUserPages) {
        this.titlePrependInterceptor = titlePrependInterceptor;
        this.denyAdminProfileChangesInterceptor = denyAdminProfileChangesInterceptor;
        this.denyLoggedInUserPages = denyLoggedInUserPages;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.titlePrependInterceptor).excludePathPatterns("/");
        registry.addInterceptor(this.denyAdminProfileChangesInterceptor).addPathPatterns("/admin/users/edit/**", "/admin/users/delete/**", "/admin/users/moderator/**", "/admin/users/moderator-revoke/**");
        registry.addInterceptor(this.denyLoggedInUserPages).addPathPatterns("/videos/add", "/videos/edit/**", "videos/delete/**");
    }
}
