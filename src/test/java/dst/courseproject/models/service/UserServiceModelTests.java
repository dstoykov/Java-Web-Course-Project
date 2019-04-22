package dst.courseproject.models.service;

import dst.courseproject.entities.Role;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class UserServiceModelTests {
    private UserServiceModel userServiceModel;

    @Before
    public void init() {
        this.userServiceModel = new UserServiceModel();
    }

    @Test
    public void userViewModel_idFieldGettersAndSetters_returnCorrect() {
        this.userServiceModel.setId("1");
        String expected = "1";
        String actual = this.userServiceModel.getId();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void userViewModel_emailFieldGettersAndSetters_returnCorrect() {
        this.userServiceModel.setEmail("admin@admin.bg");
        String expected = "admin@admin.bg";
        String actual = this.userServiceModel.getEmail();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void userViewModel_firstNameFieldGettersAndSetters_returnCorrect() {
        this.userServiceModel.setFirstName("Admin");
        String expected = "Admin";
        String actual = this.userServiceModel.getFirstName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void userViewModel_lastNameFieldGettersAndSetters_returnCorrect() {
        this.userServiceModel.setLastName("Adminov");
        String expected = "Adminov";
        String actual = this.userServiceModel.getLastName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void userViewModel_passwordFieldGettersAndSetters_returnCorrect() {
        this.userServiceModel.setPassword("123456789");
        String expected = "123456789";
        String actual = this.userServiceModel.getPassword();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void userViewModel_videosFieldGettersAndSetters_returnCorrect() {
        List<VideoServiceModel> expected = new ArrayList<>(){{
            add(new VideoServiceModel(){{
                setId("1");
            }});
            add(new VideoServiceModel(){{
                setId("2");
            }});
            add(new VideoServiceModel(){{
                setId("3");
            }});
        }};

        this.userServiceModel.setVideos(new LinkedHashSet<>(expected));
        Set<VideoServiceModel> actual = this.userServiceModel.getVideos();
        int i = 0;

        Assert.assertEquals(expected.size(), actual.size());
        for (VideoServiceModel act : actual) {
            Assert.assertEquals(expected.get(i++).getId(), act.getId());
        }
    }

    @Test
    public void userViewModel_authoritiesFieldGettersAndSetters_returnCorrect() {
        List<Role> expected = new ArrayList<>(){{
            add(new Role(){{
                setId("1");
            }});
            add(new Role(){{
                setId("2");
            }});
            add(new Role(){{
                setId("3");
            }});
        }};

        this.userServiceModel.setAuthorities(new LinkedHashSet<>(expected));
        Set<Role> actual = this.userServiceModel.getAuthorities();
        int i = 0;

        Assert.assertEquals(expected.size(), actual.size());
        for (Role act : actual) {
            Assert.assertEquals(expected.get(i++).getId(), act.getId());
        }
    }

    @Test
    public void userViewModel_likedVideosFieldGettersAndSetters_returnCorrect() {
        Map<String, VideoServiceModel> expected = new LinkedHashMap<>(){{
            put("Video", new VideoServiceModel(){{
                setTitle("Video");
            }});
            put("Video2", new VideoServiceModel(){{
                setTitle("Video2");
            }});
            put("Video3", new VideoServiceModel(){{
                setTitle("Video3");
            }});
        }};

        this.userServiceModel.setLikedVideos(expected);
        Map<String, VideoServiceModel> actual = this.userServiceModel.getLikedVideos();

        Assert.assertEquals(expected.size(), actual.size());
        for (String key : actual.keySet()) {
            Assert.assertEquals(expected.get(key), actual.get(key));
        }
    }
}
