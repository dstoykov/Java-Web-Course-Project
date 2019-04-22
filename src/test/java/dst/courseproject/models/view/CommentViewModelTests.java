package dst.courseproject.models.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentViewModelTests {
    private CommentViewModel commentViewModel;

    @Before
    public void init() {
        this.commentViewModel = new CommentViewModel();
    }

    @Test
    public void commentViewModel_idFieldGetterAndSetter_returnCorrect() {
        this.commentViewModel.setId("1");
        String expected = "1";
        String actual = this.commentViewModel.getId();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void commentViewModel_contentFieldGetterAndSetter_returnCorrect() {
        this.commentViewModel.setContent("Content");
        String expected = "Content";
        String actual = this.commentViewModel.getContent();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void commentViewModel_authorFieldGetterAndSetter_returnCorrect() {
        this.commentViewModel.setAuthor("Pesho");
        String expected = "Pesho";
        String actual = this.commentViewModel.getAuthor();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void commentViewModel_dateOfPublishingFieldGetterAndSetter_returnCorrect() {
        this.commentViewModel.setDateOfPublishing("11.11.2018");
        String expected = "11.11.2018";
        String actual = this.commentViewModel.getDateOfPublishing();

        Assert.assertEquals(expected, actual);
    }
}
