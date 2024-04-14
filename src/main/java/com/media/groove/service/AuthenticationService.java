package com.media.groove.service;

import com.media.groove.entity.User;
import com.media.groove.repository.UserRepository;
import com.media.groove.session.UserSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSession userSession;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserSession userSession) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
    }

    public boolean authenticate(String username, String password) {
        Optional<User> userToAuthenticate = this.userRepository
                .findUserByUsername(username);

        if (userToAuthenticate.isPresent()) {
            if (this.passwordIsCorrect(password, userToAuthenticate.get().getPassword())) {
                User authenticatedUser = userToAuthenticate.get();
                this.userSession.setAuthenticatedUser(authenticatedUser);

                return true;
            }
        }

        return false;
    }

    private boolean passwordIsCorrect(String rawPassword, String encodedPassword) {
        return this.passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
