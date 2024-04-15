package com.media.groove.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import org.springframework.stereotype.Controller;

import javax.naming.Binding;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.Callable;

@Controller
public class MediaPlayerController {
    private MediaPlayer mediaPlayer;

    private Media mediaFile;

    private boolean isPlaying;

    private boolean videoHasEnded;

    private boolean isMuted;


    @FXML
    public VBox mainContainer;

    @FXML
    public MediaView mediaScreen;

    @FXML
    public Slider videoSlider;

    @FXML
    public HBox videoControllers;

    @FXML
    public Button playbackButton;

    @FXML
    public HBox volumeContainer;

    @FXML
    public Slider volumeSlider;

    @FXML
    private ImageView playbackButtonIcon;

    @FXML
    private Label lblCurrentTime;

    @FXML
    private Label lblRuntime;

    public MediaPlayerController() {
        this.isPlaying = true;
        this.videoHasEnded = false;
        this.isMuted = false;
    }

    public void initialize() {
        this.mediaFile = new Media(new File("src/main/resources/ui/assets/house.mp4").toURI().toString());
        this.mediaPlayer = new MediaPlayer(mediaFile);
        this.mediaScreen.setMediaPlayer(mediaPlayer);

        this.mediaPlayer.volumeProperty().bindBidirectional(this.volumeSlider.valueProperty());
        this.setVideoMaxTime();
        this.bindCurrentTimeLabel();
        this.trackTimeSliderChanging();
        this.trackTimeSliderPropertyChanged();

        this.mediaPlayer.play();
    }

    public void playback() {
        if (this.videoHasEnded) {
            this.videoSlider.setValue(0);
            this.mediaPlayer.pause();
        }

        if (this.isPlaying) {
            this.mediaPlayer.pause();
            Image image = getImage("src/main/resources/ui/assets/play.png");
            this.playbackButtonIcon.setImage(image);
            this.isPlaying = false;

        } else {
            Image pauseIcon = getImage("src/main/resources/ui/assets/pause.png");
            this.playbackButtonIcon.setImage(pauseIcon);
            this.mediaPlayer.play();
            this.isPlaying = true;
        }
    }

    private Image getImage(String imagePath) {
        return new Image(new File(imagePath).toURI().toString());
    }

    private void bindCurrentTimeLabel() {
        this.lblCurrentTime
                .textProperty()
                .bind(Bindings
                        .createStringBinding(() -> getTime(this.mediaPlayer.getCurrentTime()) + "  /", this.mediaPlayer.currentTimeProperty()));
    }

    private void setVideoMaxTime() {
        this.mediaPlayer.totalDurationProperty().addListener((observable, oldDuration, newDuration) -> {
            this.videoSlider.maxProperty().bind(Bindings.createDoubleBinding(
                    () -> this.mediaPlayer.getTotalDuration().toSeconds(), this.mediaPlayer.totalDurationProperty()
            ));

            this.lblRuntime.setText(getTime(newDuration));
        });
    }

    private void trackTimeSliderChanging() {
        this.videoSlider.valueChangingProperty().addListener((observable, wasChanging, isChanging) -> {
            if (!isChanging) {
                this.mediaPlayer.seek(Duration.seconds(this.videoSlider.getValue()));
            }
        });

        this.mediaPlayer.currentTimeProperty().addListener((observable, oldTime, newTime) -> {
            if (!this.videoSlider.isValueChanging()) {
                this.videoSlider.setValue(newTime.toSeconds());
            }
        });
    }

    private void trackTimeSliderPropertyChanged() {
        this.videoSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double currentTime = this.mediaPlayer.getCurrentTime().toSeconds();

            if (Math.abs(currentTime - newValue.doubleValue()) > 0.5) {
                this.mediaPlayer.seek(Duration.seconds(newValue.doubleValue()));
            }
        });
    }

    private String getTime(Duration time) {
        int hours = (int) time.toHours();
        int minutes = (int) time.toMinutes();
        int seconds = (int) time.toSeconds();

        if (seconds > 59) {
            seconds = seconds % 60;
        }

        if (minutes > 59) {
            minutes = minutes % 60;
        }

        if (hours > 59) {
            hours = hours % 64;
        }

        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);

        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }
}
