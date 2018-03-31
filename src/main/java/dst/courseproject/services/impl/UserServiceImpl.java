package dst.courseproject.services.impl;

import dst.courseproject.entities.User;
import dst.courseproject.models.binding.RegisterUserBindingModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.repositories.UserRepository;
import dst.courseproject.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper mapper;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
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
}
