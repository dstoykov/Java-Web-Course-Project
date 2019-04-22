package dst.courseproject.services;

import dst.courseproject.entities.Role;
import dst.courseproject.repositories.RoleRepository;
import dst.courseproject.services.impl.RoleServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
public class RoleServiceTests {
    private RoleRepository roleRepository;
    private RoleService roleService;

    @Before
    public void init() {
        this.roleRepository = mock(RoleRepository.class);
        this.roleService = new RoleServiceImpl(this.roleRepository);
    }

    @Test
    public void roleService_save_returnRole() {
        //Arrange
        Role expected = new Role(){{setAuthority("USER");}};

        //Act
        Role actual = this.roleService.save(expected);

        //Assert
        Assert.assertEquals(expected.getAuthority(), actual.getAuthority());
    }

    @Test
    public void roleService_getRoleByAuthority_returnCorrect() {
        //Arrange
        when(this.roleRepository.findByAuthority("USER")).thenReturn(new Role(){{setAuthority("USER");}});
        Role expected = new Role(){{setAuthority("USER");}};

        //Act
        Role actual = this.roleService.getRoleByAuthority("USER");

        //Assert
        Assert.assertEquals(expected.getAuthority(), actual.getAuthority());
    }
}
