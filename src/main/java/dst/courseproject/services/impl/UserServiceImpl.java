package dst.courseproject.services.impl;

import dst.courseproject.entities.Role;
import dst.courseproject.entities.User;
import dst.courseproject.entities.Video;
import dst.courseproject.exceptions.PasswordsMismatchException;
import dst.courseproject.exceptions.UserAlreadyExistsException;
import dst.courseproject.exceptions.UserIsModeratorAlreadyException;
import dst.courseproject.exceptions.UserIsNotModeratorException;
import dst.courseproject.models.binding.UserRegisterBindingModel;
import dst.courseproject.models.binding.UserEditBindingModel;
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
    private static final String USER_ROLE = "USER";
    private static final String MODERATOR_ROLE = "MODERATOR";
    private static final String PASSWORD_MISMATCH_EXCEPTION_MSG = "Passwords mismatch!";
    private static final String USER_ALREADY_EXIST_EXCEPTION_MSG = "User with the sam email already exists!";
    private static final String USER_MODERATOR_ALREADY_EXCEPTION_MSG = "User moderator already!";
    private static final String USER_NOT_MODERATOR_EXCEPTION_MSG = "User is not moderator!";

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
            throw new PasswordsMismatchException(PASSWORD_MISMATCH_EXCEPTION_MSG);
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

    private void mapUsersToViewModels(List<User> users, List<UserViewModel> userViewModels) {
        for (User user : users) {
            UserViewModel userViewModel = this.mapper.map(user, UserViewModel.class);
            userViewModels.add(userViewModel);
        }
    }

    private void addUserToRole(User user, Role role) {
        role.getUsers().add(user);
        this.roleService.save(role);
    }

    private void checkEmail(UserRegisterBindingModel bindingModel) throws UserAlreadyExistsException {
        if (this.userRepository.findByEmailEquals(bindingModel.getEmail()) != null) {
            throw new UserAlreadyExistsException(USER_ALREADY_EXIST_EXCEPTION_MSG);
        }
    }

    private void checkPasswords(UserRegisterBindingModel bindingModel, User user) throws PasswordsMismatchException {
        if (comparePasswords(bindingModel.getPassword(), bindingModel.getConfirmPassword())) {
            user.setPassword(this.encoder.encode(bindingModel.getPassword()));
        }
    }

    private void checkRoleIsPresent(User user, Role role) throws UserIsModeratorAlreadyException {
        if (user.getAuthorities().contains(role)) {
            throw new UserIsModeratorAlreadyException(USER_MODERATOR_ALREADY_EXCEPTION_MSG);
        }
    }

    private void checkRoleNotPresent(User user, Role role) throws UserIsNotModeratorException {
        if (!user.getAuthorities().contains(role)) {
            throw new UserIsNotModeratorException(USER_NOT_MODERATOR_EXCEPTION_MSG);
        }
    }

    private void removeUserFromRole(User user, Role role) {
        role.getUsers().remove(user);
        this.roleService.save(role);
    }
    //todo check if works tomorrow -->>
    private void deleteUserVideos(User user) {
        for (Video video : user.getVideos()) {
            if (video.getDeletedOn() == null) {
                video.setDeletedOn(LocalDate.now());
            }
        }
    }

    private void restoreUserVideos(User user) {
        for (Video video : user.getVideos()) {
            if (user.getDeletedOn().isEqual(video.getDeletedOn())) {
                video.setDeletedOn(null);
            }
        }
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmailAndDeletedOnIsNull(email);
    }

    @Override
    public UserViewModel getUserViewModelById(String id) {
        User user = this.userRepository.findByIdEquals(id);
        UserViewModel userViewModel = this.mapper.map(user, UserViewModel.class);
        return userViewModel;
    }

    @Override
    public UserViewModel getUserViewModelByEmail(String email) {
        User user = this.userRepository.findByEmailAndDeletedOnIsNull(email);
        UserViewModel userViewModel = this.mapper.map(user, UserViewModel.class);
        return userViewModel;
    }

    @Override
    public UserServiceModel getUserServiceModelById(String id) {
        User user = this.userRepository.findByIdEquals(id);
        UserServiceModel userServiceModel = this.mapper.map(user, UserServiceModel.class);
        return userServiceModel;
    }

    @Override
    public UserServiceModel getUserServiceModelByEmail(String email) {
        UserServiceModel userServiceModel = this.mapper.map(this.userRepository.findByEmailAndDeletedOnIsNull(email), UserServiceModel.class);
        return userServiceModel;
    }

    @Override
    public List<UserViewModel> getListWithViewModels(String exceptEmail) {
        List<User> users = this.userRepository.getAllByEmailIsNotOrderByDeletedOn(exceptEmail);
        List<UserViewModel> userViewModels = new ArrayList<>();
        this.mapUsersToViewModels(users, userViewModels);

        return userViewModels;
    }

    @Override
    public UserServiceModel register(UserRegisterBindingModel bindingModel) throws PasswordsMismatchException, UserAlreadyExistsException {
        this.checkEmail(bindingModel);
        User user = this.mapper.map(bindingModel, User.class);
        this.checkPasswords(bindingModel, user);

        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        Role role = this.roleService.getRoleByAuthority(USER_ROLE);
        user.addRole(role);
        this.userRepository.save(user);
        this.addUserToRole(user, role);

        return this.mapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel editUserData(@Valid UserEditBindingModel userEditBindingModel, String id) throws PasswordsMismatchException {
        User user = this.userRepository.findByIdEquals(id);
        finishEditing(userEditBindingModel, user);

        return this.mapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel editUserDataByEmail(@Valid UserEditBindingModel userEditBindingModel, String email) throws PasswordsMismatchException {
        User user = this.userRepository.findByEmailAndDeletedOnIsNull(email);
        finishEditing(userEditBindingModel, user);

        return this.mapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel deleteUser(String id) {
        User user = this.userRepository.findByIdEqualsAndDeletedOnIsNull(id);
        user.setDeletedOn(LocalDate.now());
        this.deleteUserVideos(user);

        this.userRepository.save(user);

        return this.mapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel restoreUser(String id) {
        User user = this.userRepository.findByIdAndDeletedOnNotNull(id);
        this.restoreUserVideos(user);
        user.setDeletedOn(null);

        this.userRepository.save(user);

        return this.mapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel makeModerator(String id) throws UserIsModeratorAlreadyException {
        User user = this.userRepository.getOne(id);
        Role role = this.roleService.getRoleByAuthority(MODERATOR_ROLE);

        this.checkRoleIsPresent(user, role);

        user.addRole(role);
        this.userRepository.save(user);
        this.addUserToRole(user, role);

        return this.mapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel revokeModeratorAuthority(String id) throws UserIsNotModeratorException {
        User user = this.userRepository.getOne(id);
        Role role = this.roleService.getRoleByAuthority(MODERATOR_ROLE);

        this.checkRoleNotPresent(user, role);

        user.removeRole(role);
        this.userRepository.save(user);
        this.removeUserFromRole(user, role);

        return this.mapper.map(user, UserServiceModel.class);
    }

    @Override
    public boolean isUserModeratorById(String id) {
        User user = this.userRepository.getOne(id);
        return UserUtils.hasRole(MODERATOR_ROLE, user.getAuthorities());
    }

    @Override
    public boolean isUserModeratorByEmail(String email) {
        User user = this.userRepository.findByEmailEquals(email);
        return UserUtils.hasRole(MODERATOR_ROLE, user.getAuthorities());
    }

    @Override
    public UserServiceModel getDeletedUserServiceModelById(String id) {
        User user = this.userRepository.findByIdEqualsAndDeletedOnNotNull(id);
        UserServiceModel userServiceModel = this.mapper.map(user, UserServiceModel.class);
        userServiceModel.setDeletedOn(user.getDeletedOn());

        return userServiceModel;
    }
}