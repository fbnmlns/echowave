package com.media.groove.controller;

import com.media.groove.StageInitializer;
import com.media.groove.entity.Video;
import com.media.groove.session.VideoSession;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Rectangle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import java.io.File;

@Controller
public class HomeController {
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
        for (int i = 0; i < 100; i++) {
            ImageView image = getImageView("src/main/resources/ui/assets/test.jpg");
            image.setFitWidth(230);
            image.setFitHeight(146);
            image.getStyleClass().add("media-card");

            image.setOnMouseClicked(event -> {
                Video video = new Video();
                video.setPath("src/main/resources/ui/assets/house.mp4");

                this.videoSession.setCurrentVideo(video);

                this.stageInitializer.switchScene(this.mediaPlayerScreenResource);
            });

            this.allMediaContainer.setHgap(30);
            this.allMediaContainer.setVgap(30);

            this.allMediaContainer.getChildren().add(image);
        }
    }

    private ImageView getImageView(String imagePath) {
        Image image = new Image(new File(imagePath).toURI().toString());

        return new ImageView(image);
    }
}
