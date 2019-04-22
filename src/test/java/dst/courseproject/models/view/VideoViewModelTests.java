package dst.courseproject.models.view;

import dst.courseproject.entities.Category;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
public class VideoViewModelTests {
    private VideoViewModel videoViewModel;

    @Before
    public void init() {
        this.videoViewModel = new VideoViewModel();
    }

    @Test
    public void videoViewModel_idFieldGetterAndSetter_returnCorrect() {
        this.videoViewModel.setId("1");
        String expected = "1";
        String actual = this.videoViewModel.getId();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoViewModel_titleFieldGetterAndSetter_returnCorrect() {
        this.videoViewModel.setTitle("Video");
        String expected = "Video";
        String actual = this.videoViewModel.getTitle();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoViewModel_videoIdentifierFieldGetterAndSetter_returnCorrect() {
        this.videoViewModel.setVideoIdentifier("AbCdE7");
        String expected = "AbCdE7";
        String actual = this.videoViewModel.getVideoIdentifier();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoViewModel_descriptionIdentifierFieldGetterAndSetter_returnCorrect() {
        this.videoViewModel.setDescription("Long description.");
        String expected = "Long description.";
        String actual = this.videoViewModel.getDescription();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoViewModel_authorIdentifierFieldGetterAndSetter_returnCorrect() {
        this.videoViewModel.setAuthor(new UserViewModel(){{setFirstName("User");}});
        String expected = "User";
        String actual = this.videoViewModel.getAuthor().getFirstName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoViewModel_categoryIdentifierFieldGetterAndSetter_returnCorrect() {
        this.videoViewModel.setCategory(new CategoryViewModel(){{setName("Music");}});
        String expected = "Music";
        String actual = this.videoViewModel.getCategory().getName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoViewModel_commentsIdentifierFieldGetterAndSetter_returnCorrect() {
        List<CommentViewModel> expected = new ArrayList<>(){{
            add(new CommentViewModel(){{
                setContent("Long comment!");
            }});
            add(new CommentViewModel(){{
                setContent("Long comment2");
            }});
            add(new CommentViewModel(){{
                setContent("So long comment!");
            }});
        }};
        this.videoViewModel.setComments(new LinkedHashSet<>(expected));
        Set<CommentViewModel> actual = this.videoViewModel.getComments();
        int i = 0;

        Assert.assertEquals(expected.size(), actual.size());
        for (CommentViewModel act : actual) {
            Assert.assertEquals(expected.get(i++).getContent(), act.getContent());
        }
    }

    @Test
    public void videoViewModel_viewsIdentifierFieldGetterAndSetter_returnCorrect() {
        this.videoViewModel.setViews(123L);
        Long expected = 123L;
        Long actual = this.videoViewModel.getViews();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoViewModel_uploadOnIdentifierFieldGetterAndSetter_returnCorrect() {
        LocalDateTime expected = LocalDateTime.now();
        this.videoViewModel.setUploadedOn(expected);
        LocalDateTime actual = this.videoViewModel.getUploadedOn();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoViewModel_usersLikedIdentifierFieldGetterAndSetter_returnCorrect() {
        Map<String, UserViewModel> expected = new LinkedHashMap<>(){{
            put("1", new UserViewModel(){{
                setId("1");
                setFirstName("Pesho");
            }});
            put("2", new UserViewModel(){{
                setId("2");
                setFirstName("Gosho");
            }});
            put("3", new UserViewModel(){{
                setId("3");
                setFirstName("Stamat");
            }});
        }};
        this.videoViewModel.setUsersLiked(expected);
        Map<String, UserViewModel> actual = this.videoViewModel.getUsersLiked();

        Assert.assertEquals(expected.size(), actual.size());
        for (String key : actual.keySet()) {
            Assert.assertEquals(expected.get(key), actual.get(key));
        }
    }

    @Test
    public void videoViewModel_thumbnailIdentifierFieldGetterAndSetter_returnCorrect() {
        this.videoViewModel.setThumbnailUrl("https://link.com?q=query");
        String expected = "https://link.com?q=query";
        String actual = this.videoViewModel.getThumbnailUrl();

        Assert.assertEquals(expected, actual);
    }
}
