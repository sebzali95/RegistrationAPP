package com.register.service;
import com.register.entity.Role;
import com.register.entity.User;
import com.register.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        User newUser = new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(),
                List.of(new Role("USER")));
        return userRepository.save(newUser);
    }

    public Optional<User> login(String email, String password){
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        return user;
    }
}
