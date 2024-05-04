package com.media.groove.session;

import com.media.groove.entity.Media;
import org.springframework.stereotype.Component;

@Component
public class VideoSession {
    private Media currentMedia;

    public Media getCurrentVideo() {
        return currentMedia;
    }

    public void setCurrentVideo(Media currentMedia) {
        this.currentMedia = currentMedia;
    }
}
