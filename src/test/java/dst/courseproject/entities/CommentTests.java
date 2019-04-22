package dst.courseproject.entities;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class CommentTests {
    private Comment comment = new Comment();

    @Test
    public void comment_idFieldGetterAndSetter_returnCorrect() {
        this.comment.setId("123");
        String expected = "123";
        String actual = this.comment.getId();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void comment_contentFieldGetterAndSetter_returnCorrect() {
        this.comment.setContent("Content.");
        String expected = "Content.";
        String actual = this.comment.getContent();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void comment_videoGetterAndSetter_returnCorrect() {
        Video expected = new Video() {{
            setId("123");
            setTitle("Title");
        }};
        this.comment.setVideo(expected);
        Video actual = this.comment.getVideo();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void comment_authorFieldGetterAndSetter_returnCorrect() {
        User expected = new User() {{
            setId("123");
            setFirstName("Pesho");
            setLastName("Peshev");
        }};
        this.comment.setAuthor(expected);
        User actual = this.comment.getAuthor();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void comment_dateOfPublishingFieldGetterAndSetter_returnCorrect() {
        LocalDateTime expected = LocalDateTime.now();
        this.comment.setDateOfPublishing(expected);
        LocalDateTime actual = this.comment.getDateOfPublishing();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void comment_deletedOnFieldGetterAndSetter_returnCorrect() {
        this.comment.setDeletedOn(LocalDate.now());
        Assert.assertNotNull(this.comment.getDeletedOn());
    }
}
