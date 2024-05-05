package com.media.groove.controller;

import com.media.groove.StageInitializer;
import com.media.groove.entity.Media;
import com.media.groove.service.MediaService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import java.io.File;

@Controller
public class AddVideoController {
    private final StageInitializer stageInitializer;

    private final MediaService mediaService;

    private String videoPath;

    private String thumbnailPath;

    @FXML
    public TextField title;

    @FXML
    public Label thumbnailFileName;

    @FXML
    public Label videoFileName;

    @FXML
    public Label lblTitle;

    @FXML
    public Label lblVideoPathError;

    @FXML
    public Label lblThumbnailPathError;

    @FXML
    public Label lblAddingVideoError;

    @Value("classpath:/ui/home.fxml")
    private Resource homeScreenSource;

    public AddVideoController(StageInitializer stageInitializer, MediaService mediaService) {
        this.stageInitializer = stageInitializer;
        this.mediaService = mediaService;
    }

    public void addVideo() {
        Media newMedia = new Media();

        if (this.mediaInformationIsValid()) {
            newMedia.setTitle(this.title.getText());
            newMedia.setFile(this.getVideoPath());
            newMedia.setThumbnail(this.getThumbnailPath());

            this.mediaService.createMedia(newMedia);

            if (newMedia.getId() != null) {
                this.stageInitializer.switchScene(this.homeScreenSource);

            } else {
                this.lblAddingVideoError.setVisible(true);
            }
        }
    }

    public void chooseVideoFile() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video File", "*.mp4"));

        File file = fileChooser.showOpenDialog(this.stageInitializer.getScene().getWindow());

        if (file != null) {
            this.setVideoPath(file.getAbsolutePath());
            this.videoFileName.setText(file.getName());
        }
    }

    public void chooseImageFile() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*.jpg", "*.png"));

        File file = fileChooser.showOpenDialog(this.stageInitializer.getScene().getWindow());

        if (file != null) {
            this.setThumbnailPath(file.getAbsolutePath());
            this.thumbnailFileName.setText(file.getName());
        }
    }

    public void goBack() {
        this.stageInitializer.switchScene(this.homeScreenSource);
    }

    private boolean mediaInformationIsValid() {
        boolean isValid = true;

        if (this.title.getText().isEmpty()) {
            this.title.getStyleClass().remove("input");

            this.title.getStyleClass().add("error");
            this.lblTitle.getStyleClass().add("label-error");

            isValid = false;
        }

        if (this.videoFileName.getText().isEmpty()) {
            this.videoFileName.getStyleClass().remove("input");

            this.lblVideoPathError.setVisible(true);

            isValid = false;
        }

        if (this.thumbnailFileName.getText().isEmpty()) {
            this.thumbnailFileName.getStyleClass().remove("input");

            this.lblThumbnailPathError.setVisible(true);

            isValid = false;
        }

        return isValid;
    }

    private String getVideoPath() {
        return videoPath;
    }

    private void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    private String getThumbnailPath() {
        return thumbnailPath;
    }

    private void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
}
