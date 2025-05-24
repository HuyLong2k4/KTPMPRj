package main.java.sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField Height;

    @FXML
    public void Submit(ActionEvent event) {
        String height = Height.getText();  // Sửa tên biến
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Chiều cao của bạn: " + height + " cm");  // Thêm dấu cách và dấu hai chấm
        alert.show();
    }
}
