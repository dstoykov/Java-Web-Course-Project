package dst.courseproject.services;

import dst.courseproject.models.binding.RegisterUserBindingModel;
import dst.courseproject.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserServiceModel getUserByEmail(String email);

    void register(RegisterUserBindingModel bindingModel);
}
