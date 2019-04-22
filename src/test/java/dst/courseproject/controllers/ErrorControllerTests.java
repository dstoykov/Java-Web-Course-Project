package dst.courseproject.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

@SpringBootTest
public class ErrorControllerTests {
    private ErrorController controller = new ErrorController();

    @Test
    public void errorController_errorGetRequest_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        this.controller.error(modelAndView);

        Assert.assertEquals("error/default-error-page", modelAndView.getViewName());
        Assert.assertEquals("Oops...", modelAndView.getModelMap().get("title"));
    }
}
