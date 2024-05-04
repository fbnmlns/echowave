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

    @FXML
    public TextField title;

    @FXML
    public TextField thumbnailPath;

    @FXML
    public TextField videoOrAudioPath;

    @FXML
    public Label lblTitle;

    @FXML
    public Label lblVideoOrAudioPath;

    @FXML
    public Label lblThumbnailPath;

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
            newMedia.setFile(this.videoOrAudioPath.getText());
            newMedia.setThumbnail(this.thumbnailPath.getText());

            this.mediaService.createMedia(newMedia);

            if (newMedia.getId() != null) {
                this.stageInitializer.switchScene(this.homeScreenSource);

            } else {
                this.lblAddingVideoError.setVisible(true);
            }
        }
    }

    public void chooseImageFile() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*.jpg", "*.png"));

        File file = fileChooser.showOpenDialog(this.stageInitializer.getScene().getWindow());

        if (file != null) {
            this.thumbnailPath.setText(file.getAbsolutePath());
        }
    }

    public void chooseVideoOrAudioFile() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Media File", "*.mp4", "*.mp3"));

        File file = fileChooser.showOpenDialog(this.stageInitializer.getScene().getWindow());

        if (file != null) {
            this.videoOrAudioPath.setText(file.getAbsolutePath());
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

        if (this.videoOrAudioPath.getText().isEmpty()) {
            this.videoOrAudioPath.getStyleClass().remove("input");

            this.videoOrAudioPath.getStyleClass().add("error");
            this.lblVideoOrAudioPath.getStyleClass().add("label-error");

            isValid = false;
        }

        if (this.thumbnailPath.getText().isEmpty()) {
            this.thumbnailPath.getStyleClass().remove("input");

            this.thumbnailPath.getStyleClass().add("error");
            this.lblThumbnailPath.getStyleClass().add("label-error");

            isValid = false;
        }

        return isValid;
    }
}
