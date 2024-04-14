package com.media.groove.controller;

import com.media.groove.StageInitializer;
import com.media.groove.service.AuthenticationService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {
    private final AuthenticationService authenticationService;

    private final StageInitializer stageInitializer;

    @FXML
    public TextField username;

    @FXML
    public PasswordField password;

    @Value("classpath:/ui/home.fxml")
    private Resource homeScreenSource;

    public LoginController(AuthenticationService authenticationService, StageInitializer stageInitializer) {
        this.authenticationService = authenticationService;
        this.stageInitializer = stageInitializer;
    }

    @FXML
    public void logIn() {
        if (this.authenticationService.authenticate(this.username.getText(), this.password.getText())) {
            this.stageInitializer.switchScene(homeScreenSource);
        } else {
            System.out.println("error");
        }
    }
}
