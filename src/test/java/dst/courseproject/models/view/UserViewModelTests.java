package dst.courseproject.models.view;

import dst.courseproject.entities.Role;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
public class UserViewModelTests {
    private UserViewModel userViewModel;

    @Before
    public void init() {
        this.userViewModel = new UserViewModel();
    }

    @Test
    public void userViewModel_idFieldGettersAndSetters_returnCorrect() {
        this.userViewModel.setId("1");
        String expected = "1";
        String actual = this.userViewModel.getId();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void userViewModel_emailFieldGettersAndSetters_returnCorrect() {
        this.userViewModel.setEmail("admin@admin.bg");
        String expected = "admin@admin.bg";
        String actual = this.userViewModel.getEmail();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void userViewModel_firstNameFieldGettersAndSetters_returnCorrect() {
        this.userViewModel.setFirstName("Admin");
        String expected = "Admin";
        String actual = this.userViewModel.getFirstName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void userViewModel_lastNameFieldGettersAndSetters_returnCorrect() {
        this.userViewModel.setLastName("Adminov");
        String expected = "Adminov";
        String actual = this.userViewModel.getLastName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void userViewModel_videosFieldGettersAndSetters_returnCorrect() {
        List<VideoViewModel> expected = new ArrayList<>(){{
            add(new VideoViewModel(){{
                setId("1");
            }});
            add(new VideoViewModel(){{
                setId("2");
            }});
            add(new VideoViewModel(){{
                setId("3");
            }});
        }};

        this.userViewModel.setVideos(new LinkedHashSet<>(expected));
        Set<VideoViewModel> actual = this.userViewModel.getVideos();
        int i = 0;

        Assert.assertEquals(expected.size(), actual.size());
        for (VideoViewModel act : actual) {
            Assert.assertEquals(expected.get(i++).getId(), act.getId());
        }
    }

    @Test
    public void userViewModel_commentsFieldGettersAndSetters_returnCorrect() {
        List<CommentViewModel> expected = new ArrayList<>(){{
            add(new CommentViewModel(){{
                setId("1");
            }});
            add(new CommentViewModel(){{
                setId("2");
            }});
            add(new CommentViewModel(){{
                setId("3");
            }});
        }};

        this.userViewModel.setComments(new LinkedHashSet<>(expected));
        Set<CommentViewModel> actual = this.userViewModel.getComments();
        int i = 0;

        Assert.assertEquals(expected.size(), actual.size());
        for (CommentViewModel act : actual) {
            Assert.assertEquals(expected.get(i++).getId(), act.getId());
        }
    }

    @Test
    public void userViewModel_deletedOnFieldGettersAndSetters_returnCorrect() {
        LocalDate expected = LocalDate.now();
        this.userViewModel.setDeletedOn(expected);
        LocalDate actual = this.userViewModel.getDeletedOn();

        Assert.assertEquals(expected, actual);
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

        this.userViewModel.setAuthorities(new LinkedHashSet<>(expected));
        Set<Role> actual = this.userViewModel.getAuthorities();
        int i = 0;

        Assert.assertEquals(expected.size(), actual.size());
        for (Role act : actual) {
            Assert.assertEquals(expected.get(i++).getId(), act.getId());
        }
    }
}
