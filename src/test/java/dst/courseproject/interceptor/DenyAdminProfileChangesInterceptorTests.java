package dst.courseproject.interceptor;

import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DenyAdminProfileChangesInterceptorTests {
    private DenyAdminProfileChangesInterceptor denyAdminProfileChangesInterceptor;
    private UserService userService;

    @Before
    public void init() {
        this.userService = mock(UserService.class);
        this.denyAdminProfileChangesInterceptor = new DenyAdminProfileChangesInterceptor(this.userService);
    }

    @Test
    public void denyAdminProfileChangesInterceptor_preHandleWithAdminUser_shouldSendRedirect() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setRequestURI("/users/edit/123");
        when(this.userService.getUserServiceModelById("123")).thenReturn(new UserServiceModel(){{
            setId("123");
            setEmail("admin@admin.bg");
        }});

        this.denyAdminProfileChangesInterceptor.preHandle(request, response, new Object());

        String expected = "/";
        String actual = response.getHeader("Location");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void denyAdminProfileChangesInterceptor_preHandleWithNoAdminUser_shouldNotSendRedirect() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setRequestURI("/users/edit/321");
        when(this.userService.getUserServiceModelById("321")).thenReturn(new UserServiceModel(){{
            setId("321");
            setEmail("stamat@goshomail.com");
        }});

        this.denyAdminProfileChangesInterceptor.preHandle(request, response, new Object());

        String actual = response.getHeader("Location");

        Assert.assertNull(actual);
    }
}