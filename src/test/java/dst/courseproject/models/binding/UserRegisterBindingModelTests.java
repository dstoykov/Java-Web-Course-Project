package dst.courseproject.models.binding;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class UserRegisterBindingModelTests {
    private UserRegisterBindingModel user;
    private Validator validator;

    @Before
    public void init() {
        this.user = new UserRegisterBindingModel();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void userRegisterBindingModel_emailFieldWithNull_shouldHaveValidationError() {
        this.user.setEmail(null);
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword("12345678");
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = this.validator.validate(user);
        ConstraintViolation<UserRegisterBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Field must not be empty. ", violation.getMessage());
        Assert.assertEquals("email", violation.getPropertyPath().toString());
        Assert.assertNull(violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_emailFieldWithEmptyString_shouldHaveValidationErrors() {
        this.user.setEmail("");
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword("12345678");
        this.user.setConfirmPassword("12345678");

        List<ConstraintViolation<UserRegisterBindingModel>> violations = new ArrayList<>(this.validator.validate(user));
        ConstraintViolation<UserRegisterBindingModel> violation = violations.iterator().next();

        boolean violation0 = violations.get(0).getMessage().equals("Field must not be empty. ") || violations.get(0).getMessage().equals("Invalid email format. ");
        boolean violation1 = violations.get(1).getMessage().equals("Field must not be empty. ") || violations.get(1).getMessage().equals("Invalid email format. ");

        Assert.assertEquals(2, violations.size());
        Assert.assertEquals("email", violation.getPropertyPath().toString());
        Assert.assertTrue(violation0);
        Assert.assertTrue(violation1);
        Assert.assertEquals("", violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_emailFieldWithWrongFormat_shouldHaveValidationError() {
        this.user.setEmail("01email.com@alpha");
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword("12345678");
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = this.validator.validate(user);
        ConstraintViolation<UserRegisterBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Invalid email format. ", violation.getMessage());
        Assert.assertEquals("email", violation.getPropertyPath().toString());
        Assert.assertEquals("01email.com@alpha", violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_emailFieldGetterAndSetter_returnCorrect() {
        this.user.setEmail("mail@mail.com");
        String expected = "mail@mail.com";
        String actual = this.user.getEmail();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void userRegisterBindingModel_firstNameFieldWithNull_shouldHaveValidationError() {
        this.user.setEmail("mail@mail.com");
        this.user.setFirstName(null);
        this.user.setLastName("Peshev");
        this.user.setPassword("12345678");
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = this.validator.validate(user);
        ConstraintViolation<UserRegisterBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Field name must not be empty. ", violation.getMessage());
        Assert.assertEquals("firstName", violation.getPropertyPath().toString());
        Assert.assertNull(violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_firstNameFieldWithEmptyString_shouldHaveValidationErrors() {
        this.user.setEmail("mail@mail.com");
        this.user.setFirstName("");
        this.user.setLastName("Peshev");
        this.user.setPassword("12345678");
        this.user.setConfirmPassword("12345678");

        List<ConstraintViolation<UserRegisterBindingModel>> violations = new ArrayList<>(this.validator.validate(user));
        ConstraintViolation<UserRegisterBindingModel> violation = violations.iterator().next();

        boolean violation0 = violations.get(0).getMessage().equals("Field name must not be empty. ") || violations.get(0).getMessage().equals("Incorrect name format. ");
        boolean violation1 = violations.get(1).getMessage().equals("Field name must not be empty. ") || violations.get(1).getMessage().equals("Incorrect name format. ");

        Assert.assertEquals(2, violations.size());
        Assert.assertEquals("firstName", violation.getPropertyPath().toString());
        Assert.assertTrue(violation0);
        Assert.assertTrue(violation1);
        Assert.assertEquals("", violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_firstNameFieldWithWrongFormat_shouldHaveValidationError() {
        this.user.setEmail("mail@mail.com");
        this.user.setFirstName("PeSh0");
        this.user.setLastName("Peshev");
        this.user.setPassword("12345678");
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = this.validator.validate(user);
        ConstraintViolation<UserRegisterBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Incorrect name format. ", violation.getMessage());
        Assert.assertEquals("firstName", violation.getPropertyPath().toString());
        Assert.assertEquals("PeSh0", violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_firstNameFieldGetterAndSetter_returnCorrect() {
        this.user.setFirstName("PeShO");
        String expected = "PeShO";
        String actual = this.user.getFirstName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void userRegisterBindingModel_lastNameFieldWithNull_shouldHaveValidationError() {
        this.user.setEmail("mail@mail.com");
        this.user.setFirstName("Pesho");
        this.user.setLastName(null);
        this.user.setPassword("12345678");
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = this.validator.validate(user);
        ConstraintViolation<UserRegisterBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Field name must not be empty. ", violation.getMessage());
        Assert.assertEquals("lastName", violation.getPropertyPath().toString());
        Assert.assertNull(violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_lastNameFieldWithEmptyString_shouldHaveValidationErrors() {
        this.user.setEmail("mail@mail.com");
        this.user.setFirstName("Pesho");
        this.user.setLastName("");
        this.user.setPassword("12345678");
        this.user.setConfirmPassword("12345678");

        List<ConstraintViolation<UserRegisterBindingModel>> violations = new ArrayList<>(this.validator.validate(user));
        ConstraintViolation<UserRegisterBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(2, violations.size());
        Assert.assertEquals("lastName", violation.getPropertyPath().toString());
        Assert.assertTrue(violations.get(0).getMessage().equals("Field name must not be empty. ") || violations.get(0).getMessage().equals("Incorrect name format. "));
        Assert.assertTrue(violations.get(1).getMessage().equals("Field name must not be empty. ") || violations.get(1).getMessage().equals("Incorrect name format. "));
        Assert.assertEquals("", violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_lastNameFieldWithWrongFormat_shouldHaveValidationError() {
        this.user.setEmail("mail@mail.com");
        this.user.setFirstName("Pesho");
        this.user.setLastName("PeShEv1");
        this.user.setPassword("12345678");
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = this.validator.validate(user);
        ConstraintViolation<UserRegisterBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Incorrect name format. ", violation.getMessage());
        Assert.assertEquals("lastName", violation.getPropertyPath().toString());
        Assert.assertEquals("PeShEv1", violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_lastNameFieldGetterAndSetter_returnCorrect() {
        this.user.setLastName("PeShEv");
        String expected = "PeShEv";
        String actual = this.user.getLastName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void userRegisterBindingModel_passwordFieldWithNull_shouldHaveValidationError() {
        this.user.setEmail("mail@mail.com");
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword(null);
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = this.validator.validate(user);
        ConstraintViolation<UserRegisterBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Field must not be empty. ", violation.getMessage());
        Assert.assertEquals("password", violation.getPropertyPath().toString());
        Assert.assertNull(violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_passwordFieldWithEmptyString_shouldHaveValidationErrors() {
        this.user.setEmail("mail@mail.com");
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword("");
        this.user.setConfirmPassword("12345678");

        List<ConstraintViolation<UserRegisterBindingModel>> violations = new LinkedList<>(this.validator.validate(user));
        ConstraintViolation<UserRegisterBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(2, violations.size());
        Assert.assertEquals("password", violation.getPropertyPath().toString());
        Assert.assertTrue(violations.get(0).getMessage().equals("Password length must be between 8 and 20 symbols. ") || violations.get(0).getMessage().equals("Field must not be empty. "));
        Assert.assertTrue(violations.get(0).getMessage().equals("Password length must be between 8 and 20 symbols. ") || violations.get(0).getMessage().equals("Field must not be empty. "));
        Assert.assertEquals("", violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_passwordFieldWith7Symbols_shouldHaveValidationError() {
        this.user.setEmail("mail@mail.com");
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword("1234567");
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = this.validator.validate(user);
        ConstraintViolation<UserRegisterBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Password length must be between 8 and 20 symbols. ", violation.getMessage());
        Assert.assertEquals("password", violation.getPropertyPath().toString());
        Assert.assertEquals("1234567", violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_passwordFieldWith21Symbols_shouldHaveValidationError() {
        this.user.setEmail("mail@mail.com");
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword("123456789012345678901");
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = this.validator.validate(user);
        ConstraintViolation<UserRegisterBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Password length must be between 8 and 20 symbols. ", violation.getMessage());
        Assert.assertEquals("password", violation.getPropertyPath().toString());
        Assert.assertEquals("123456789012345678901", violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_passwordFieldWith8Symbols_shouldHaveValidationError() {
        this.user.setEmail("mail@mail.com");
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword("12345678");
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = this.validator.validate(user);

        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void userRegisterBindingModel_passwordFieldWith20Symbols_shouldHaveValidationError() {
        this.user.setEmail("mail@mail.com");
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword("12345678901234567890");
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = this.validator.validate(user);

        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void userRegisterBindingModel_passwordFieldGetterAndSetter_returnCorrect() {
        this.user.setPassword("123456789");
        String expected = "123456789";
        String actual = this.user.getPassword();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void userRegisterBindingModel_confirmPasswordFieldWithNull_shouldHaveValidationError() {
        this.user.setEmail("mail@mail.com");
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword("12345678");
        this.user.setConfirmPassword(null);

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = this.validator.validate(user);
        ConstraintViolation<UserRegisterBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Field must not be empty. ", violation.getMessage());
        Assert.assertNull(violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_confirmPasswordFieldWithEmptyString_shouldHaveValidationErrors() {
        this.user.setEmail("mail@mail.com");
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword("12345678");
        this.user.setConfirmPassword("");

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = (this.validator.validate(user));
        ConstraintViolation<UserRegisterBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("confirmPassword", violation.getPropertyPath().toString());
        Assert.assertEquals("Field must not be empty. ", violation.getMessage());
        Assert.assertEquals("", violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_confirmPasswordFieldGetterAndSetter_returnCorrect() {
        this.user.setConfirmPassword("123456789");
        String expected = "123456789";
        String actual = this.user.getConfirmPassword();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void userRegisterBindingModel_allFieldsWithCorrectValues_shoulHaveZeroErrors() {
        this.user.setEmail("mail@mail.com");
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword("123456789");
        this.user.setConfirmPassword("123456789");

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = this.validator.validate(this.user);

        Assert.assertEquals(0, violations.size());
    }
}