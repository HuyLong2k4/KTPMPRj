package main.java.sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class KhoanThuController {
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


    //    router chuyển kênh
    @FXML
    public void handleTrangChuClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Homepage.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) lblTrangChu.getScene().getWindow(); // lấy cửa sổ hiện tại
            stage.setScene(new Scene(canHoPage));
            stage.setTitle("Trang Chu");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

    public void handleCuDanClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/Cudan.fxml"));
            Parent khoanThuPage = loader.load();

            Stage stage = (Stage) lblCuDan.getScene().getWindow(); // lấy cửa sổ hiện tại
            stage.setScene(new Scene(khoanThuPage));
            stage.setTitle("Quản lý Cu Dan");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

    public void handleKhoanthuClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/KhoanThu.fxml"));
            Parent khoanThuPage = loader.load();

            Stage stage = (Stage) lblKhoanThu.getScene().getWindow(); // lấy cửa sổ hiện tại
            stage.setScene(new Scene(khoanThuPage));
            stage.setTitle("Quản lý khoản thu");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

    @FXML
    public void handleCanHoClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/CanHo.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) lblCanHo.getScene().getWindow(); // lấy cửa sổ hiện tại
            stage.setScene(new Scene(canHoPage));
            stage.setTitle("Quản lý căn hộ");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

    public void handleThongkelick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/ThongKe.fxml"));
            Parent khoanThuPage = loader.load();

            Stage stage = (Stage) lblThongke.getScene().getWindow(); // lấy cửa sổ hiện tại
            stage.setScene(new Scene(khoanThuPage));
            stage.setTitle("Quản lý tống kê");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }
    public void handleTamVangClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/TamVang.fxml"));
            Parent khoanThuPage = loader.load();

            Stage stage = (Stage) lblThongke.getScene().getWindow(); // lấy cửa sổ hiện tại
            stage.setScene(new Scene(khoanThuPage));
            stage.setTitle("Quản lý tạm vắng");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }
    public void handleTamTruClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/TamTru.fxml"));
            Parent khoanThuPage = loader.load();

            Stage stage = (Stage) lblThongke.getScene().getWindow(); // lấy cửa sổ hiện tại
            stage.setScene(new Scene(khoanThuPage));
            stage.setTitle("Quản lý tạm trú");
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
