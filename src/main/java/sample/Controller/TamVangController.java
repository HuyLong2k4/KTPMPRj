package main.java.sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TamVangController {
    public void onSearch () {

    }

    public void onRefresh () {

    }

    public void onAdd () {

    }

    @FXML
    private Label lblCanHo;

    @FXML
    private Label lblThongke;

    @FXML
    private Label lblCuDan;

    @FXML
    private Label lblTrangChu;

    @FXML
    private Label lblKhoanThu;

    @FXML
    private Label lblChuHo;

    public void handleChuHoClick() {
        navigateTo("/main/java/sample/Views/ChuHo.fxml", "Quản lý Chủ Hộ", lblChuHo);
    }

    @FXML
    public void handleTrangChuClick() {
        navigateTo("Homepage.fxml", "Trang Chủ", lblTrangChu);
    }

    public void handleCuDanClick() {
        navigateTo("/main/java/sample/Views/Cudan.fxml", "Quản lý Cư Dân", lblCuDan);
    }

    public void handleKhoanthuClick() {
        navigateTo("/main/java/sample/Views/KhoanThu.fxml", "Quản lý Khoản Thu", lblKhoanThu);
    }

    @FXML
    public void handleCanHoClick() {
        navigateTo("/main/java/sample/Views/CanHo.fxml", "Quản lý Căn Hộ", lblCanHo);
    }

    public void handleThongkelick() {
        navigateTo("/main/java/sample/Views/ThongKe.fxml", "Quản lý Thống Kê", lblThongke);
    }

    public void handleTamVangClick() {
        navigateTo("/main/java/sample/Views/TamVang.fxml", "Quản lý Tạm Vắng", lblThongke);
    }

    public void handleTamTruClick() {
        navigateTo("/main/java/sample/Views/TamTru.fxml", "Quản lý Tạm Trú", lblThongke);
    }

    private void navigateTo(String fxmlPath, String title, Label sourceLabel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent page = loader.load();

            Stage stage = (Stage) sourceLabel.getScene().getWindow();
            stage.setScene(new Scene(page));
            stage.setTitle(title);
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
