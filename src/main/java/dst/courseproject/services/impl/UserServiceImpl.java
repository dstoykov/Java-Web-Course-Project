package dst.courseproject.services.impl;

import dst.courseproject.entities.Role;
import dst.courseproject.entities.User;
import dst.courseproject.exceptions.PasswordsMismatchException;
import dst.courseproject.exceptions.UserAlreadyExistsException;
import dst.courseproject.exceptions.UserIsModeratorAlreadyException;
import dst.courseproject.exceptions.UserIsNotModeratorException;
import dst.courseproject.models.binding.UserRegisterBindingModel;
import dst.courseproject.models.binding.UserEditBindingModel;
import dst.courseproject.models.service.UserRestoreServiceModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.view.UserViewModel;
import dst.courseproject.repositories.UserRepository;
import dst.courseproject.services.RoleService;
import dst.courseproject.services.UserService;
import dst.courseproject.util.UserUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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

    private boolean comparePasswords(String password, String confirmPassword) throws PasswordsMismatchException {
        if (!password.equals(confirmPassword)) {
            throw new PasswordsMismatchException("Passwords mismatch!");
        }

        return true;
    }

    private void finishEditing(@Valid UserEditBindingModel userEditBindingModel, User user) throws PasswordsMismatchException {
        if (comparePasswords(userEditBindingModel.getPassword(), userEditBindingModel.getConfirmPassword())) {
            user.setPassword(this.encoder.encode(userEditBindingModel.getPassword()));
        }
        user.setFirstName(userEditBindingModel.getFirstName());
        user.setLastName(userEditBindingModel.getLastName());

        this.userRepository.save(user);
    }

    @Override
    public Long getTotalUsersCount() {
        return this.userRepository.countUsersByEmailIsNotNull();
    }

    @Override
    public Long getTotalActiveUsersCount() {
        return this.userRepository.countUsersByDeletedOnIsNull();
    }

    @Override
    public User getUserByEmail(String email) {
        User user = this.userRepository.findByEmailAndDeletedOnIsNull(email);
        return user;
    }

    @Override
    public UserServiceModel getUserServiceModelByEmail(String email) {
        UserServiceModel userServiceModel = this.mapper.map(this.userRepository.findByEmailAndDeletedOnIsNull(email), UserServiceModel.class);
        return userServiceModel;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmailAndDeletedOnIsNull(email);
    }

    @Override
    public UserViewModel getUserViewModelByEmail(String email) {
        User user = this.userRepository.findByEmailAndDeletedOnIsNull(email);
        UserViewModel userViewModel = this.mapper.map(user, UserViewModel.class);
        return userViewModel;
    }

    @Override
    public UserServiceModel getUserServiceModelById(String id) {
        User user = this.userRepository.findByIdEqualsAndDeletedOnIsNull(id);
        UserServiceModel userServiceModel = this.mapper.map(user, UserServiceModel.class);
        return userServiceModel;
    }

    @Override
    public UserRestoreServiceModel getDeletedUserServiceModelById(String id) {
        User user = this.userRepository.findByIdEqualsAndDeletedOnIsNotNull(id);
        UserRestoreServiceModel userRestoreServiceModel = this.mapper.map(user, UserRestoreServiceModel.class);
        return userRestoreServiceModel;
    }

    @Override
    public UserViewModel getUserViewModelById(String id) {
        User user = this.userRepository.findByIdEquals(id);
        UserViewModel userViewModel = this.mapper.map(user, UserViewModel.class);
        return userViewModel;
    }

    @Override
    public UserEditBindingModel getUserEditBindingModelById(String id) {
        User user = this.userRepository.findByIdEqualsAndDeletedOnIsNull(id);
        UserEditBindingModel userEditBindingModel = this.mapper.map(user, UserEditBindingModel.class);
        return userEditBindingModel;
    }

    @Override
    public List<UserViewModel> getListWithViewModels(String email) {
        List<User> users = this.userRepository.getAllByEmailIsNotOrderByDeletedOn(email);
        List<UserViewModel> userViewModels = new ArrayList<>();
        for (User user : users) {
            UserViewModel userViewModel = this.mapper.map(user, UserViewModel.class);
            userViewModels.add(userViewModel);
        }

        return userViewModels;
    }

    @Override
    public void register(UserRegisterBindingModel bindingModel) throws PasswordsMismatchException, UserAlreadyExistsException {
        if (this.userRepository.findByEmailEquals(bindingModel.getEmail()) != null) {
            throw new UserAlreadyExistsException("User with the sam email already exists!");
        }
        User user = this.mapper.map(bindingModel, User.class);
        if (comparePasswords(bindingModel.getPassword(), bindingModel.getConfirmPassword())) {
            user.setPassword(this.encoder.encode(bindingModel.getPassword()));
        }

        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        Role role = this.roleService.getRoleByAuthority("USER");
        user.addRole(role);
        this.userRepository.save(user);
        role.getUsers().add(user);
        this.roleService.save(role);
    }

    @Override
    public void editUserData(@Valid UserEditBindingModel userEditBindingModel, String id) throws PasswordsMismatchException {
        User user = this.userRepository.findByIdEqualsAndDeletedOnIsNull(id);
        finishEditing(userEditBindingModel, user);
    }

    @Override
    public void editUserDataByEmail(@Valid UserEditBindingModel userEditBindingModel, String email) throws PasswordsMismatchException {
        User user = this.userRepository.findByEmailAndDeletedOnIsNull(email);
        finishEditing(userEditBindingModel, user);
    }

    @Override
    public void deleteUser(String id) {
        User user = this.userRepository.findByIdEqualsAndDeletedOnIsNull(id);
        user.setDeletedOn(LocalDate.now());

        this.userRepository.save(user);
    }

    @Override
    public void restoreUser(String id) {
        User user = this.userRepository.findByIdEqualsAndDeletedOnIsNotNull(id);
        user.setDeletedOn(null);

        this.userRepository.save(user);
    }

    @Override
    public void makeModerator(String id) throws UserIsModeratorAlreadyException {
        User user = this.userRepository.getOne(id);
        Role role = this.roleService.getRoleByAuthority("MODERATOR");

        if (user.getAuthorities().contains(role)) {
            throw new UserIsModeratorAlreadyException("User moderator already!");
        }

        user.addRole(role);
        this.userRepository.save(user);
        role.getUsers().add(user);
        this.roleService.save(role);
    }

    @Override
    public void revokeModeratorAuthority(String id) throws UserIsNotModeratorException {
        User user = this.userRepository.getOne(id);
        Role role = this.roleService.getRoleByAuthority("MODERATOR");

        if (!user.getAuthorities().contains(role)) {
            throw new UserIsNotModeratorException("User is not moderator!");
        }

        user.removeRole(role);
        this.userRepository.save(user);
        role.getUsers().remove(user);
        this.roleService.save(role);
    }

    @Override
    public boolean isUserModerator(String id) {
        User user = this.userRepository.getOne(id);
        return UserUtils.hasRole("MODERATOR", user.getAuthorities());
    }
}