package dst.courseproject.models.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
public class VideoServiceModelTests {
    private VideoServiceModel videoServiceModel;

    @Before
    public void init() {
        this.videoServiceModel = new VideoServiceModel();
    }

    @Test
    public void videoViewModel_idFieldGetterAndSetter_returnCorrect() {
        this.videoServiceModel.setId("1");
        String expected = "1";
        String actual = this.videoServiceModel.getId();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoViewModel_titleFieldGetterAndSetter_returnCorrect() {
        this.videoServiceModel.setTitle("Video");
        String expected = "Video";
        String actual = this.videoServiceModel.getTitle();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoViewModel_videoIdentifierFieldGetterAndSetter_returnCorrect() {
        this.videoServiceModel.setVideoIdentifier("AbCdE7");
        String expected = "AbCdE7";
        String actual = this.videoServiceModel.getVideoIdentifier();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoViewModel_descriptionIdentifierFieldGetterAndSetter_returnCorrect() {
        this.videoServiceModel.setDescription("Long description.");
        String expected = "Long description.";
        String actual = this.videoServiceModel.getDescription();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoViewModel_authorIdentifierFieldGetterAndSetter_returnCorrect() {
        this.videoServiceModel.setAuthor(new UserServiceModel(){{setFirstName("User");}});
        String expected = "User";
        String actual = this.videoServiceModel.getAuthor().getFirstName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoViewModel_categoryIdentifierFieldGetterAndSetter_returnCorrect() {
        this.videoServiceModel.setCategory(new CategoryServiceModel(){{setName("Music");}});
        String expected = "Music";
        String actual = this.videoServiceModel.getCategory().getName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoViewModel_commentsIdentifierFieldGetterAndSetter_returnCorrect() {
        List<CommentServiceModel> expected = new ArrayList<>(){{
            add(new CommentServiceModel(){{
                setContent("Long comment!");
            }});
            add(new CommentServiceModel(){{
                setContent("Long comment2");
            }});
            add(new CommentServiceModel(){{
                setContent("So long comment!");
            }});
        }};
        this.videoServiceModel.setComments(new LinkedHashSet<>(expected));
        Set<CommentServiceModel> actual = this.videoServiceModel.getComments();
        int i = 0;

        Assert.assertEquals(expected.size(), actual.size());
        for (CommentServiceModel act : actual) {
            Assert.assertEquals(expected.get(i++).getContent(), act.getContent());
        }
    }

    @Test
    public void videoViewModel_viewsIdentifierFieldGetterAndSetter_returnCorrect() {
        this.videoServiceModel.setViews(123L);
        Long expected = 123L;
        Long actual = this.videoServiceModel.getViews();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoViewModel_uploadOnIdentifierFieldGetterAndSetter_returnCorrect() {
        LocalDateTime expected = LocalDateTime.now();
        this.videoServiceModel.setUploadedOn(expected);
        LocalDateTime actual = this.videoServiceModel.getUploadedOn();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoViewModel_usersLikedIdentifierFieldGetterAndSetter_returnCorrect() {
        Map<String, UserServiceModel> expected = new LinkedHashMap<>(){{
            put("1", new UserServiceModel(){{
                setId("1");
                setFirstName("Pesho");
            }});
            put("2", new UserServiceModel(){{
                setId("2");
                setFirstName("Gosho");
            }});
            put("3", new UserServiceModel(){{
                setId("3");
                setFirstName("Stamat");
            }});
        }};
        this.videoServiceModel.setUsersLiked(expected);
        Map<String, UserServiceModel> actual = this.videoServiceModel.getUsersLiked();

        Assert.assertEquals(expected.size(), actual.size());
        for (String key : actual.keySet()) {
            Assert.assertEquals(expected.get(key), actual.get(key));
        }
    }

    @Test
    public void videoViewModel_thumbnailIdentifierFieldGetterAndSetter_returnCorrect() {
        this.videoServiceModel.setThumbnailUrl("https://link.com?q=query");
        String expected = "https://link.com?q=query";
        String actual = this.videoServiceModel.getThumbnailUrl();

        Assert.assertEquals(expected, actual);
    }
}
