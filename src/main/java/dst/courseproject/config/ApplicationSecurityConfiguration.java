package dst.courseproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String STYLES = "/styles/**";
    private static final String SCRIPTS = "/scripts/**";
    private static final String IMAGES = "/images/**";
    private static final String FAVICON = "/favicon.ico";

    private static final String ROOT = "/";
    private static final String ERROR = "/errors/**";
    private static final String LOGIN = "/users/login";
    private static final String REGISTER = "/users/register";
    private static final String LOGOUT = "/users/logout";
    private static final String ADMIN_ALL_USERS = "/admin/users/all";
    private static final String ADMIN_USER_PROFILE = "/admin/users/{id}";
    private static final String ADMIN_EDIT_USER = "/admin/users/edit/**";
    private static final String ADMIN_ALL_CATEGORIES = "/admin/categories/all";
    private static final String ADMIN_ADD_CATEGORY = "/admin/categories/add/**";
    private static final String ADMIN_EDIT_CATEGORY = "/admin/categories/edit/**";
    private static final String ADMIN_DELETE_USER = "/admin/users/delete/**";
    private static final String ADMIN_RESTORE_USER = "/admin/users/restore/**";
    private static final String ADMIN_CREATE_MODERATOR = "/admin/users/moderator/**";
    private static final String ADMIN_REVOKE_AUTHORITY = "/admin/users/moderator-revoke/**";
    private static final String ADMIN_DELETE_CATEGORY = "/admin/categories/delete/**";
    private static final String ADMIN_AUTHORITY = "hasAuthority('ADMIN')";
    private static final String MODERATOR_AUTHORITY = "hasAuthority('MODERATOR')";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String REMEMBER_PARAMETER = "remember";
    private static final String REMEMBER_COOKIE_NAME = "rememberMeCookie";
    private static final String REMEMBER_KEY = "48433e39-e610-4a2c-926c-f86d46f5360a";
    private static final Integer TOKEN_VALIDITY_SECONDS = 100;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(STYLES, SCRIPTS, IMAGES, FAVICON);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(ROOT, LOGIN, REGISTER, ERROR).permitAll()
                .antMatchers(ADMIN_ALL_USERS, ADMIN_USER_PROFILE, ADMIN_EDIT_USER, ADMIN_ALL_CATEGORIES, ADMIN_ADD_CATEGORY, ADMIN_EDIT_CATEGORY)
                            .access(MODERATOR_AUTHORITY)
                .antMatchers(ADMIN_DELETE_USER, ADMIN_RESTORE_USER, ADMIN_CREATE_MODERATOR, ADMIN_REVOKE_AUTHORITY, ADMIN_DELETE_CATEGORY)
                            .access(ADMIN_AUTHORITY)
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin().loginPage(LOGIN).permitAll()
                .loginProcessingUrl(LOGIN)
                .usernameParameter(EMAIL)
                .passwordParameter(PASSWORD)
                .defaultSuccessUrl(ROOT)
                .and()
                .rememberMe()
                .rememberMeParameter(REMEMBER_PARAMETER)
                .rememberMeCookieName(REMEMBER_COOKIE_NAME)
                .key(REMEMBER_KEY)
                .tokenValiditySeconds(TOKEN_VALIDITY_SECONDS)
                .and()
                .logout().logoutUrl(LOGOUT)
                .logoutSuccessUrl(LOGIN)
                .permitAll();
    }
}