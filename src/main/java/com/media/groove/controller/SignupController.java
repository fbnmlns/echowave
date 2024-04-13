package com.media.groove.controller;

import com.media.groove.entity.User;
import com.media.groove.service.UserService;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

@Controller
public class SignupController {
    private final UserService userService;

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

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @FXML
    public void signUp() {
        User newUser = new User();

        if (this.signUpInformationIsValid()) {
            newUser.setName(this.name.getText());
            newUser.setLastName(this.lastName.getText());
            newUser.setUsername(this.username.getText());

            this.userService.createUser(newUser, this.password.getText());
        }
    }

    private boolean signUpInformationIsValid() {
        boolean isValid = true;

        if (this.username.getText().isEmpty()) {
            this.username.getStyleClass().remove("input");

            this.username.getStyleClass().add("error");
            this.lblUsername.getStyleClass().add("label-error");

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
