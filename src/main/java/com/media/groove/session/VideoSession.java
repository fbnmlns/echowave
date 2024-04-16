package com.media.groove.session;

import com.media.groove.entity.Video;
import org.springframework.stereotype.Component;

@Component
public class VideoSession {
    private Video currentVideo;

    public Video getCurrentVideo() {
        return currentVideo;
    }

    public void setCurrentVideo(Video currentVideo) {
        this.currentVideo = currentVideo;
    }
}
