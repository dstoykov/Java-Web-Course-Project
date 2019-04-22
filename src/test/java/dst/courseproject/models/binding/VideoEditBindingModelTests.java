package dst.courseproject.models.binding;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class VideoEditBindingModelTests {
    private VideoEditBindingModel video;
    private ValidatorFactory validatorFactory;
    private Validator validator;

    @Before
    public void init() {
        this.video = new VideoEditBindingModel();
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    @Test
    public void videoEditBindingModel_titleFieldWithForbiddenSymbols_shouldHaveValidationError() {
        this.video.setTitle("@!#");
        this.video.setDescription("Long description.");
        this.video.setCategory("Music");

        Set<ConstraintViolation<VideoEditBindingModel>> violations = this.validator.validate(this.video);
        ConstraintViolation<VideoEditBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Title can only have capital and small letters, number and punctuation[ '!', '?', '.', ','].", violation.getMessage());
        Assert.assertEquals("title", violation.getPropertyPath().toString());
        Assert.assertEquals("@!#", violation.getInvalidValue());
    }

    @Test
    public void videoEditBindingModel_titleFieldWith51Symbols_shouldHaveValidationError() {
        this.video.setTitle(".,!?1234567890qwertyuioplkjhgfdsazxcvbnmqwertyuiopl");
        this.video.setDescription("Long description.");
        this.video.setCategory("Music");

        Set<ConstraintViolation<VideoEditBindingModel>> violations = this.validator.validate(this.video);
        ConstraintViolation<VideoEditBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Title must not be longer than 50 symbols.", violation.getMessage());
        Assert.assertEquals("title", violation.getPropertyPath().toString());
        Assert.assertEquals(".,!?1234567890qwertyuioplkjhgfdsazxcvbnmqwertyuiopl", violation.getInvalidValue());
    }

    @Test
    public void videoEditBindingModel_titleFieldWith50Symbols_shouldHaveValidationError() {
        this.video.setTitle(".,!?1234567890qwertyuioplkjhgfdsazxcvbnmqwertyuiop");
        this.video.setDescription("Long description.");
        this.video.setCategory("Music");

        Set<ConstraintViolation<VideoEditBindingModel>> violations = this.validator.validate(this.video);

        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void videoEditBindingModel_titleFieldWithNull_shouldHaveValidationError() {
        this.video.setTitle(null);
        this.video.setDescription("Long description.");
        this.video.setCategory("Music");

        Set<ConstraintViolation<VideoEditBindingModel>> violations = this.validator.validate(this.video);
        ConstraintViolation<VideoEditBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Title must not be empty. ", violation.getMessage());
        Assert.assertEquals("title", violation.getPropertyPath().toString());
        Assert.assertNull(violation.getInvalidValue());
    }

    @Test
    public void videoEditBindingModel_titleFieldWithEmptyString_shouldHaveValidationError() {
        this.video.setTitle("");
        this.video.setDescription("Long description.");
        this.video.setCategory("Music");

        List<ConstraintViolation<VideoEditBindingModel>> violations = new ArrayList<>(this.validator.validate(this.video));
        ConstraintViolation<VideoEditBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(2, violations.size());
        Assert.assertEquals("Title can only have capital and small letters, number and punctuation[ '!', '?', '.', ','].", violations.get(0).getMessage());
        Assert.assertEquals("Title must not be empty. ", violations.get(1).getMessage());
        Assert.assertEquals("title", violations.get(0).getPropertyPath().toString());
        Assert.assertEquals("title", violations.get(1).getPropertyPath().toString());
        Assert.assertEquals("", violations.get(0).getInvalidValue());
        Assert.assertEquals("", violations.get(1).getInvalidValue());
    }

    @Test
    public void videoEditBindingModel_titleFieldGetterAndSetter_shouldReturnCorrect() {
        this.video.setTitle("Title0!?,.");
        String expected = "Title0!?,.";
        String actual = this.video.getTitle();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoEditBindingModel_descriptionFieldWithNull_shouldHaveValidationError() {
        this.video.setTitle("Title");
        this.video.setDescription(null);
        this.video.setCategory("Music");

        Set<ConstraintViolation<VideoEditBindingModel>> violations = this.validator.validate(this.video);
        ConstraintViolation<VideoEditBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Description must not be empty.", violation.getMessage());
        Assert.assertEquals("description", violation.getPropertyPath().toString());
        Assert.assertNull(violation.getInvalidValue());
    }

    @Test
    public void videoEditBindingModel_descriptionFieldWithEmptyString_shouldHaveValidationError() {
        this.video.setTitle("Title");
        this.video.setDescription("");
        this.video.setCategory("Music");

        Set<ConstraintViolation<VideoEditBindingModel>> violations = (this.validator.validate(this.video));
        ConstraintViolation<VideoEditBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Description must not be empty.", violation.getMessage());
        Assert.assertEquals("description", violation.getPropertyPath().toString());
        Assert.assertEquals("", violation.getInvalidValue());
    }

    @Test
    public void videoEditBindingModel_descriptionFieldGetterAndSetter_shouldReturnCorrect() {
        this.video.setDescription("Long description");
        String expected = "Long description";
        String actual = this.video.getDescription();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoEditBindingModel_categoryFieldWithNull_shouldHaveValidationError() {
        this.video.setTitle("Title");
        this.video.setDescription("Long description");
        this.video.setCategory(null);

        Set<ConstraintViolation<VideoEditBindingModel>> violations = this.validator.validate(this.video);
        ConstraintViolation<VideoEditBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("You must choose a category.", violation.getMessage());
        Assert.assertEquals("category", violation.getPropertyPath().toString());
        Assert.assertNull(violation.getInvalidValue());
    }

    @Test
    public void videoEditBindingModel_categoryFieldWithEmptyString_shouldHaveValidationError() {
        this.video.setTitle("Title");
        this.video.setDescription("Long description");
        this.video.setCategory("");

        Set<ConstraintViolation<VideoEditBindingModel>> violations = (this.validator.validate(this.video));
        ConstraintViolation<VideoEditBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("You must choose a category.", violation.getMessage());
        Assert.assertEquals("category", violation.getPropertyPath().toString());
        Assert.assertEquals("", violation.getInvalidValue());
    }

    @Test
    public void videoEditBindingModel_categoryFieldGetterAndSetter_shouldReturnCorrect() {
        this.video.setCategory("Sport");
        String expected = "Sport";
        String actual = this.video.getCategory();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoEditBindingModel_identifierFieldGetterAndSetter_shouldReturnCorrect() {
        this.video.setIdentifier("aBcDe");
        String expected = "aBcDe";
        String actual = this.video.getIdentifier();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoEditBindingModel_allFieldsWithCorrectSymbols_shouldHaveZeroErrors() {
        this.video.setTitle("Title0!?,.");
        this.video.setDescription("Long description.!@#$%^&*()");
        this.video.setCategory("Music");

        Set<ConstraintViolation<VideoEditBindingModel>> violations = this.validator.validate(this.video);

        Assert.assertTrue(violations.isEmpty());
    }

    @After
    public void finish() {
        this.validatorFactory.close();
    }
}
