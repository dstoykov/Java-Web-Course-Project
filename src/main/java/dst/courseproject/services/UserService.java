package dst.courseproject.services;

import dst.courseproject.exceptions.PasswordsMismatchException;
import dst.courseproject.exceptions.UserAlreadyExistsException;
import dst.courseproject.exceptions.UserIsModeratorAlreadyException;
import dst.courseproject.exceptions.UserIsNotModeratorException;
import dst.courseproject.models.binding.UserRegisterBindingModel;
import dst.courseproject.models.binding.UserEditBindingModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.view.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.validation.Valid;
import java.util.List;

public interface UserService extends UserDetailsService {

    UserServiceModel getUserServiceModelByEmail(String email);

    UserServiceModel register(UserRegisterBindingModel bindingModel) throws PasswordsMismatchException, UserAlreadyExistsException;

    UserViewModel getUserViewModelByEmail(String email);

    UserServiceModel getUserServiceModelById(String id);

    UserViewModel getUserViewModelById(String id);

    UserServiceModel editUserData(@Valid UserEditBindingModel userEditBindingModel, String id) throws PasswordsMismatchException;

    UserServiceModel editUserDataByEmail(@Valid UserEditBindingModel userEditBindingModel, String name) throws PasswordsMismatchException;

    UserServiceModel deleteUser(String id);

    List<UserViewModel> getListWithViewModels(String email);

    UserServiceModel restoreUser(String id);

    UserServiceModel makeModerator(String id) throws UserIsModeratorAlreadyException;

    UserServiceModel revokeModeratorAuthority(String id) throws UserIsNotModeratorException;

    Long getTotalUsersCount();

    Long getTotalActiveUsersCount();

    boolean isUserModerator(String id);

    UserServiceModel getDeletedUserServiceModelById(String id);
}
