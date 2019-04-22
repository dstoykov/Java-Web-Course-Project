package dst.courseproject.services;

import dst.courseproject.entities.Role;
import dst.courseproject.entities.User;
import dst.courseproject.exceptions.PasswordsMismatchException;
import dst.courseproject.exceptions.UserAlreadyExistsException;
import dst.courseproject.exceptions.UserIsModeratorAlreadyException;
import dst.courseproject.models.binding.UserEditBindingModel;
import dst.courseproject.models.binding.UserRegisterBindingModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.view.UserViewModel;
import dst.courseproject.repositories.UserRepository;
import dst.courseproject.services.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTests {
    private UserRepository userRepository;
    private RoleService roleService;
    private UserService service;

    @Before
    public void init() {
        this.userRepository = mock(UserRepository.class);
        this.roleService = mock(RoleService.class);
        this.service = new UserServiceImpl(this.userRepository, this.roleService, new ModelMapper(), new BCryptPasswordEncoder());
        when(this.userRepository.getOne("1")).thenReturn(new User(){{
            setId("1");
            setEmail("pesho@pesho.com");
            setFirstName("Pesho");
            setLastName("Peshev");
            setPassword("qwerty12");
            setDeletedOn(null);
            setAuthorities(new LinkedHashSet<>(){{
                add(new Role(){{
                    setAuthority("USER");
                }});
            }});
        }});
        when(this.userRepository.findByIdEquals("1")).thenReturn(new User(){{
            setId("1");
            setEmail("pesho@pesho.com");
            setFirstName("Pesho");
            setLastName("Peshev");
        }});
        when(this.userRepository.findByEmailAndDeletedOnIsNull("pesho@pesho.com")).thenReturn(new User() {{
            setId("1");
            setEmail("pesho@pesho.com");
            setFirstName("Pesho");
            setLastName("Peshev");
            setPassword("qwerty12");
            setDeletedOn(null);
        }});
        when(this.userRepository.findByIdEqualsAndDeletedOnIsNull("1")).thenReturn(new User() {{
            setId("1");
            setEmail("pesho@pesho.com");
            setFirstName("Pesho");
            setLastName("Peshev");
            setPassword("qwerty12");
            setDeletedOn(null);
        }});
        when(this.roleService.getRoleByAuthority("MODERATOR")).thenReturn(new Role(){{setAuthority("MODERATOR");}});
    }

    @Test
    public void userService_getTotalUsersCount_returnCorrect() {
        when(this.userRepository.countUsersByEmailIsNotNull()).thenReturn(5L);
        long expected = 5L;
        long actual = this.service.getTotalUsersCount();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void userService_getTotalActiveUsersCount_returnCorrect() {
        when(this.userRepository.countUsersByDeletedOnIsNull()).thenReturn(4L);
        long expected = 4L;
        long actual = this.service.getTotalActiveUsersCount();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void userService_loadUserByUserName_returnCorrectUserDetails() {
        UserDetails actual = this.service.loadUserByUsername("pesho@pesho.com");
        Assert.assertEquals("pesho@pesho.com", actual.getUsername());
    }

    @Test
    public void userService_getUserViewModelById_returnViewModel() {
        when(this.userRepository.findByIdEquals("1")).thenReturn(new User() {{
            setId("1");
            setEmail("pesho@pesho.com");
            setFirstName("Pesho");
            setLastName("Peshev");
        }});
        UserViewModel actual = this.service.getUserViewModelById("1");

        Assert.assertEquals("1", actual.getId());
        Assert.assertEquals("pesho@pesho.com", actual.getEmail());
        Assert.assertEquals("Pesho", actual.getFirstName());
        Assert.assertEquals("Peshev", actual.getLastName());
    }

    @Test
    public void userService_getUserViewModelByEmail_returnViewModel() {
        when(this.userRepository.findByEmailAndDeletedOnIsNull("pesho@pesho.com")).thenReturn(new User() {{
            setId("1");
            setEmail("pesho@pesho.com");
            setFirstName("Pesho");
            setLastName("Peshev");
        }});
        UserViewModel actual = this.service.getUserViewModelByEmail("pesho@pesho.com");

        Assert.assertEquals("1", actual.getId());
        Assert.assertEquals("pesho@pesho.com", actual.getEmail());
        Assert.assertEquals("Pesho", actual.getFirstName());
        Assert.assertEquals("Peshev", actual.getLastName());
    }

    @Test
    public void userService_getUserServiceModelById_returnViewModel() {
        UserServiceModel actual = this.service.getUserServiceModelById("1");

        Assert.assertEquals("1", actual.getId());
        Assert.assertEquals("pesho@pesho.com", actual.getEmail());
        Assert.assertEquals("Pesho", actual.getFirstName());
        Assert.assertEquals("Peshev", actual.getLastName());
    }

    @Test
    public void userService_getUserServiceModelByEmail_returnViewModel() {
        UserServiceModel actual = this.service.getUserServiceModelByEmail("pesho@pesho.com");

        Assert.assertEquals("1", actual.getId());
        Assert.assertEquals("pesho@pesho.com", actual.getEmail());
        Assert.assertEquals("Pesho", actual.getFirstName());
        Assert.assertEquals("Peshev", actual.getLastName());
    }

    @Test
    public void userService_getListWithViewModels_returnCollection() {
        when(this.userRepository.getAllByEmailIsNotOrderByDeletedOn("pesho@pesho.com")).thenReturn(new ArrayList<>(){{
            add(new User(){{
                setId("2");
                setEmail("gosho@gosho.com");
                setFirstName("Gosho");
                setLastName("Goshev");
                setDeletedOn(null);
            }});
            add(new User(){{
                setId("3");
                setEmail("stefan@stefan.com");
                setFirstName("Stefan");
                setLastName("Stefanov");
                setDeletedOn(LocalDate.now());
            }});
        }});
        List<UserViewModel> actual = this.service.getListWithViewModels("pesho@pesho.com");

        Assert.assertEquals("2", actual.get(0).getId());
        Assert.assertEquals("gosho@gosho.com", actual.get(0).getEmail());
        Assert.assertEquals("Gosho", actual.get(0).getFirstName());
        Assert.assertEquals("Goshev", actual.get(0).getLastName());
        Assert.assertNull(actual.get(0).getDeletedOn());
        Assert.assertEquals("3", actual.get(1).getId());
        Assert.assertEquals("stefan@stefan.com", actual.get(1).getEmail());
        Assert.assertEquals("Stefan", actual.get(1).getFirstName());
        Assert.assertEquals("Stefanov", actual.get(1).getLastName());
        Assert.assertNotNull(actual.get(1).getDeletedOn());
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void userService_registerWithUsedEmail_throwException() throws PasswordsMismatchException, UserAlreadyExistsException {
        when(this.userRepository.findByEmailEquals("pesho@pesho.com")).thenReturn(new User(){{
            setEmail("pesho@pesho.com");
        }});
        UserRegisterBindingModel model = new UserRegisterBindingModel(){{
            setEmail("pesho@pesho.com");
        }};

        this.service.register(model);
    }

    @Test(expected = PasswordsMismatchException.class)
    public void userService_registerWithPasswordsMismatch_throwException() throws PasswordsMismatchException, UserAlreadyExistsException {
        UserRegisterBindingModel model = new UserRegisterBindingModel(){{
            setEmail("pesho@pesho.com");
            setPassword("12345678");
            setConfirmPassword("qwerty12");
        }};
        this.service.register(model);
    }

    @Test
    public void userService_registerWithValidData_returnServiceModel() throws PasswordsMismatchException, UserAlreadyExistsException {
        when(this.roleService.getRoleByAuthority("USER")).thenReturn(new Role(){{setAuthority("USER");}});
        UserRegisterBindingModel model = new UserRegisterBindingModel(){{
            setEmail("pesho@pesho.com");
            setFirstName("Pesho");
            setLastName("Peshev");
            setPassword("12345678");
            setConfirmPassword("12345678");
        }};
        UserServiceModel actual = this.service.register(model);

        Assert.assertEquals("pesho@pesho.com", actual.getEmail());
        Assert.assertEquals("Pesho", actual.getFirstName());
        Assert.assertEquals("Peshev", actual.getLastName());
        for (Role authority : actual.getAuthorities()) {
            Assert.assertEquals("USER", authority.getAuthority());
        }
    }

    @Test(expected = PasswordsMismatchException.class)
    public void userService_editUserDataWithPasswordsMismatch_throwException() throws PasswordsMismatchException {
        UserEditBindingModel model = new UserEditBindingModel(){{
            setPassword("12345678");
            setConfirmPassword("87654321");
        }};
        this.service.editUserData(model, "1");
    }

    @Test
    public void userService_editUserDataWithCorrectData_returnCorrect() throws PasswordsMismatchException {
        UserEditBindingModel model = new UserEditBindingModel(){{
            setFirstName("Pesho");
            setLastName("Peshev");
            setPassword("123456789");
            setConfirmPassword("123456789");
        }};
        UserServiceModel actual = this.service.editUserData(model, "1");

        Assert.assertEquals("Pesho", actual.getFirstName());
        Assert.assertEquals("Peshev", actual.getLastName());
    }

    @Test(expected = PasswordsMismatchException.class)
    public void userService_editUserDataByEmailWithPasswordsMismatch_throwException() throws PasswordsMismatchException {
        UserEditBindingModel model = new UserEditBindingModel(){{
            setPassword("12345678");
            setConfirmPassword("87654321");
        }};
        this.service.editUserDataByEmail(model, "pesho@pesho.com");
    }

    @Test
    public void userService_editUserDataByEmailWithCorrectData_returnCorrect() throws PasswordsMismatchException {
        UserEditBindingModel model = new UserEditBindingModel(){{
            setFirstName("Pesho");
            setLastName("Peshev");
            setPassword("123456789");
            setConfirmPassword("123456789");
        }};
        UserServiceModel actual = this.service.editUserDataByEmail(model, "pesho@pesho.com");

        Assert.assertEquals("Pesho", actual.getFirstName());
        Assert.assertEquals("Peshev", actual.getLastName());
    }

    @Test
    public void userService_deleteUser_returnServiceModelWithDeletedOnField() {
        UserServiceModel actual = this.service.deleteUser("1");
        Assert.assertNotNull(actual.getDeletedOn());
    }

    @Test
    public void userService_restoreUser_returnServiceModelWithDeletedOnNull() {
        when(this.userRepository.findByIdAndDeletedOnNotNull("1")).thenReturn(new User(){{
            setId("1");
            setEmail("pesho@pesho.com");
            setDeletedOn(LocalDate.now());
        }});
        UserServiceModel actual = this.service.restoreUser("1");
        Assert.assertNull(actual.getDeletedOn());
    }

    @Test
    public void userService_makeModeratorWithCorrectData_returnServiceModel() throws UserIsModeratorAlreadyException {
        UserServiceModel actual = this.service.makeModerator("1");
        List<Role> actualRoles = new ArrayList<>(actual.getAuthorities());
        Assert.assertEquals("MODERATOR", actualRoles.get(1).getAuthority());
    }

    @Test
    public void userService_getDeletedUserServiceModelById_returnServiceModel() throws UserIsModeratorAlreadyException {
        when(this.userRepository.findByIdEqualsAndDeletedOnNotNull("1")).thenReturn(new User(){{
            setFirstName("Pesho");
            setLastName("Peshev");
            setDeletedOn(LocalDate.now());
        }});
        UserServiceModel actual = this.service.getDeletedUserServiceModelById("1");

        Assert.assertEquals("Pesho", actual.getFirstName());
        Assert.assertEquals("Peshev", actual.getLastName());
        Assert.assertNotNull(actual.getDeletedOn());
    }
}
