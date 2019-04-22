package dst.courseproject.entities;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
public class UserTests {
    private User user = new User();

    @Test
    public void user_idFieldGetterAndSetter_returnCorrect() {
        this.user.setId("123");
        String expected = "123";
        String actual = this.user.getId();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void user_emailFieldGetterAndSetter_returnCorrect() {
        this.user.setEmail("email@example.com");
        String expected = "email@example.com";
        String actual = this.user.getEmail();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void user_firstNameFieldGetterAndSetter_returnCorrect() {
        this.user.setFirstName("Name");
        String expected = "Name";
        String actual = this.user.getFirstName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void user_lastNameFieldGetterAndSetter_returnCorrect() {
        this.user.setLastName("Namov");
        String expected = "Namov";
        String actual = this.user.getLastName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void user_passwordFieldGetterAndSetter_returnCorrect() {
        this.user.setPassword("12345678");
        String expected = "12345678";
        String actual = this.user.getPassword();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void user_videosFieldGetterAndSetter_returnCorrect() {
        Set<Video> expected = new HashSet<>(){{
            add(new Video(){{
                setId("123");
            }});
            add(new Video(){{
                setId("321");
            }});
        }};
        this.user.setVideos(expected);
        Set<Video> actual = this.user.getVideos();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void user_isAccountNonExpiredFieldGetterAndSetter_returnCorrect() {
        this.user.setAccountNonExpired(true);
        boolean actual = this.user.isAccountNonExpired();

        Assert.assertTrue(actual);
    }

    @Test
    public void user_isAccountNonLockedFieldGetterAndSetter_returnCorrect() {
        this.user.setAccountNonLocked(true);
        boolean actual = this.user.isAccountNonLocked();

        Assert.assertTrue(actual);
    }

    @Test
    public void user_isCredentialsNonExpiredFieldGetterAndSetter_returnCorrect() {
        this.user.setCredentialsNonExpired(true);
        boolean actual = this.user.isCredentialsNonExpired();

        Assert.assertTrue(actual);
    }

    @Test
    public void user_isEnabledFieldGetterAndSetter_returnCorrect() {
        this.user.setEnabled(true);
        boolean actual = this.user.isEnabled();

        Assert.assertTrue(actual);
    }

    @Test
    public void user_authoritiesFieldGetterAndSetter_returnCorrect() {
        Set<Role> expected = new HashSet<>(){{
            add(new Role(){{
                setAuthority("USER");
            }});
            add(new Role(){{
                setAuthority("MODERATOR");
            }});
        }};
        this.user.setAuthorities(expected);
        Set<Role> actual = (Set<Role>) this.user.getAuthorities();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void user_addRole_shouldAddRole() {
        this.user.setAuthorities(new LinkedHashSet<>(){{
            add(new Role(){{
                setAuthority("USER");
            }});
            add(new Role(){{
                setAuthority("MODERATOR");
            }});
        }});
        this.user.addRole(new Role(){{setAuthority("ADMIN");}});
        List<Role> expected = new ArrayList<>(){{
            add(new Role(){{
                setAuthority("USER");
            }});
            add(new Role(){{
                setAuthority("MODERATOR");
            }});
            add(new Role(){{
                setAuthority("ADMIN");
            }});
        }};
        Set<Role> actual = (Set<Role>) this.user.getAuthorities();
        int i = 0;

        for (Role act : actual) {
            Assert.assertEquals(expected.get(i++).getAuthority(), act.getAuthority());
        }
    }

    @Test
    public void user_removeRole_shouldRemoveRole() {
        Role toRemove = new Role(){{
            setId("3");
            setAuthority("ADMIN");
        }};
        this.user.setAuthorities(new LinkedHashSet<>(){{
            add(new Role(){{
                setId("1");
                setAuthority("USER");
            }});
            add(new Role(){{
                setId("2");
                setAuthority("MODERATOR");
            }});
            add(toRemove);
        }});
        this.user.removeRole(toRemove);
        List<Role> expected = new ArrayList<>(){{
            add(new Role(){{
                setId("1");
                setAuthority("USER");
            }});
            add(new Role(){{
                setId("2");
                setAuthority("MODERATOR");
            }});
        }};
        Set<Role> actual = (Set<Role>) this.user.getAuthorities();
        int i = 0;

        for (Role act : actual) {
            Assert.assertEquals(expected.get(i).getId(), act.getId());
            Assert.assertEquals(expected.get(i++).getAuthority(), act.getAuthority());
        }
    }

    @Test
    public void user_deletedOnFieldGetterAndSetter_returnCorrect() {
        LocalDate expected = LocalDate.now();
        this.user.setDeletedOn(expected);
        LocalDate actual = this.user.getDeletedOn();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void user_likedVideosFieldGetterAndSetter_returnCorrect() {
        Map<String, Video> expected = new HashMap<>(){{
            put("123", new Video(){{
                setId("123");
            }});
            put("234", new Video(){{
                setId("234");
            }});
        }};
        this.user.setLikedVideos(expected);
        Map<String, Video> actual = this.user.getLikedVideos();

        Assert.assertEquals(expected, actual);
    }
}