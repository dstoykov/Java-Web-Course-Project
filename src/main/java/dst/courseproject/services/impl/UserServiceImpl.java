package dst.courseproject.services.impl;

import dst.courseproject.entities.User;
import dst.courseproject.models.binding.RegisterUserBindingModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.repositories.UserRepository;
import dst.courseproject.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void createUser(RegisterUserBindingModel userBindingModel) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = this.modelMapper.map(userBindingModel, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userBindingModel.getPassword()));

        this.userRepository.save(user);
    }

    @Override
    public UserServiceModel getUserByEmail(String email) {
        UserServiceModel userServiceModel = this.modelMapper.map(this.userRepository.findByEmail(email), UserServiceModel.class);
        return userServiceModel;
    }
}
