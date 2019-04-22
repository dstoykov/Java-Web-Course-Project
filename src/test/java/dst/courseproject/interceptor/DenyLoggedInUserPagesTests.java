package dst.courseproject.interceptor;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.management.remote.JMXPrincipal;

@SpringBootTest
public class DenyLoggedInUserPagesTests {
    private DenyLoggedInUserPagesInterceptor denyLoggedInUserPages = new DenyLoggedInUserPagesInterceptor();

    @Test
    public void denyLoggedInUserPages_preHandleWithNullPrincipal_sendActualLocation() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        this.denyLoggedInUserPages.preHandle(request, response, new Object());

        String expected = "/users/login";
        String actual = response.getHeader("Location");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void denyLoggedInUserPages_preHandleWithActualPrincipal_sendNoLocation() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setUserPrincipal(new JMXPrincipal("mail@mail.com"));
        this.denyLoggedInUserPages.preHandle(request, response, new Object());

        String actual = response.getHeader("Location");

        Assert.assertNull(actual);
    }
}