package dst.courseproject.services;

import dst.courseproject.models.binding.RegisterUserBindingModel;
import dst.courseproject.models.service.UserServiceModel;

public interface UserService {
    void createUser(RegisterUserBindingModel userBindingModel);

    UserServiceModel getUserByEmail(String email);
}
