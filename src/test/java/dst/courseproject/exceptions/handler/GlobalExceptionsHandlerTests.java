package dst.courseproject.exceptions.handler;

import dst.courseproject.services.LogService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;

import static org.mockito.Mockito.mock;

@SpringBootTest
public class GlobalExceptionsHandlerTests {
    private GlobalExceptionsHandler handler;

    @Before
    public void init() {
        LogService logService = mock(LogService.class);
        this.handler = new GlobalExceptionsHandler(logService);
    }

    @Test
    public void globalExceptionHandler_handleUploadingVideoExceptions_shouldReturnModelAndViewWithViewName() {
        String expected = "redirect:../errors/error";
        String actual = this.handler.handleUploadingVideoExceptions(new Exception()).getViewName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void globalExceptionHandler_handleIllegalArgumentException_shouldReturnModelAndViewWithViewName() {
        String expected = "redirect:../";
        String actual = this.handler.handleIllegalArgumentException().getViewName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void globalExceptionHandler_handleAccessDenied_shouldReturnModelAndViewWithViewName() {
        String expected = "redirect:../users/login";
        String actual = this.handler.handleAccessDenied(new AccessDeniedException("")).getViewName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void globalExceptionHandler_handleAnyException_shouldReturnModelAndViewWithViewName() {
        String expected = "redirect:../errors/error";
        String actual = this.handler.handleAnyException(new Exception()).getViewName();

        Assert.assertEquals(expected, actual);
    }
}
