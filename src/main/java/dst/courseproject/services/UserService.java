package dst.courseproject.services;

import dst.courseproject.entities.User;
import dst.courseproject.exceptions.PasswordsMismatchException;
import dst.courseproject.exceptions.UserAlreadyExistsException;
import dst.courseproject.models.binding.RegisterUserBindingModel;
import dst.courseproject.models.binding.UserEditBindingModel;
import dst.courseproject.models.service.UserRestoreServiceModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.view.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

public interface UserService extends UserDetailsService {

    User getUserByEmail(String email);

    UserServiceModel getUserServiceModelByEmail(String email);

    void register(RegisterUserBindingModel bindingModel) throws PasswordsMismatchException, UserAlreadyExistsException;

    UserViewModel getUserViewModelByEmail(String email);

    UserServiceModel getUserServiceModelById(String id);

    UserRestoreServiceModel getDeletedUserServiceModelById(String id);

    UserViewModel getUserViewModelById(String id);

    UserEditBindingModel getUserEditBindingModelById(String id);

    void editUserData(@Valid UserEditBindingModel userEditBindingModel, String id) throws PasswordsMismatchException;

    void editUserDataByEmail(@Valid UserEditBindingModel userEditBindingModel, String name) throws PasswordsMismatchException;

    void deleteUser(String id);

    List<UserViewModel> getListWithViewModels(String email);

    void restoreUser(String id);

    void makeModerator(String id);

    Long getTotalUsersCount();

    Long getTotalActiveUsersCount();
}
