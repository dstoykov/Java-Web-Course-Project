package dst.courseproject.models.binding;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@SpringBootTest
public class CommentAddBindingModelTests {
    private CommentAddBindingModel comment;
    private Validator validator;

    @Before
    public void init() {
        this.comment = new CommentAddBindingModel();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void commentAddBindingModel_contentFieldWithNull_shouldHaveValidationError() {
        this.comment.setContent(null);

        Set<ConstraintViolation<CommentAddBindingModel>> violations = this.validator.validate(this.comment);
        ConstraintViolation<CommentAddBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("You have to write comment!", violation.getMessage());
        Assert.assertEquals("content", violation.getPropertyPath().toString());
        Assert.assertNull(violation.getInvalidValue());
    }

    @Test
    public void commentAddBindingModel_contentFieldWithEmptyString_shouldHaveValidationError() {
        this.comment.setContent("");

        Set<ConstraintViolation<CommentAddBindingModel>> violations = this.validator.validate(this.comment);
        ConstraintViolation<CommentAddBindingModel> violation = violations.iterator().next();

        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("You have to write comment!", violation.getMessage());
        Assert.assertEquals("content", violation.getPropertyPath().toString());
        Assert.assertEquals("", violation.getInvalidValue());
    }

    @Test
    public void commentAddBindingModel_contentFieldWithCorrectValue_shouldNotHaveValidationErrors() {
        this.comment.setContent("Long content.");

        Set<ConstraintViolation<CommentAddBindingModel>> violations = this.validator.validate(this.comment);

        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void commentAddBindingModel_contentFieldGetterAndSetter_returnCorrect() {
        this.comment.setContent("Another long content...");
        String expected = "Another long content...";
        String actual = this.comment.getContent();

        Assert.assertEquals(expected, actual);
    }
}
