package dst.courseproject.entities;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class RoleTests {
    private Role role = new Role();

    @Test
    public void role_idFieldGetterAndSetter_returnCorrect() {
        this.role.setId("123");
        String expected = "123";
        String actual = this.role.getId();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void role_authorityFieldGetterAndSetter_returnCorrect() {
        this.role.setAuthority("USER");
        String expected = "USER";
        String actual = this.role.getAuthority();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void role_usersFieldGetterAndSetter_returnCorrect() {
        Set<User> expected = new HashSet<>(){{
            new User(){{
                setId("123");
                setFirstName("Gosho");
                setLastName("Goshev");
            }};
            new User(){{
                setId("321");
                setFirstName("Stamat");
                setLastName("Stamatov");
            }};

        }};
        this.role.setUsers(expected);
        Set<User> actual = this.role.getUsers();

        Assert.assertEquals(expected, actual);
    }
}