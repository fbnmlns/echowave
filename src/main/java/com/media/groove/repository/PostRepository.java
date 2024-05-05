package com.media.groove.repository;

import com.media.groove.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT P FROM Post P WHERE P.owner.id = :userId")
    List<Post> getAllMediaByOwnerId(Long userId);
}
