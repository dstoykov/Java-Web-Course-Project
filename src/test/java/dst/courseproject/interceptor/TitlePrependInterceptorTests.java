package dst.courseproject.interceptor;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;

@SpringBootTest
public class TitlePrependInterceptorTests {
    private TitlePrependInterceptor titlePrependInterceptor = new TitlePrependInterceptor();

    @Test
    public void titlePrependInterceptor_postHandleWithModelMapWithTitle_shouldChangeTitle() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().put("title", "Home");

        this.titlePrependInterceptor.postHandle(mock(HttpServletRequest.class), mock(HttpServletResponse.class), new Object(), modelAndView);

        String expected = "Home | In The Box";
        String actual = (String) modelAndView.getModelMap().get("title");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void titlePrependInterceptor_postHandleWithEmptyModelMap_shouldDoNothing() throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        this.titlePrependInterceptor.postHandle(mock(HttpServletRequest.class), mock(HttpServletResponse.class), new Object(), modelAndView);

        String actual = (String) modelAndView.getModelMap().get("title");

        Assert.assertNull(actual);
    }
}
