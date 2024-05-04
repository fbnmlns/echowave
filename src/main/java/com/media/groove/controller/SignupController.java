package com.media.groove.controller;

import com.media.groove.StageInitializer;
import com.media.groove.entity.User;
import com.media.groove.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

@Controller
public class SignupController {
    private final UserService userService;

    private final StageInitializer stageInitializer;

    @FXML
    public PasswordField password;

    @FXML
    public PasswordField passwordConfirmation;

    @FXML
    public TextField name;

    @FXML
    public TextField lastName;

    @FXML
    public TextField username;

    @FXML
    public Button signupButton;

    @FXML
    public Label lblName;

    @FXML
    public Label lblLastName;

    @FXML
    public Label lblUsername;

    @FXML
    public Label lblPassword;

    @FXML
    public Label lblPasswordConfirmation;

    @FXML
    public Label lblUsernameNotAvailable;

    @FXML
    public Label lblAccountCreationError;

    @Value("classpath:/ui/login.fxml")
    private Resource loginScreenSource;

    @Value("classpath:/ui/welcome-page.fxml")
    private Resource welcomeScreenSource;

    public SignupController(UserService userService, StageInitializer stageInitializer) {
        this.userService = userService;
        this.stageInitializer = stageInitializer;
    }

    @FXML
    public void signUp() {
        User newUser = new User();

        if (this.signUpInformationIsValid()) {
            newUser.setName(this.name.getText());
            newUser.setLastName(this.lastName.getText());
            newUser.setUsername(this.username.getText());

            this.userService.createUser(newUser, this.password.getText());

            if (newUser.getId() != null) {
                this.stageInitializer.switchScene(this.loginScreenSource);

            } else {
                this.lblAccountCreationError.setVisible(true);
            }
        }
    }

    public void goBack() {
        this.stageInitializer.switchScene(this.welcomeScreenSource);
    }

    private boolean signUpInformationIsValid() {
        boolean isValid = true;

        if (this.username.getText().isEmpty()) {
            this.username.getStyleClass().remove("input");

            this.username.getStyleClass().add("error");
            this.lblUsername.getStyleClass().add("label-error");

            isValid = false;

        } else if (this.userService.isUsernameAlreadyTaken(this.username.getText())) {
            this.username.getStyleClass().remove("input");

            this.username.getStyleClass().add("error");
            this.lblUsername.getStyleClass().add("label-error");

            this.lblUsernameNotAvailable.setVisible(true);

            isValid = false;
        }

        if (this.lastName.getText().isEmpty()) {
            this.lastName.getStyleClass().remove("input");

            this.lastName.getStyleClass().add("error");
            this.lblLastName.getStyleClass().add("label-error");

            isValid = false;
        }

        if (this.name.getText().isEmpty()) {
            this.name.getStyleClass().remove("input");

            this.name.getStyleClass().add("error");
            this.lblName.getStyleClass().add("label-error");

            isValid = false;
        }

        if (this.password.getText().isEmpty() || !this.passwordsMatch()) {
            this.password.getStyleClass().remove("input");
            this.passwordConfirmation.getStyleClass().remove("error");

            this.password.getStyleClass().add("error");
            this.passwordConfirmation.getStyleClass().add("error");

            this.lblPassword.getStyleClass().add("label-error");
            this.lblPasswordConfirmation.getStyleClass().add("label-error");

            isValid = false;
        }

        return isValid;
    }

    private boolean passwordsMatch() {
        return (this.password.getText().equals(this.passwordConfirmation.getText()));
    }
}
