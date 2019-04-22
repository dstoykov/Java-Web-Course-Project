package dst.courseproject.models.binding;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class UserEditBindingModelTests {
    private UserEditBindingModel user;
    private Validator validator;

    @Before
    public void init() {
        this.user = new UserEditBindingModel();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void userRegisterBindingModel_idFieldGetterAndSetter_returnCorrect() {
        this.user.setId("12");
        String expected = "12";
        String actual = this.user.getId();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void userRegisterBindingModel_firstNameFieldWithNull_shouldHaveValidationError() {
        this.user.setFirstName(null);
        this.user.setLastName("Peshev");
        this.user.setPassword("12345678");
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserEditBindingModel>> violations = this.validator.validate(user);
        ConstraintViolation<UserEditBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Field name must not be empty. ", violation.getMessage());
        Assert.assertEquals("firstName", violation.getPropertyPath().toString());
        Assert.assertNull(violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_firstNameFieldWithEmptyString_shouldHaveValidationErrors() {
        this.user.setFirstName("");
        this.user.setLastName("Peshev");
        this.user.setPassword("12345678");
        this.user.setConfirmPassword("12345678");

        List<ConstraintViolation<UserEditBindingModel>> violations = new ArrayList<>(this.validator.validate(user));
        ConstraintViolation<UserEditBindingModel> violation = violations.iterator().next();

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
        this.user.setFirstName("PeSh0");
        this.user.setLastName("Peshev");
        this.user.setPassword("12345678");
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserEditBindingModel>> violations = this.validator.validate(user);
        ConstraintViolation<UserEditBindingModel> violation = violations.iterator().next();

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
        this.user.setFirstName("Pesho");
        this.user.setLastName(null);
        this.user.setPassword("12345678");
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserEditBindingModel>> violations = this.validator.validate(user);
        ConstraintViolation<UserEditBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Field name must not be empty. ", violation.getMessage());
        Assert.assertEquals("lastName", violation.getPropertyPath().toString());
        Assert.assertNull(violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_lastNameFieldWithEmptyString_shouldHaveValidationErrors() {
        this.user.setFirstName("Pesho");
        this.user.setLastName("");
        this.user.setPassword("12345678");
        this.user.setConfirmPassword("12345678");

        List<ConstraintViolation<UserEditBindingModel>> violations = new ArrayList<>(this.validator.validate(user));
        ConstraintViolation<UserEditBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(2, violations.size());
        Assert.assertEquals("lastName", violation.getPropertyPath().toString());
        Assert.assertEquals("Field name must not be empty. ", violations.get(1).getMessage());
        Assert.assertEquals("Incorrect name format. ", violations.get(0).getMessage());
        Assert.assertEquals("", violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_lastNameFieldWithWrongFormat_shouldHaveValidationError() {
        this.user.setFirstName("Pesho");
        this.user.setLastName("PeShEv1");
        this.user.setPassword("12345678");
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserEditBindingModel>> violations = this.validator.validate(user);
        ConstraintViolation<UserEditBindingModel> violation = violations.iterator().next();

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
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword(null);
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserEditBindingModel>> violations = this.validator.validate(user);
        ConstraintViolation<UserEditBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Field must not be empty. ", violation.getMessage());
        Assert.assertEquals("password", violation.getPropertyPath().toString());
        Assert.assertNull(violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_passwordFieldWithEmptyString_shouldHaveValidationErrors() {
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword("");
        this.user.setConfirmPassword("12345678");

        List<ConstraintViolation<UserEditBindingModel>> violations = new ArrayList<>(this.validator.validate(user));
        ConstraintViolation<UserEditBindingModel> violation = violations.iterator().next();

        boolean violation0 = violations.get(0).getMessage().equals("Field must not be empty. ") || violations.get(0).getMessage().equals("Password length must be between 8 and 20 symbols. ");
        boolean violation1 = violations.get(1).getMessage().equals("Field must not be empty. ") || violations.get(1).getMessage().equals("Password length must be between 8 and 20 symbols. ");

        Assert.assertEquals(2, violations.size());
        Assert.assertEquals("password", violation.getPropertyPath().toString());
        Assert.assertTrue(violation0);
        Assert.assertTrue(violation1);
        Assert.assertEquals("", violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_passwordFieldWith7Symbols_shouldHaveValidationError() {
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword("1234567");
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserEditBindingModel>> violations = this.validator.validate(user);
        ConstraintViolation<UserEditBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Password length must be between 8 and 20 symbols. ", violation.getMessage());
        Assert.assertEquals("password", violation.getPropertyPath().toString());
        Assert.assertEquals("1234567", violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_passwordFieldWith21Symbols_shouldHaveValidationError() {
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword("123456789012345678901");
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserEditBindingModel>> violations = this.validator.validate(user);
        ConstraintViolation<UserEditBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Password length must be between 8 and 20 symbols. ", violation.getMessage());
        Assert.assertEquals("password", violation.getPropertyPath().toString());
        Assert.assertEquals("123456789012345678901", violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_passwordFieldWith8Symbols_shouldHaveValidationError() {
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword("12345678");
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserEditBindingModel>> violations = this.validator.validate(user);

        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void userRegisterBindingModel_passwordFieldWith20Symbols_shouldHaveValidationError() {
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword("12345678901234567890");
        this.user.setConfirmPassword("12345678");

        Set<ConstraintViolation<UserEditBindingModel>> violations = this.validator.validate(user);

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
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword("12345678");
        this.user.setConfirmPassword(null);

        Set<ConstraintViolation<UserEditBindingModel>> violations = this.validator.validate(user);
        ConstraintViolation<UserEditBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Field must not be empty. ", violation.getMessage());
        Assert.assertNull(violation.getInvalidValue());
    }

    @Test
    public void userRegisterBindingModel_confirmPasswordFieldWithEmptyString_shouldHaveValidationErrors() {
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword("12345678");
        this.user.setConfirmPassword("");

        Set<ConstraintViolation<UserEditBindingModel>> violations = (this.validator.validate(user));
        ConstraintViolation<UserEditBindingModel> violation = violations.iterator().next();

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
        this.user.setFirstName("Pesho");
        this.user.setLastName("Peshev");
        this.user.setPassword("123456789");
        this.user.setConfirmPassword("123456789");

        Set<ConstraintViolation<UserEditBindingModel>> violations = this.validator.validate(this.user);

        Assert.assertEquals(0, violations.size());
    }
}
