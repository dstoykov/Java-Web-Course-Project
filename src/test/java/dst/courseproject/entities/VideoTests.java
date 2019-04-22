package dst.courseproject.entities;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootTest
public class VideoTests {
    private Video video = new Video();

    @Test
    public void video_idFieldGetterAndSetter_returnCorrect() {
        this.video.setId("123");
        String expected = "123";
        String actual = this.video.getId();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void video_titleFieldGetterAndSetter_returnCorrect() {
        this.video.setTitle("Title");
        String expected = "Title";
        String actual = this.video.getTitle();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void video_videoIdentifierFieldGetterAndSetter_returnCorrect() {
        this.video.setVideoIdentifier("aBcDeFg");
        String expected = "aBcDeFg";
        String actual = this.video.getVideoIdentifier();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void video_descriptionFieldGetterAndSetter_returnCorrect() {
        this.video.setDescription("Description");
        String expected = "Description";
        String actual = this.video.getDescription();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void video_authorFieldGetterAndSetter_returnCorrect() {
        this.video.setAuthor(new User(){{
            setFirstName("Petko");
            setLastName("Petkov");
        }});
        User expected = new User(){{
            setFirstName("Petko");
            setLastName("Petkov");
        }};
        User actual = this.video.getAuthor();

        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals(expected.getLastName(), actual.getLastName());
    }

    @Test
    public void video_categoryFieldGetterAndSetter() {
        this.video.setCategory(new Category(){{
            setName("Music");
        }});
        Category expected = new Category(){{setName("Music");}};
        Category actual = this.video.getCategory();

        Assert.assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void video_commentsFieldGetterAndSetter_returnTrue() {
        Set<Comment> expected = new HashSet<>(){{
            add(new Comment(){{
                setId("12");
            }});
            add(new Comment(){{
                setId("23");
            }});
        }};
        this.video.setComments(expected);
        Set<Comment> actual = this.video.getComments();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void video_viewsFieldGetterAndSetter_returnCorrect() {
        this.video.setViews(1000000L);
        long expected = 1000000L;
        long actual = this.video.getViews();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void video_uploadedOnFieldGetterAndSetter_returnCorrect() {
        LocalDateTime expected = LocalDateTime.now();
        this.video.setUploadedOn(expected);
        LocalDateTime actual = this.video.getUploadedOn();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void video_usersLikedFieldGetterAndSetter_returnCorrect() {
        Map<String, User> expected = new HashMap<>(){{
            put("123", new User(){{
                setId("123");
            }});
            put("234", new User(){{
                setId("234");
            }});
        }};
        this.video.setUsersLiked(expected);
        Map<String, User> actual = this.video.getUsersLiked();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void video_deletedOnFieldGetterAndSetter_returnCorrect() {
        LocalDate expected = LocalDate.now();
        this.video.setDeletedOn(expected);
        LocalDate actual = this.video.getDeletedOn();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void video_thumbnailUrlFieldGetterAndSetter_returnCorrect() {
        this.video.setThumbnailUrl("https://url.com?query=q");
        String expected = "https://url.com?query=q";
        String actual = this.video.getThumbnailUrl();

        Assert.assertEquals(expected, actual);
    }
}
