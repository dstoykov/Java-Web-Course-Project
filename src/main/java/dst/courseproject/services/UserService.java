package dst.courseproject.services;

import dst.courseproject.models.binding.RegisterUserBindingModel;
import dst.courseproject.models.binding.UserEditBindingModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.view.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.validation.Valid;

public interface UserService extends UserDetailsService {

    UserServiceModel getUserByEmail(String email);

    void register(RegisterUserBindingModel bindingModel);

    UserViewModel getUserModelByEmail(String email);

    UserServiceModel getUserModelById(String id);

    UserEditBindingModel getUserById(String id);

    void editUserData(@Valid UserEditBindingModel userEditBindingModel, String id);

    void deleteUser(String id);
}
