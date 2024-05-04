package com.media.groove.repository;

import com.media.groove.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    @Query("SELECT U.username FROM User U WHERE U.username LIKE :username")
    Optional<String> findUserByUsernameLike(String username);
}
