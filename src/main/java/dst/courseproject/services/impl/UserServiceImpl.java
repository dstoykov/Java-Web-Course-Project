package dst.courseproject.services.impl;

import dst.courseproject.entities.User;
import dst.courseproject.models.binding.RegisterUserBindingModel;
import dst.courseproject.models.binding.UserEditBindingModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.view.UserViewModel;
import dst.courseproject.repositories.UserRepository;
import dst.courseproject.services.RoleService;
import dst.courseproject.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper mapper, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mapper = mapper;
        this.encoder = encoder;
    }

    @Override
    public UserServiceModel getUserByEmail(String email) {
        UserServiceModel userServiceModel = this.mapper.map(this.userRepository.findByEmail(email), UserServiceModel.class);
        return userServiceModel;
    }

    @Override
    public void register(RegisterUserBindingModel bindingModel) {
        User user = this.mapper.map(bindingModel, User.class);
        user.setPassword(this.encoder.encode(bindingModel.getPassword()));
        user.addRole(this.roleService.getRoleByAuthority("USER"));

        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public UserViewModel getUserModelByEmail(String email) {
        User user = this.userRepository.findByEmail(email);
        UserViewModel userViewModel = this.mapper.map(user, UserViewModel.class);
        return userViewModel;
    }

    @Override
    public UserServiceModel getUserModelById(String id) {
        User user = this.userRepository.findByIdEquals(id);
        UserServiceModel userServiceModel = this.mapper.map(user, UserServiceModel.class);
        return userServiceModel;
    }

    @Override
    public UserEditBindingModel getUserById(String id) {
        User user = this.userRepository.findByIdEquals(id);
        UserEditBindingModel userEditBindingModel = this.mapper.map(user, UserEditBindingModel.class);
        return userEditBindingModel;
    }

    @Override
    public void editUserData(@Valid UserEditBindingModel userEditBindingModel, String id) {
        User user = this.userRepository.findByIdEquals(id);

        user.setFirstName(userEditBindingModel.getFirstName());
        user.setLastName(userEditBindingModel.getLastName());
        user.setPassword(this.encoder.encode(userEditBindingModel.getPassword()));

        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUser(String id) {
        User user = this.userRepository.findByIdEquals(id);
        user.setDeletedOn(LocalDate.now());
    }
}
