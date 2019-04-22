package dst.courseproject.util;

import dst.courseproject.entities.Role;
import dst.courseproject.entities.User;
import dst.courseproject.models.service.UserServiceModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

@SpringBootTest
public class UserUtilsTest {
    private static final String MODERATOR = "MODERATOR";
    private static final String ADMIN = "ADMIN";

    private ModelMapper mapper = new ModelMapper();
    private UserServiceModel userServiceModel;


    @Before
    public void init() {
        //Arrange
        Role role = new Role();
        role.setAuthority("MODERATOR");

        User user = new User();
        user.setFirstName("Petar");
        user.setLastName("Petrov");
        user.addRole(role);

        this.userServiceModel = this.mapper.map(user, UserServiceModel.class);
    }


    @Test
    public void methodHasRole_whenModerator_expectTrue() {
        //Act
        Boolean isModerator = UserUtils.hasRole(MODERATOR, this.userServiceModel.getAuthorities());

        //Assert
        assertThat(isModerator, is(true));
    }

    @Test
    public void methodHasRole_whenNotAdmin_expectFalse() {
        //Act
        Boolean isAdmin = UserUtils.hasRole(ADMIN, this.userServiceModel.getAuthorities());

        //Assert
        assertThat(isAdmin, is(false));
    }

    @Test
    public void methodGetFullName_expectFullName() {
        //Arrange
        String expected = "Petar Petrov";

        //Act
        String actual = UserUtils.getUserFullName(this.userServiceModel);

        //Assert
        Assert.assertEquals(expected, actual);
    }
}
