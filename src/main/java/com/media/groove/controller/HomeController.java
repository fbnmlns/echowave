package com.media.groove.controller;

import com.media.groove.StageInitializer;
import com.media.groove.entity.Post;
import com.media.groove.service.PostService;
import com.media.groove.session.UserSession;
import com.media.groove.session.VideoSession;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import java.io.File;

@Controller
public class HomeController {
    public final int FLOW_PANE_HGAP_VALUE = 30;

    public final int FLOW_PANE_VGAP_VALUE = 30;

    public final int THUMBNAIL_FIT_HEIGHT = 146;

    public final int THUMBNAIL_FIT_WIDTH = 230;

    private final StageInitializer stageInitializer;

    private final PostService postService;

    private final VideoSession videoSession;

    private final UserSession userSession;

    @FXML
    public FlowPane allMediaContainer;

    @FXML
    public Circle profilePicture;

    @FXML
    public MenuButton sessionMenu;

    @Value("classpath:/ui/media-player.fxml")
    private Resource mediaPlayerScreenResource;

    @Value("classpath:/ui/add-post.fxml")
    private Resource addNewVideoScreenResource;

    @Value("classpath:/ui/welcome-page.fxml")
    private Resource welcomeScreenSource;

    public HomeController(
            StageInitializer stageInitializer,
            PostService postService,
            VideoSession videoSession,
            UserSession userSession) {
        this.stageInitializer = stageInitializer;
        this.postService = postService;
        this.videoSession = videoSession;
        this.userSession = userSession;
    }

    public void initialize() {
        this.allMediaContainer.setHgap(this.FLOW_PANE_HGAP_VALUE);
        this.allMediaContainer.setVgap(this.FLOW_PANE_VGAP_VALUE);
        this.setUserProfilePicture();
        this.loadMedia();
    }

    public void addPost() {
        this.stageInitializer.switchScene(this.addNewVideoScreenResource);
    }

    public void signOut() {
        this.sessionMenu.hide();
        this.userSession.clearSession();
        this.stageInitializer.switchScene(this.welcomeScreenSource);
    }

    private void loadMedia() {
        for (Post currMedia : this.postService.getAllMediaByCurrentUserId()) {
            ImageView thumbnail = getVideoThumbnail(currMedia.getThumbnail());
            thumbnail.setFitWidth(this.THUMBNAIL_FIT_WIDTH);
            thumbnail.setFitHeight(this.THUMBNAIL_FIT_HEIGHT);
            thumbnail.getStyleClass().add("media-card");

            thumbnail.setOnMouseClicked(event -> {
                this.videoSession.setCurrentPost(currMedia);
                this.startPlayingMedia();
            });

            VBox mediaContainer = new VBox();

            Label mediaTitle = new Label(currMedia.getTitle());
            mediaTitle.getStyleClass().add("media-title");

            mediaContainer.getChildren().add(thumbnail);
            mediaContainer.getChildren().add(mediaTitle);

            this.allMediaContainer.getChildren().add(mediaContainer);
        }
    }

    private void setUserProfilePicture() {
        this.profilePicture.setFill(this.getProfilePicture(this.userSession.getAuthenticatedUser().getProfilePicture()));
    }

    private ImageView getVideoThumbnail(String imagePath) {
        Image image = new Image(new File(imagePath).toURI().toString());

        return new ImageView(image);
    }

    private ImagePattern getProfilePicture(String imagePath) {
        Image image = new Image(new File(imagePath).toURI().toString());

        return new ImagePattern(image);
    }

    private void startPlayingMedia() {
        this.stageInitializer.switchScene(this.mediaPlayerScreenResource);
    }
}
