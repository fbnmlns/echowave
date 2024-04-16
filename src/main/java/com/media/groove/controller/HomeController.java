package com.media.groove.controller;

import com.media.groove.StageInitializer;
import com.media.groove.entity.Media;
import com.media.groove.session.VideoSession;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.ArrayList;

@Controller
public class HomeController {
    public final int FLOW_PANE_HGAP_VALUE = 30;

    public final int FLOW_PANE_VGAP_VALUE = 30;
    
    public final int THUMBNAIL_FIT_HEIGHT = 146;

    public final int THUMBNAIL_FIT_WIDTH = 230;

    private final StageInitializer stageInitializer;

    private final VideoSession videoSession;

    @Value("classpath:/ui/media-player.fxml")
    private Resource mediaPlayerScreenResource;

    @FXML
    public FlowPane allMediaContainer;

    public HomeController(StageInitializer stageInitializer, VideoSession videoSession) {
        this.stageInitializer = stageInitializer;
        this.videoSession = videoSession;
    }

    public void initialize() {
        this.loadMedia();
    }

    private void loadMedia() {
        ArrayList<Media> list = new ArrayList<>();

        Media media = new Media(); //Remove once there's a user with videos
        media.setFile("src/main/resources/ui/assets/house.mp4");

        list.add(media);

        for (Media currMedia : list) {
            ImageView thumbnail = getMediaThumbnail(currMedia.getThumbnail());
            thumbnail.setFitWidth(this.THUMBNAIL_FIT_WIDTH);
            thumbnail.setFitHeight(this.THUMBNAIL_FIT_HEIGHT);
            thumbnail.getStyleClass().add("media-card");

            thumbnail.setOnMouseClicked(event -> {
                this.videoSession.setCurrentVideo(currMedia);
                this.startPlayingMedia();
            });

            this.allMediaContainer.setHgap(this.FLOW_PANE_HGAP_VALUE);
            this.allMediaContainer.setVgap(this.FLOW_PANE_VGAP_VALUE);

            this.allMediaContainer.getChildren().add(thumbnail);
        }
    }

    private ImageView getMediaThumbnail(String imagePath) {
        Image image = new Image(new File(imagePath).toURI().toString());

        return new ImageView(image);
    }
    
    private void startPlayingMedia() {
        this.stageInitializer.switchScene(this.mediaPlayerScreenResource);
    }
}
