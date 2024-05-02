package com.media.groove.repository;

import com.media.groove.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    @Query("SELECT M FROM Media M WHERE M.owner.id = :userId")
    List<Media> getAllMediaByOwnerId(Long userId);
}
