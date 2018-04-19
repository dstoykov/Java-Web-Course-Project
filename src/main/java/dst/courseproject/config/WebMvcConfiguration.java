package dst.courseproject.config;

import dst.courseproject.interceptor.TitlePrependInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final TitlePrependInterceptor titlePrependInterceptor;

    @Autowired
    public WebMvcConfiguration(TitlePrependInterceptor titlePrependInterceptor) {
        this.titlePrependInterceptor = titlePrependInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.titlePrependInterceptor).excludePathPatterns("/");
    }
}
