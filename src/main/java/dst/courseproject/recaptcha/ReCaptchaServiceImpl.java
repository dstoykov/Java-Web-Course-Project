package dst.courseproject.recaptcha;

import dst.courseproject.exceptions.InvalidReCaptchaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ReCaptchaServiceImpl implements ReCaptchaService {
    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String CAPTCHA_PARAMS = "?secret=%s&response=%s";
    private static final String PRIVATE_KEY = "6LfRCJUUAAAAAEflhbMC3ltHG4gCszOi09XksHST";

    private final RestTemplate restTemplate;

    @Autowired
    public ReCaptchaServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void captcha(String captchaResponse) throws InvalidReCaptchaException {
        String reqParams = String.format(CAPTCHA_PARAMS, PRIVATE_KEY, captchaResponse);
        ReCaptchaResponse reCaptchaResponse = this.restTemplate
                .exchange(CAPTCHA_URL + reqParams,
                        HttpMethod.POST,
                        null,
                        ReCaptchaResponse.class)
                .getBody();

        if (!reCaptchaResponse.isSuccess()) {
            throw new InvalidReCaptchaException("Invalid ReCaptcha!");
        }
    }
}
