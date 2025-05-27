package main.java.sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ThongKeKhoanDongGopController {
    public void onSearch () {

    }

    public void onClearSearch() {

    }

    @FXML
    private Label lblChuHo;

    public void handleChuHoClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/ChuHo.fxml"));
            Parent chuHoPage = loader.load();

            Stage stage = (Stage) lblChuHo.getScene().getWindow();
            stage.setScene(new Scene(chuHoPage));
            stage.setTitle("Quan ly Chủ Hộ");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
