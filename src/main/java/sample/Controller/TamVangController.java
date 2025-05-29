package main.java.sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import database.DatabaseConnection;
import main.java.sample.Model.TemporaryAbsent;
import main.java.sample.Model.Resident;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TamVangController {

    @FXML
    private TextField txtGlobalSearch;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<TemporaryAbsent> tableTamVang;

    @FXML
    private TableColumn<TemporaryAbsent, Number> colID;

    @FXML
    private TableColumn<TemporaryAbsent, Number> colResidentId;


    @FXML
    private TableColumn<TemporaryAbsent, String> colLocation;

    @FXML
    private TableColumn<TemporaryAbsent, String> colStartDate;

    @FXML
    private TableColumn<TemporaryAbsent, String> colEndDate;

    @FXML
    private TableColumn<TemporaryAbsent, String> colReason;

    private ObservableList<TemporaryAbsent> temporaryAbsentList = FXCollections.observableArrayList();


    @FXML
    private TableColumn<TemporaryAbsent, Void> colAction;

    @FXML
    private Label lblTrangChu;

    @FXML
    private Label lblCanHo;

    @FXML
    private Label lblCuDan;

    @FXML
    private Label lblChuHo;

    @FXML
    private Label lblKhoanThu;

    @FXML
    private Label lblThongke;

    @FXML
    private Label lblTamVang;

    @FXML
    private Label lblTamTru;


    public void initialize() {
        // Thiết lập giá trị cho các cột
        colID.setCellValueFactory(cellData -> cellData.getValue().temporaryAbsentIdProperty());
        colLocation.setCellValueFactory(cellData -> cellData.getValue().absentLocationProperty());
        colStartDate.setCellValueFactory(cellData -> cellData.getValue().startAtProperty());
        colEndDate.setCellValueFactory(cellData -> cellData.getValue().endAtProperty());
        colResidentId.setCellValueFactory(cellData -> cellData.getValue().residentIdProperty());
        colReason.setCellValueFactory(cellData -> cellData.getValue().reasonProperty());
        addActionButtonsToTable();
        loadTemporaryAbsents();
    }


    private void addActionButtonsToTable() {
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button btnDelete = new Button("Xóa");
            private final Button btnEdit = new Button("Sửa");
            private final HBox hbox = new HBox(5, btnEdit, btnDelete);

            {
                btnDelete.setOnAction(event -> {
                    TemporaryAbsent ta = getTableView().getItems().get(getIndex());
                    deleteTemporaryAbsent(ta);
                });

                btnEdit.setOnAction(event -> {
                    TemporaryAbsent ta = getTableView().getItems().get(getIndex());
                    editTemporaryAbsent(ta);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(hbox);
                }
            }
        });
    }

    @FXML
    private void editTemporaryAbsent(TemporaryAbsent h) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Sửa thông tin tam vang");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TextField mahoField = new TextField(h.getAbsentLocation());
        TextField cmndField = new TextField(h.getStartAt());
        TextField sdtField = new TextField(h.getEndAt());
        TextField hoTenField = new TextField(String.valueOf(h.getResidentId()));
        TextField gioiTinhField = new TextField(h.getReason());



        vbox.getChildren().addAll(
                new Label("Địa điểm tạm vắng:"), mahoField,
                new Label("Thời gian bắt đầu:"), cmndField,
                new Label("Thời gian kết thúc:"), sdtField,
                new Label("ID người tạm vắng:"), hoTenField,
                new Label("Lí do tạm vắng:"), gioiTinhField);


        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String maho = mahoField.getText().trim();
                String cmnd = cmndField.getText().trim();
                String sdt = sdtField.getText().trim();
                String hotenStr = hoTenField.getText().trim();
                String gioiTinh = gioiTinhField.getText().trim();

                try {
                    int hoten = Integer.parseInt(hotenStr);

                    try (Connection conn = DatabaseConnection.getConnection()) {
                        String sql = "UPDATE temporary_absent SET absent_location = ?, start_at = ?, end_at = ?, resident_id = ?, reason = ?  WHERE temporary_absent_id = ?";
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setString(1, maho);
                        ps.setString(2, cmnd);
                        ps.setString(3, sdt);
                        ps.setInt(4,  hoten);
                        ps.setString(5,  gioiTinh);


                        ps.setInt(6, h.getTemporaryAbsentId());

                        if (ps.executeUpdate() > 0) {
                            h.setAbsentLocation(maho);
                            h.setStartAt(cmnd);
                            h.setEndAt(sdt);
                            h.setResidentId(hoten);
                            h.setReason(gioiTinh);


                            tableTamVang.refresh();
                            showAlert("Đã cập nhật thông tin cư dân");
                        }
                    }
                }catch (NumberFormatException nfe) {
                    showAlert("Sai định dạng số: " + nfe.getMessage());
                } catch (Exception ex) {
                    showAlert("Lỗi khi cập nhật: " + ex.getMessage());
                }
            }
        });
    }


    private void deleteTemporaryAbsent(TemporaryAbsent h) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM temporary_absent WHERE temporary_absent_id = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, h.getTemporaryAbsentId());
            if (ps.executeUpdate() > 0) {
                temporaryAbsentList.remove(h);
                tableTamVang.refresh();
                showAlert("Đã xóa cư dân tạm vắng " + h.getTemporaryAbsentId());
            }
        } catch (Exception e) {
            showAlert("Lỗi khi xóa: " + e.getMessage());
        }
    }



    @FXML
    private void onAdd() {
        Dialog<TemporaryAbsent> dialog = new Dialog<>();
        dialog.setTitle("Thêm mới tạm vắng");
        dialog.setHeaderText("Nhập thông tin tạm vắng");

        ButtonType addButtonType = new ButtonType("Thêm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        TextField residentIdField = new TextField();
        residentIdField.setPromptText("ID cư dân");
        TextField locationField = new TextField();
        locationField.setPromptText("Địa điểm tạm vắng");

        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Ngày bắt đầu (yyyy-MM-dd)");

        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setPromptText("Ngày kết thúc (yyyy-MM-dd)");

        TextField reasonField = new TextField();
        reasonField.setPromptText("Lý do");

        grid.add(new Label("ID cư dân*:"), 0, 0);
        grid.add(residentIdField, 1, 0);
        grid.add(new Label("Địa điểm*:"), 0, 1);
        grid.add(locationField, 1, 1);
        grid.add(new Label("Ngày bắt đầu*:"), 0, 2);
        grid.add(startDatePicker, 1, 2);
        grid.add(new Label("Ngày kết thúc*:"), 0, 3);
        grid.add(endDatePicker, 1, 3);
        grid.add(new Label("Lý do:"), 0, 4);
        grid.add(reasonField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                if (residentIdField.getText().isEmpty() || locationField.getText().isEmpty() ||
                        startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
                    showAlert("Vui lòng nhập các trường bắt buộc (đánh dấu *)");
                    return null;
                }

                try {
                    return new TemporaryAbsent(
                            0, // ID sẽ được tạo tự động
                            locationField.getText(),
                            startDatePicker.getValue() != null ? startDatePicker.getValue().toString() : "",
                            endDatePicker.getValue() != null ? endDatePicker.getValue().toString() : "",
                            Integer.parseInt(residentIdField.getText()),
                            reasonField.getText()
                    );
                } catch (NumberFormatException e) {
                    showAlert("ID cư dân phải là số");
                    return null;
                }
            }
            return null;
        });

        Optional<TemporaryAbsent> result = dialog.showAndWait();
        result.ifPresent(newTa -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO temporary_absent (absent_location, start_at, end_at, resident_id, reason) " +
                        "VALUES (?, ?, ?, ?, ?)";

                PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, newTa.getAbsentLocation());
                ps.setString(2, newTa.getStartAt());
                ps.setString(3, newTa.getEndAt());
                ps.setInt(4, newTa.getResidentId());
                ps.setString(5, newTa.getReason());

                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        newTa.setTemporaryAbsentId(rs.getInt(1));
                        temporaryAbsentList.add(newTa);
                        showAlert("Thêm tạm vắng thành công!");
                    }
                }
            } catch (Exception e) {
                showAlert("Lỗi khi thêm tạm vắng: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void onSearch() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            tableTamVang.setItems(temporaryAbsentList);
            return;
        }

        ObservableList<TemporaryAbsent> filtered = FXCollections.observableArrayList();
        for (TemporaryAbsent ta : temporaryAbsentList) {
            if (String.valueOf(ta.getTemporaryAbsentId()).contains(keyword) ||
                    String.valueOf(ta.getResidentId()).contains(keyword) ||
//                    getResidentNameById(ta.getResidentId()).toLowerCase().contains(keyword) ||
                    ta.getAbsentLocation().toLowerCase().contains(keyword) ||
                    ta.getStartAt().toLowerCase().contains(keyword) ||
                    ta.getEndAt().toLowerCase().contains(keyword) ||
                    ta.getReason().toLowerCase().contains(keyword)) {
                filtered.add(ta);
            }
        }
        tableTamVang.setItems(filtered);
    }

    @FXML
    private void onRefresh() {
        txtSearch.clear();
        loadTemporaryAbsents();
    }

    private void loadTemporaryAbsents() {
        temporaryAbsentList.clear();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM temporary_absent";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TemporaryAbsent ta = new TemporaryAbsent(
                        rs.getInt("temporary_absent_id"),
                        rs.getString("absent_location"),
                        rs.getString("start_at"),
                        rs.getString("end_at"),
                        rs.getInt("resident_id"),
                        rs.getString("reason")
                );
                temporaryAbsentList.add(ta);
            }
            tableTamVang.setItems(temporaryAbsentList);
        } catch (Exception e) {
            showAlert("Lỗi khi tải dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // Các phương thức chuyển trang
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
    @FXML
    private void handleCanHoClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/CanHo.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) lblCanHo.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Quản lý Căn hộ");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

    @FXML
    private void handleCuDanClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/CuDan.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) lblCuDan.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Quản lý Cư dân");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

    @FXML
    private void handleChuHoClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/ChuHo.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) lblChuHo.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Quản lý Chủ hộ");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

    @FXML
    private void handleKhoanthuClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/KhoanThu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) lblKhoanThu.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Quản lý Khoản thu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

    @FXML
    private void handleThongkelick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/ThongKe.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) lblThongke.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Thống kê");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

    @FXML
    private void handleTamTruClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/TamTru.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) lblTamTru.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Quản lý Tạm trú");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }
}