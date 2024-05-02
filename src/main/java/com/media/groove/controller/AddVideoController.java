package com.media.groove.controller;

import com.media.groove.StageInitializer;
import com.media.groove.entity.Media;
import com.media.groove.service.MediaService;
import javafx.fxml.FXML;
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
    public TextField thumbnail;

    @FXML
    public TextField videoOrAudio;

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
            newMedia.setFile(this.videoOrAudio.getText());
            newMedia.setThumbnail(this.thumbnail.getText());

            this.mediaService.createMedia(newMedia);

            if (newMedia.getId() != null) {
                this.stageInitializer.switchScene(this.homeScreenSource);

            } else {
                //this.lblAccountCreationError.setVisible(true);
            }
        }
    }

    public void chooseImageFile() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*.jpg", "*.png"));

        File file = fileChooser.showOpenDialog(this.stageInitializer.getScene().getWindow());

        if (file != null) {
            this.thumbnail.setText(file.getAbsolutePath());
        }
    }

    public void chooseVideoOrAudioFile() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Media File", "*.mp4", "*.mp3"));

        File file = fileChooser.showOpenDialog(this.stageInitializer.getScene().getWindow());

        if (file != null) {
            this.videoOrAudio.setText(file.getAbsolutePath());
        }
    }

    private boolean mediaInformationIsValid() {
        return true;
    }
}
