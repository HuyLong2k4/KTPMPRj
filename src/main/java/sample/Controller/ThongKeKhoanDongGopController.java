package main.java.sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import database.DatabaseConnection;

public class ThongKeKhoanDongGopController {
    public void onSearch () {

    }

    public void onClearSearch() {

    }

    @FXML
    private Label lblCuDan;

    @FXML
    private Label lblKhoanThu;

    @FXML
    private Label lblCanHo;

    @FXML
    private Label lblThongke;

    @FXML
    private Label lblTrangChu;

    @FXML
    private Label lblChuHo;

    @FXML
    private TableView<Map<String, Object>> tableViewThongKe;
    @FXML
    private TableColumn<Map<String, Object>, String> colTenKhoanDongGop;
    @FXML
    private TableColumn<Map<String, Object>, Double> colTongTien;
    @FXML
    private TableColumn<Map<String, Object>, Integer> colSoLuotDongGop;
    @FXML
    private TableColumn<Map<String, Object>, String> colMoTa;
    @FXML
    private Label totalAmountLabel;
    @FXML
    private Label tempResidentLabel;
    @FXML
    private Label tempAbsentLabel;

    public void initialize() {
        // Initialize columns
        colTenKhoanDongGop.setCellValueFactory(cellData -> {
            Map<String, Object> row = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty((String) row.get("tenKhoanDongGop"));
        });

        colTongTien.setCellValueFactory(cellData -> {
            Map<String, Object> row = cellData.getValue();
            // Assuming tongTien is stored as Double in Map
            Double tongTien = (Double) row.get("tongTien");
            return new javafx.beans.property.SimpleDoubleProperty(tongTien != null ? tongTien : 0.0).asObject();
        });

        colSoLuotDongGop.setCellValueFactory(cellData -> {
            Map<String, Object> row = cellData.getValue();
            // Assuming soLuotDongGop is stored as Integer in Map
            Integer soLuotDongGop = (Integer) row.get("soLuotDongGop");
            return new javafx.beans.property.SimpleIntegerProperty(soLuotDongGop != null ? soLuotDongGop : 0).asObject();
        });

        colMoTa.setCellValueFactory(cellData -> {
            Map<String, Object> row = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty((String) row.get("moTa"));
        });

        // Load data
        loadThongKeData();
        loadResidentStats();
    }

    private void loadThongKeData() {
        String query = "SELECT f.name, f.description, SUM(hf.amount) AS tongTien, COUNT(hf.household_id) AS soLuotDongGop " +
                "FROM fees f " +
                "JOIN household_fees hf ON f.fees_id = hf.fees_id " +
                "GROUP BY f.name, f.description";

        ObservableList<Map<String, Object>> data = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            double totalContribution = 0;

            while (rs.next()) {
                Map<String, Object> row = new java.util.HashMap<>();
                row.put("tenKhoanDongGop", rs.getString("name"));
                row.put("moTa", rs.getString("description"));
                double tongTien = rs.getDouble("tongTien");
                row.put("tongTien", tongTien);
                row.put("soLuotDongGop", rs.getInt("soLuotDongGop"));
                data.add(row);
                totalContribution += tongTien;
            }

            System.out.println("Loaded " + data.size() + " rows for ThongKe data.");
            tableViewThongKe.setItems(data);
            totalAmountLabel.setText(String.format("%,.2f VNĐ", totalContribution));

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Lỗi khi tải dữ liệu thống kê: " + e.getMessage());
        }
    }

    private void loadResidentStats() {
        // Query to get total residents (resident)
        String totalResidentQuery = "SELECT COUNT(*) AS totalResidents FROM resident";
        // Query to get temporary absent residents (temporary_absent)
        String absentResidentQuery = "SELECT COUNT(*) AS absentResidents FROM temporary_absent";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement totalStmt = conn.prepareStatement(totalResidentQuery);
             ResultSet totalRs = totalStmt.executeQuery();
             PreparedStatement absentStmt = conn.prepareStatement(absentResidentQuery);
             ResultSet absentRs = absentStmt.executeQuery()) {

            if (totalRs.next()) {
                int total = totalRs.getInt("totalResidents");
                tempResidentLabel.setText(String.valueOf(total));
            }

            if (absentRs.next()) {
                int absent = absentRs.getInt("absentResidents");
                tempAbsentLabel.setText(String.valueOf(absent));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Lỗi khi tải thống kê dân cư: " + e.getMessage());
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