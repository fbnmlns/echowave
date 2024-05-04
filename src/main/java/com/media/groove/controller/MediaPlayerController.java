package com.media.groove.controller;

import com.media.groove.StageInitializer;
import com.media.groove.session.VideoSession;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import java.io.File;

@Controller
public class MediaPlayerController {
    private final StageInitializer stageInitializer;

    private final VideoSession videoSession;

    private MediaPlayer mediaPlayer;

    private boolean isPlaying;

    private boolean videoHasEnded;

    public Image playIcon;

    public Image pauseIcon;

    public Image restartIcon;

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

    @Value("classpath:/ui/home.fxml")
    private Resource homeScreenSource;

    public MediaPlayerController(StageInitializer stageInitializer, VideoSession videoSession) {
        this.stageInitializer = stageInitializer;
        this.videoSession = videoSession;
        this.isPlaying = true;
        this.videoHasEnded = false;
    }

    public void initialize() {
        this.mediaPlayer = new MediaPlayer(this.getCurrentMedia());
        this.mediaScreen.setMediaPlayer(mediaPlayer);
        this.stageInitializer.setStageTitle(this.videoSession.getCurrentVideo().getTitle());

        this.playIcon = getImage("src/main/resources/ui/assets/images/play.png");
        this.pauseIcon = getImage("src/main/resources/ui/assets/images/pause.png");
        this.restartIcon = getImage("src/main/resources/ui/assets/images/restart.png");

        this.mediaPlayer.volumeProperty().bindBidirectional(this.volumeSlider.valueProperty());
        this.setVideoMaxTime();
        this.bindCurrentTimeLabel();
        this.keepTrackOfVideoSliderChanging();
        this.keepTrackOfVideoSliderValueProperty();

        this.mediaPlayer.play();

        this.allowToRestartMedia();
    }

    public void playback() {
        this.bindCurrentTimeLabel();

        if (this.videoHasEnded) {
            this.videoSlider.setValue(0);
            this.isPlaying = false;
        }

        if (this.isPlaying) {
            this.mediaPlayer.pause();
            this.playbackButtonIcon.setImage(this.playIcon);
            this.isPlaying = false;

        } else {
            this.playbackButtonIcon.setImage(this.pauseIcon);
            this.mediaPlayer.play();
            this.isPlaying = true;
        }
    }

    public void closeMediaPlayer() {
        this.mediaPlayer.stop();
        this.videoSession.clearCurrentVideo();
        this.stageInitializer.setStageTitle("Home");
        this.stageInitializer.switchScene(this.homeScreenSource);
    }

    private void bindCurrentTimeLabel() {
        this.lblCurrentTime
                .textProperty()
                .bind(Bindings.createStringBinding(() ->
                        getTime(this.mediaPlayer.getCurrentTime()) + "  /", this.mediaPlayer.currentTimeProperty()));
    }

    private void setVideoMaxTime() {
        this.mediaPlayer.totalDurationProperty().addListener((observable, oldDuration, newDuration) -> {
            this.bindCurrentTimeLabel();

            this.videoSlider.maxProperty().bind(Bindings.createDoubleBinding(
                    () -> this.mediaPlayer.getTotalDuration().toSeconds(), this.mediaPlayer.totalDurationProperty()
            ));

            this.lblRuntime.setText(getTime(newDuration));
        });
    }

    private void keepTrackOfVideoSliderChanging() {
        this.videoSlider.valueChangingProperty().addListener((observable, wasChanging, isChanging) -> {
            this.bindCurrentTimeLabel();

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

    private void keepTrackOfVideoSliderValueProperty() {
        this.videoSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.bindCurrentTimeLabel();

            double currentTime = this.mediaPlayer.getCurrentTime().toSeconds();

            if (Math.abs(currentTime - newValue.doubleValue()) > 0.5) {
                this.mediaPlayer.seek(Duration.seconds(newValue.doubleValue()));
            }
        });
    }

    private void allowToRestartMedia() {
        this.mediaPlayer.setOnEndOfMedia(() -> {
            this.playbackButtonIcon.setImage(this.restartIcon);
            this.videoHasEnded = true;

            if (!(this.lblCurrentTime.textProperty().equals(this.lblRuntime.textProperty()))) {
                this.lblCurrentTime.textProperty().unbind();
                this.lblCurrentTime.setText(getTime(this.mediaPlayer.getCurrentTime()) + "  /");
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

    private Image getImage(String imagePath) {
        return new Image(new File(imagePath).toURI().toString());
    }

    private Media getCurrentMedia() {
        return new Media(new File(this.videoSession.getCurrentVideo().getFile()).toURI().toString());
    }
}
