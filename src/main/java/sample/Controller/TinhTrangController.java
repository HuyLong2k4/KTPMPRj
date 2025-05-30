package main.java.sample.Controller;

import database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import main.java.sample.Model.TamTruTamVang;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class TinhTrangController {

    @FXML private TableView<TamTruTamVang> tableTamTruTamVang;
    @FXML private TableColumn<TamTruTamVang, Integer> colId;
    @FXML private TableColumn<TamTruTamVang, String> colHoTenNhanKhau;
    @FXML private TableColumn<TamTruTamVang, String> colTrangThai;
    @FXML private TableColumn<TamTruTamVang, String> colDiaChi;
    @FXML private TableColumn<TamTruTamVang, String> colThoiGian;
    @FXML private TableColumn<TamTruTamVang, String> colNoiDung;
    @FXML private TableColumn<TamTruTamVang, Void> colAction;

    @FXML private TextField txtSearch;

    private ObservableList<TamTruTamVang> dataList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colHoTenNhanKhau.setCellValueFactory(new PropertyValueFactory<>("hoTenNhanKhau"));
        colTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        colDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChiTamTruTamVang"));
        colThoiGian.setCellValueFactory(new PropertyValueFactory<>("thoiGian"));
        colNoiDung.setCellValueFactory(new PropertyValueFactory<>("noiDungDeNghi"));

        addActionButtons();

        loadData();
    }

    private void loadData() {
        dataList.clear();
        String sql = "SELECT id, hoTenNhanKhau, trangThai, diaChiTamTruTamVang, thoiGian, noiDungDeNghi FROM tamtrutamvang";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TamTruTamVang item = new TamTruTamVang();
                item.setId(rs.getInt("id"));
                int nhanKhauId = rs.getInt("nhankhau_id");
                item.setNhanKhauId(nhanKhauId);

                String hoTen = getHoTenNhanKhauById(conn, nhanKhauId);
                item.setHoTenNhanKhau(hoTen != null ? hoTen : "Khong xac dinh");
                item.setTrangThai(rs.getString("trangThai"));
                item.setDiaChiTamTruTamVang(rs.getString("diaChiTamTruTamVang"));
                Date sqlDate = rs.getDate("thoiGian");
                if (sqlDate != null) {
                    item.setThoiGian(sqlDate);
                } else {
                    item.setThoiGian(null);
                }
                item.setNoiDungDeNghi(rs.getString("noiDungDeNghi"));
                dataList.add(item);
            }
            tableTamTruTamVang.setItems(dataList);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi tải dữ liệu", "Không thể tải dữ liệu.");
        }
    }

    private String getHoTenNhanKhauById(Connection conn, int nhanKhauId) {
        String hoTen = null;
        String sql = "SELECT hoten FROM nhankhau WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nhanKhauId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    hoTen = rs.getString("hoten");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoTen;
    }

    @FXML
    private void onSearch() {
        String keyword = txtSearch.getText().toLowerCase().trim();
        if (keyword.isEmpty()) {
            tableTamTruTamVang.setItems(dataList);
            return;
        }
        ObservableList<TamTruTamVang> filtered = FXCollections.observableArrayList();
        for (TamTruTamVang item : dataList) {
            if (item.getHoTenNhanKhau().toLowerCase().contains(keyword)) {
                filtered.add(item);
            }
        }
        tableTamTruTamVang.setItems(filtered);
    }

    @FXML
    private void onRefresh() {
        txtSearch.clear();
        loadData();
    }

    @FXML
    private void onAdd() {
        TamTruTamVang newItem = showEditDialog(null);
        if (newItem != null) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO tamtrutamvang (hoTenNhanKhau, trangThai, diaChiTamTruTamVang, thoiGian, noiDungDeNghi) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, newItem.getHoTenNhanKhau());
                ps.setString(2, newItem.getTrangThai());
                ps.setString(3, newItem.getDiaChiTamTruTamVang());
                ps.setDate(4, newItem.getThoiGian());
                ps.setString(5, newItem.getNoiDungDeNghi());
                ps.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Thêm mới", "Đã thêm thành công.");
                loadData();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi thêm mới", e.getMessage());
            }
        }
    }

    private void addActionButtons() {
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button btnEdit = new Button("Sửa");
            private final Button btnDelete = new Button("Xóa");
            private final HBox pane = new HBox(10, btnEdit, btnDelete);

            {
                btnEdit.setOnAction(event -> {
                    TamTruTamVang item = getTableView().getItems().get(getIndex());
                    TamTruTamVang updated = showEditDialog(item);
                    if (updated != null) {
                        updateItemInDatabase(updated);
                        loadData();
                    }
                });
                btnDelete.setOnAction(event -> {
                    TamTruTamVang item = getTableView().getItems().get(getIndex());
                    deleteItemInDatabase(item);
                    loadData();
                });
            }

            @Override
            protected void updateItem(Void t, boolean empty) {
                super.updateItem(t, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }

    private TamTruTamVang showEditDialog(TamTruTamVang item) {
        Dialog<TamTruTamVang> dialog = new Dialog<>();
        dialog.setTitle(item == null ? "Thêm mới" : "Sửa thông tin");
        dialog.setHeaderText(item == null ? "Nhập thông tin mới" : "Cập nhật thông tin");

        ButtonType btnOk = new ButtonType("Lưu", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnOk, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField hoTenField = new TextField();
        TextField trangThaiField = new TextField();
        TextField diaChiField = new TextField();
        TextField thoiGianField = new TextField();
        TextField noiDungField = new TextField();

        if (item != null) {
            hoTenField.setText(item.getHoTenNhanKhau());
            trangThaiField.setText(item.getTrangThai());
            diaChiField.setText(item.getDiaChiTamTruTamVang());
            if (item.getThoiGian() != null) {
                thoiGianField.setText(item.getThoiGian().toString()); // chuyển Date thành String
            } else {
                thoiGianField.setText("");
            }

            noiDungField.setText(item.getNoiDungDeNghi());
        }

        grid.add(new Label("Họ Tên:"), 0, 0);
        grid.add(hoTenField, 1, 0);
        grid.add(new Label("Trạng thái:"), 0, 1);
        grid.add(trangThaiField, 1, 1);
        grid.add(new Label("Địa chỉ:"), 0, 2);
        grid.add(diaChiField, 1, 2);
        grid.add(new Label("Thời gian:"), 0, 3);
        grid.add(thoiGianField, 1, 3);
        grid.add(new Label("Nội dung đề nghị:"), 0, 4);
        grid.add(noiDungField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {
            if (button == btnOk) {
                TamTruTamVang result = new TamTruTamVang();
                if (item != null) {
                    result.setId(item.getId());
                }
                result.setHoTenNhanKhau(hoTenField.getText().trim());
                result.setTrangThai(trangThaiField.getText().trim());
                result.setDiaChiTamTruTamVang(diaChiField.getText().trim());
                String ngayStr = thoiGianField.getText().trim();
                if (!ngayStr.isEmpty()) {
                    try {
                        LocalDate localDate = LocalDate.parse(ngayStr); // parse sang LocalDate
                        result.setThoiGian(java.sql.Date.valueOf(localDate)); // chuyển sang java.sql.Date
                    } catch (Exception e) {
                        showAlert(Alert.AlertType.ERROR, "Lỗi nhập liệu", "Ngày tháng không hợp lệ (định dạng yyyy-MM-dd)");
                        return null;
                    }
                } else {
                    result.setThoiGian(null);
                }
                result.setNoiDungDeNghi(noiDungField.getText().trim());
                return result;
            }
            return null;
        });

        Optional<TamTruTamVang> result = dialog.showAndWait();
        return result.orElse(null);
    }

    private void updateItemInDatabase(TamTruTamVang item) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE tamtrutamvang SET hoTenNhanKhau=?, trangThai=?, diaChiTamTruTamVang=?, thoiGian=?, noiDungDeNghi=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, item.getHoTenNhanKhau());
            ps.setString(2, item.getTrangThai());
            ps.setString(3, item.getDiaChiTamTruTamVang());
            ps.setDate(4, item.getThoiGian());
            ps.setString(5, item.getNoiDungDeNghi());
            ps.setInt(6, item.getId());
            ps.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Cập nhật thành công", "Thông tin đã được cập nhật.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi cập nhật", e.getMessage());
        }
    }

    private void deleteItemInDatabase(TamTruTamVang item) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xóa tạm trú/tạm vắng");
        confirm.setHeaderText(null);
        confirm.setContentText("Bạn có chắc muốn xóa thông tin của " + item.getHoTenNhanKhau() + "?");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try (Connection conn = DatabaseConnection.getConnection()) {
                    String sql = "DELETE FROM tamtrutamvang WHERE id=?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, item.getId());
                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        showAlert(Alert.AlertType.INFORMATION, "Xóa thành công", "Thông tin đã được xóa.");
                        loadData();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Lỗi", "Xóa thất bại.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Lỗi xóa", e.getMessage());
                }
            }
        });
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
