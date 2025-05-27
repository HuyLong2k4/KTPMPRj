package main.java.sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ChuHoController {
    @FXML
    private Label lblCanHo;

    @FXML
    private Label lblKhoanThu;

    @FXML
    private Label lblCuDan;

    @FXML
    private Label lblThongKe;

    @FXML
    private Label lblTrangChu;

    public void handleTrangChuClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Controller/HomePage.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) lblTrangChu.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setTitle("Quan ly Can Hộ");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

    public void handleCanHoClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/CanHo.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) lblCanHo.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setTitle("Quan ly Can Hộ");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

    public void handleKhoanThuClick() {
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

    public void handleCuDanClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/CuDan.fxml"));
            Parent khoanThuPage = loader.load();

            Stage stage = (Stage) lblCuDan.getScene().getWindow(); // lấy cửa sổ hiện tại
            stage.setScene(new Scene(khoanThuPage));
            stage.setTitle("Quản lý cư dân");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

    public void handleThongKeClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/ThongKeKhoanDongGop.fxml"));
            Parent thongKePage = loader.load();

            Stage stage = (Stage) lblThongKe.getScene().getWindow(); // lấy cửa sổ hiện tại
            stage.setScene(new Scene(thongKePage));
            stage.setTitle("Quản lý thống kê");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }



    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    //onAction
    public void onSearch() {

    }
    public void onRefresh() {

    }
    public void onAdd() {

    }
}
