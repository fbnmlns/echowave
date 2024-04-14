package com.media.groove.controller;

import com.media.groove.StageInitializer;
import com.media.groove.service.AuthenticationService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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

    @FXML
    public Label lblUsername;

    @FXML
    public Label lblPassword;

    @FXML
    public Label lblWrongCredentials;

    @Value("classpath:/ui/home.fxml")
    private Resource homeScreenSource;

    public LoginController(AuthenticationService authenticationService, StageInitializer stageInitializer) {
        this.authenticationService = authenticationService;
        this.stageInitializer = stageInitializer;
    }

    @FXML
    public void logIn() {
        if (this.logInInformationIsValid()) {
            if (this.authenticationService.authenticate(this.username.getText(), this.password.getText())) {
                this.stageInitializer.switchScene(homeScreenSource);
                
            } else {
                this.indicateCredentialsAreWrong();
            }
        }
    }

    private void indicateCredentialsAreWrong() {
        this.indicateErrorOnUsernameField();
        this.indicateErrorOnPasswordField();

        this.lblWrongCredentials.setVisible(true);
    }

    private boolean logInInformationIsValid() {
        boolean isValid = true;

        if (this.username.getText().isEmpty()) {
            indicateErrorOnUsernameField();

            isValid = false;
        }

        if (this.password.getText().isEmpty()) {
            this.indicateErrorOnPasswordField();

            isValid = false;
        }

        return isValid;
    }

    private void indicateErrorOnUsernameField() {
        this.username.getStyleClass().remove("input");
        this.username.getStyleClass().add("error");
        this.lblUsername.getStyleClass().add("label-error");
    }

    private void indicateErrorOnPasswordField() {
        this.password.getStyleClass().remove("input");
        this.password.getStyleClass().add("error");
        this.lblPassword.getStyleClass().add("label-error");
    }
}
