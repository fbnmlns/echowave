package com.media.groove.service;

import com.media.groove.entity.User;
import com.media.groove.repository.UserRepository;
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

    public void createUser(User newUser, String rawPassword) {
        newUser.setPassword(passwordEncoder.encode(rawPassword));
        this.userRepository.save(newUser);
    }

    public boolean isUsernameAlreadyTaken(String username) {
        return this.userRepository
                .findUserByUsernameLike(username)
                .isPresent();
    }
}
