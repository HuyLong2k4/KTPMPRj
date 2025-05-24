package main.java.sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getText();
            System.out.println("Email: " + email + ", Password: " + password);
        });
    }
}
