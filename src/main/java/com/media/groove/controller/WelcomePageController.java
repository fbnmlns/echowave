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
    public Button signUpPageButton;

    @Value("classpath:/ui/signup.fxml")
    private Resource signupScreenSource;

    @Value("classpath:/ui/login.fxml")
    private Resource loginScreenSource;

    private final StageInitializer stageInitializer;

    public void initialize() {
    }

    public WelcomePageController(StageInitializer stageInitializer) {
        this.stageInitializer = stageInitializer;
    }

    public void goToSignupPage() {
        stageInitializer.switchScene(this.signupScreenSource);
    }

    public void goToLoginPage() {
        stageInitializer.switchScene(this.loginScreenSource);
    }
}
