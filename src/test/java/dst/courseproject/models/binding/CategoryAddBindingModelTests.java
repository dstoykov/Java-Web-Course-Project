package dst.courseproject.models.binding;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class CategoryAddBindingModelTests {
    private CategoryAddBindingModel category;
    private Validator validator;

    @Before
    public void init() {
        this.category = new CategoryAddBindingModel();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void categoryAddBindingModel_nameFieldWithNull_shouldHaveValidationError() {
        this.category.setName(null);

        Set<ConstraintViolation<CategoryAddBindingModel>> violations = this.validator.validate(this.category);
        ConstraintViolation<CategoryAddBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("name", violation.getPropertyPath().toString());
        Assert.assertEquals("Field cannot be empty.", violation.getMessage());
        Assert.assertNull(violation.getInvalidValue());
    }

    @Test
    public void categoryAddBindingModel_nameFieldWithEmptyString_shouldHaveValidationError() {
        this.category.setName("");

        List<ConstraintViolation<CategoryAddBindingModel>> violations = new LinkedList<>(this.validator.validate(this.category));
        ConstraintViolation<CategoryAddBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(2, violations.size());
        Assert.assertEquals("name", violation.getPropertyPath().toString());
        Assert.assertEquals("Field cannot be empty.", violations.get(0).getMessage());
        Assert.assertEquals("Incorrect name format. ", violations.get(1).getMessage());
        Assert.assertEquals("", violation.getInvalidValue());
    }

    @Test
    public void categoryAddBindingModel_nameFieldWithIncorrectFormat_shouldHaveValidationError() {
        this.category.setName("p01g32?!#");

        List<ConstraintViolation<CategoryAddBindingModel>> violations = new LinkedList<>(this.validator.validate(this.category));
        ConstraintViolation<CategoryAddBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("name", violation.getPropertyPath().toString());
        Assert.assertEquals("Incorrect name format. ", violation.getMessage());
        Assert.assertEquals("p01g32?!#", violation.getInvalidValue());
    }

    @Test
    public void categoryAddBindingModel_nameFieldGetterAndSetter_returnCorrect() {
        this.category.setName("Name");
        String expected = "Name";
        String actual = this.category.getName();

        Assert.assertEquals(expected, actual);
    }
}
