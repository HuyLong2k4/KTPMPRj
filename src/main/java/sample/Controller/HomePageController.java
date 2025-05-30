package main.java.sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HomePageController {
    @FXML
    private Label lblCanHo;

    @FXML
    private Label lblKhoanThu;

    @FXML
    private Label lblCuDan;

    @FXML
    private Label lblThongke;

    @FXML
    private Label lblChuHo;

    @FXML
    private Label lblTamVang;

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

    @FXML
    private Button btnCanHo;

    @FXML
    private void handleCanHoClickBtn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/CanHo.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) btnCanHo.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setTitle("Quản lý căn hộ");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Không thể chuyển sang trang Căn hộ: " + e.getMessage());
        }
    }
    @FXML
    private Button btnCuDan;

    @FXML
    private void handleCuDanClickBtn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/NhanKhau.fxml"));
            Parent cuDanPage = loader.load();

            Stage stage = (Stage) btnCanHo.getScene().getWindow();
            stage.setScene(new Scene(cuDanPage));
            stage.setTitle("Quản lý cư dân");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Không thể chuyển sang trang Cư dân: " + e.getMessage());
        }
    }

    @FXML
    private Button btnKhoanThu;

    @FXML
    public void handleKhoanthuClickBtn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/KhoanThu.fxml"));
            Parent khoanThuPage = loader.load();

            Stage stage = (Stage) btnKhoanThu.getScene().getWindow(); // lấy cửa sổ hiện tại
            stage.setScene(new Scene(khoanThuPage));
            stage.setTitle("Quản lý khoản thu");
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

    public void handleCuDanClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/NhanKhau.fxml"));
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

            Stage stage = (Stage) lblTamVang.getScene().getWindow(); // lấy cửa sổ hiện tại
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

    @FXML
    private Label lblTongSoCanHo;

    @FXML
    private Label lblTongSoCuDan;

    @FXML
    private Label lblTongSoKhoanThu;

    @FXML
    public void initialize() {
        updateSummaryCounts();
    }

    private void updateSummaryCounts() {
        try (Connection conn = database.DatabaseConnection.getConnection()) {
            // Đếm số căn hộ
            PreparedStatement ps1 = conn.prepareStatement("SELECT COUNT(*) FROM household");
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                lblTongSoCanHo.setText("Tổng số: " + rs1.getInt(1));
            }

            // Đếm số cư dân
            PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(*) FROM resident");
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                lblTongSoCuDan.setText("Tổng số: " + rs2.getInt(1));
            }

            // Đếm số khoản thu
            PreparedStatement ps3 = conn.prepareStatement("SELECT COUNT(*) FROM fees");
            ResultSet rs3 = ps3.executeQuery();
            if (rs2.next()) {
                lblTongSoKhoanThu.setText("Tổng số: " + rs3.getInt(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi khi lấy dữ liệu thống kê: " + e.getMessage());
        }
    }
    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }


}
