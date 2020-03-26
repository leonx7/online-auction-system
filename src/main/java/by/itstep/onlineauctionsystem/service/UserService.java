package by.itstep.onlineauctionsystem.service;

import by.itstep.onlineauctionsystem.model.User;
import by.itstep.onlineauctionsystem.model.UserDto;
import by.itstep.onlineauctionsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(UserDto userDto) {
        User user = new User(userDto);
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return user;
    }
}
