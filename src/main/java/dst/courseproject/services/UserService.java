package dst.courseproject.services;

import dst.courseproject.exception.PasswordsMismatchException;
import dst.courseproject.models.binding.RegisterUserBindingModel;
import dst.courseproject.models.binding.UserEditBindingModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.view.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.validation.Valid;
import java.util.List;

public interface UserService extends UserDetailsService {

    UserServiceModel getUserByEmail(String email);

    void register(RegisterUserBindingModel bindingModel) throws PasswordsMismatchException;

    UserViewModel getUserModelByEmail(String email);

    UserServiceModel getUserServiceModelById(String id);

    UserViewModel getUserViewModelById(String id);

    UserEditBindingModel getUserById(String id);

    void editUserData(@Valid UserEditBindingModel userEditBindingModel, String id) throws PasswordsMismatchException;

    void deleteUser(String id);

    List<UserViewModel> getListWithViewModels(String email);
}
