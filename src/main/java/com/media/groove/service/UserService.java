package com.media.groove.service;

import com.media.groove.entity.User;
import com.media.groove.repository.UserRepository;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(User newUser) {
        newUser.setPassword(passwordEncoder.encode("hola123"));
        this.userRepository.save(newUser);
    }
}
