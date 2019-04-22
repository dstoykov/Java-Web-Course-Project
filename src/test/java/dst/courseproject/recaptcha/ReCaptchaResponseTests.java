package dst.courseproject.recaptcha;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReCaptchaResponseTests {
    private ReCaptchaResponse reCaptchaResponse;

    @Before
    public void init() {
        this.reCaptchaResponse = new ReCaptchaResponse();
    }

    @Test
    public void recaptchaService_successFieldGetterAndSetter_workCorrect() {
        //Act
        this.reCaptchaResponse.setSuccess(true);
        boolean actual = this.reCaptchaResponse.isSuccess();

        //Assert
        Assert.assertTrue(actual);
    }

    @Test
    public void recaptchaService_challengeTsFieldGetterAndSetter_workCorrect() {
        //Act
        this.reCaptchaResponse.setChallenge_ts("Challenge_ts");
        String expected = "Challenge_ts";
        String actual = this.reCaptchaResponse.getChallenge_ts();

        //Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void recaptchaService_hostnameFieldGetterAndSetter_workCorrect() {
        //Act
        this.reCaptchaResponse.setHostname("Host");
        String expected = "Host";
        String actual = this.reCaptchaResponse.getHostname();

        //Assert
        Assert.assertEquals(expected, actual);
    }
}
