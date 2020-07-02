package by.itstep.onlineauctionsystem.service;

import by.itstep.onlineauctionsystem.entity.user.User;
import by.itstep.onlineauctionsystem.dto.UserDto;
import by.itstep.onlineauctionsystem.exeption.UserAlreadyExistException;
import by.itstep.onlineauctionsystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User saveNewUser(UserDto userDto) throws UserAlreadyExistException {
        if(emailExist(userDto.getEmail())){
            throw new UserAlreadyExistException(
                    "There is an account with that email address: " +  userDto.getEmail());
        }
        User user = new User(userDto);
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return user;
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
