package com.media.groove.controller;

import com.media.groove.StageInitializer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WelcomePageController {
    @FXML
    public Button btnLogin;

    @Value("classpath:/hola.fxml")
    private Resource screenSource;
    private final StageInitializer stageInitializer;

    public WelcomePageController(StageInitializer stageInitializer) {
        this.stageInitializer = stageInitializer;
    }

    public void go() throws IOException {
        stageInitializer.switchScene(screenSource);
    }
}
