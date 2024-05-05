package com.media.groove.session;

import com.media.groove.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class VideoSession {
    private Post currentPost;

    public Post getCurrentPost() {
        return currentPost;
    }

    public void setCurrentPost(Post currentPost) {
        this.currentPost = currentPost;
    }

    public void clearCurrentPost() {
        this.currentPost = null;
    }
}
