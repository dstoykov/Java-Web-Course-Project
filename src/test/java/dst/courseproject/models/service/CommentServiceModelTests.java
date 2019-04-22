package dst.courseproject.models.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class CommentServiceModelTests {
    private CommentServiceModel commentServiceModel;

    @Before
    public void init() {
        this.commentServiceModel = new CommentServiceModel();
    }

    @Test
    public void commentViewModel_idFieldGetterAndSetter_returnCorrect() {
        this.commentServiceModel.setId("1");
        String expected = "1";
        String actual = this.commentServiceModel.getId();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void commentViewModel_contentFieldGetterAndSetter_returnCorrect() {
        this.commentServiceModel.setContent("Content");
        String expected = "Content";
        String actual = this.commentServiceModel.getContent();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void commentViewModel_authorFieldGetterAndSetter_returnCorrect() {
        this.commentServiceModel.setAuthor(new UserServiceModel(){{setFirstName("Author");}});
        String expected = "Author";
        String actual = this.commentServiceModel.getAuthor().getFirstName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void commentViewModel_deletedOnFieldGetterAndSetter_returnCorrect() {
        this.commentServiceModel.setDeletedOn(LocalDate.now());

        Assert.assertNotNull(this.commentServiceModel.getDeletedOn());
    }
}
