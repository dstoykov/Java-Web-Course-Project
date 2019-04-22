package dst.courseproject.models.binding;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserLoginBindingModelTests {
    private UserLoginBindingModel user;

    @Before
    public void init() {
        this.user = new UserLoginBindingModel();
    }

    @Test
    public void userLoginBindingModel_emailFieldGetterAndSetter_returnCorrect() {
        this.user.setEmail("mail@mail.com");
        String expected = "mail@mail.com";
        String actual = this.user.getEmail();
    }

    @Test
    public void userLoginBindingModel_passwordFieldGetterAndSetter_returnCorrect() {
        this.user.setPassword("12345678");
        String expected = "12345678";
        String actual = this.user.getPassword();
    }
}
