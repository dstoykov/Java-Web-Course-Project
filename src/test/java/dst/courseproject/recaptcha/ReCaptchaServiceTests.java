package dst.courseproject.recaptcha;

import dst.courseproject.exceptions.InvalidReCaptchaException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class ReCaptchaServiceTests {
    private ReCaptchaService reCaptchaService;

    @Before
    public void init() {
        this.reCaptchaService = new ReCaptchaServiceImpl(new RestTemplate());
    }

    @Test(expected = InvalidReCaptchaException.class)
    public void reCatptchaService_captchaMethod_ShouldThrowException() throws InvalidReCaptchaException {
        this.reCaptchaService.captcha("");
    }
}
