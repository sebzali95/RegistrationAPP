package com.register.service;


import com.register.entity.User;
import com.register.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailChecking {

    private final UserRepository userRepository;

    public Optional<User> checkEmail(User user) {
        Optional<User> user1 = userRepository.findByEmail(user.getEmail());
        return user1;
    }
}
