package com.media.groove.service;

import com.media.groove.entity.Media;
import com.media.groove.repository.MediaRepository;
import com.media.groove.session.UserSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaService {
    private final MediaRepository mediaRepository;

    private final UserSession userSession;

    public MediaService(MediaRepository mediaRepository, UserSession userSession) {
        this.mediaRepository = mediaRepository;
        this.userSession = userSession;
    }

    public void createMedia(Media media) {
        media.setOwner(this.userSession.getAuthenticatedUser());

        this.mediaRepository.save(media);
    }

    public List<Media> getAllMediaByCurrentUserId() {
        return this.mediaRepository.getAllMediaByOwnerId(this.userSession.getAuthenticatedUser().getId());
    }
}
