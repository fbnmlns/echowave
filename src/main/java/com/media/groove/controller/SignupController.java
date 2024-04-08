package com.media.groove.controller;

import com.media.groove.entity.User;
import com.media.groove.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

@Controller
public class SignupController {
    private final UserService userService;

    @FXML
    public PasswordField password;

    @FXML
    public TextField name;

    @FXML
    public TextField lastName;

    @FXML
    public TextField username;

    @FXML
    public Button signupButton;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @FXML
    public void signUp() {
        User newUser = new User();

        newUser.setName(this.name.getText());
        newUser.setLastName(this.lastName.getText());
        newUser.setUsername(this.username.getText());

        this.userService.createUser(newUser, this.password.getText());
    }
}
