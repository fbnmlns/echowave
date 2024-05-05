package com.media.groove.service;

import com.media.groove.entity.Post;
import com.media.groove.repository.PostRepository;
import com.media.groove.session.UserSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final UserSession userSession;

    public PostService(PostRepository postRepository, UserSession userSession) {
        this.postRepository = postRepository;
        this.userSession = userSession;
    }

    public void createMedia(Post post) {
        post.setOwner(this.userSession.getAuthenticatedUser());

        this.postRepository.save(post);
    }

    public List<Post> getAllMediaByCurrentUserId() {
        return this.postRepository.getAllMediaByOwnerId(this.userSession.getAuthenticatedUser().getId());
    }
}
