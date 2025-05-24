package main.java.sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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


//    public void initialize() {
//        loginButton.setOnAction(event -> {
//            String email = emailField.getText();
//            String password = passwordField.getText();
//            System.out.println("Email: " + email + ", Password: " + password);
//        });
//    }
@FXML
public void Submit(ActionEvent event) {
    String email = emailField.getText();
    String password = passwordField.getText();
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Thông tin đăng nhập");
    alert.setHeaderText(null);
    alert.setContentText("Email: " + email + "\nPassword: " + password);
    alert.show();
}

}
