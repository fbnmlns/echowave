package com.media.groove.repository;

import com.media.groove.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean getUserByUsernameAndPassword(String username, String password);
}
