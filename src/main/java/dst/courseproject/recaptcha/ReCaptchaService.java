package dst.courseproject.recaptcha;

import dst.courseproject.exceptions.InvalidReCaptchaException;

public interface ReCaptchaService {
    void captcha(String captchaResponse) throws InvalidReCaptchaException;
}
