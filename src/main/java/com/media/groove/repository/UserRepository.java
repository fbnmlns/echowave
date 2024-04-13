package com.media.groove.repository;

import com.media.groove.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean getUserByUsernameAndPassword(String username, String password);
    Optional<User> findUserByUsernameLike(String username);
}
