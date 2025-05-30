package main.java.sample.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.sample.Model.Fees;
import database.DatabaseConnection;

import javafx.geometry.Insets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class KhoanThuController {

    @FXML
    private TextField txtGlobalSearch;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<Fees> tableKhoanThu;

    @FXML
    private TableColumn<Fees, Number> feesIdCol;

    @FXML
    private TableColumn<Fees, String> nameCol;

    @FXML
    private TableColumn<Fees, Number> priceCol;

    @FXML
    private TableColumn<Fees, String> descriptionCol;

    private ObservableList<Fees> feesList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Fees, Void> colAction;

    @FXML
    private Label lblKhoanThu;

    @FXML
    private Label lblCuDan;

    @FXML
    private Label lblThongke;

    @FXML
    private Label lblTrangChu;

    @FXML
    private Label lblChuHo;

    @FXML
    private Label lblTamVang;

    @FXML
    private Label lblTamChu;

    @FXML
    private Label lblCanHo;

    @FXML
    public void initialize() {
        feesIdCol.setCellValueFactory(cellData -> cellData.getValue().feesIdProperty());
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceCol.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        descriptionCol.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        addActionButtonsToTable();
        loadFeesData();
    }

    private void addActionButtonsToTable() {
        colAction.setCellFactory(col -> new TableCell<>() {
            private final Button btnDelete = new Button("Xóa");
            private final Button btnEdit = new Button("Sửa");
            private final HBox hbox = new HBox(5, btnEdit, btnDelete);

            {
                btnDelete.setOnAction(e -> {
                    Fees f = getTableView().getItems().get(getIndex());
                    deleteFee(f);
                });

                btnEdit.setOnAction(e -> {
                    Fees f = getTableView().getItems().get(getIndex());
                    editFee(f);
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
    private void editFee(Fees f) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Sửa thông tin khoản thu");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label idLabel = new Label(String.valueOf(f.getFeesId()));
        TextField nameField = new TextField(f.getName());
        TextField priceField = new TextField(String.valueOf(f.getPrice()));
        TextField descriptionField = new TextField(f.getDescription());

        // Thay đổi thứ tự: "Loại" lên trước "Giá tiền"
        vbox.getChildren().addAll(new Label("ID:"), idLabel,
                new Label("Tên khoản thu:"), nameField,
                new Label("Loại:"), descriptionField,
                new Label("Giá tiền:"), priceField);

        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String name = nameField.getText().trim();
                String priceStr = priceField.getText().trim();
                String description = descriptionField.getText().trim();

                try {
                    double price = Double.parseDouble(priceStr);

                    try (Connection conn = DatabaseConnection.getConnection()) {
                        String sql = "UPDATE fees SET name = ?, price = ?, description = ? WHERE fees_id = ?";
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setString(1, name);
                        ps.setDouble(2, price);
                        ps.setString(3, description);
                        ps.setInt(4, f.getFeesId());

                        if (ps.executeUpdate() > 0) {
                            f.setName(name);
                            f.setPrice(price);
                            f.setDescription(description);
                            tableKhoanThu.refresh();
                            showAlert("Đã cập nhật khoản thu.");
                        }
                    }
                } catch (Exception e) {
                    showAlert("Lỗi khi cập nhật: " + e.getMessage());
                }
            }
        });
    }

    private void deleteFee(Fees f) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM fees WHERE fees_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, f.getFeesId());
            if (ps.executeUpdate() > 0) {
                feesList.remove(f);
                tableKhoanThu.refresh();
                showAlert("Đã xóa khoản thu: " + f.getName());
            }
        } catch (Exception e) {
            showAlert("Lỗi khi xóa: " + e.getMessage());
        }
    }

    public void loadFeesData() {
        feesList.clear();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM fees";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Fees f = new Fees(
                        rs.getInt("fees_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description")
                );
                feesList.add(f);
            }
            tableKhoanThu.setItems(feesList);
        } catch (Exception e) {
            showAlert("Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    @FXML
    public void onSearch() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            tableKhoanThu.setItems(feesList);
            return;
        }

        ObservableList<Fees> filtered = FXCollections.observableArrayList();
        for (Fees f : feesList) {
            if (f.getName().toLowerCase().contains(keyword)) {
                filtered.add(f);
            }
        }
        tableKhoanThu.setItems(filtered);
    }

    @FXML
    public void onRefresh() {
        txtSearch.clear();
        loadFeesData();
    }

    @FXML
    public void onAdd() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Thêm khoản thu mới");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TextField idField = new TextField();
        idField.setPromptText("ID");
        TextField nameField = new TextField();
        nameField.setPromptText("Tên khoản thu");
        TextField priceField = new TextField();
        priceField.setPromptText("Giá tiền");
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Loại");

        // Thay đổi thứ tự: "Loại" lên trước "Giá tiền"
        vbox.getChildren().addAll(new Label("ID:"), idField,
                new Label("Tên khoản thu:"), nameField,
                new Label("Loại:"), descriptionField,
                new Label("Giá tiền:"), priceField);

        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String idStr = idField.getText().trim();
                String name = nameField.getText().trim();
                String priceStr = priceField.getText().trim();
                String description = descriptionField.getText().trim();

                if (idStr.isEmpty() || name.isEmpty() || priceStr.isEmpty() || description.isEmpty()) {
                    showAlert("Vui lòng nhập đầy đủ thông tin.");
                    return;
                }

                try {
                    int id = Integer.parseInt(idStr);
                    double price = Double.parseDouble(priceStr);

                    try (Connection conn = DatabaseConnection.getConnection()) {
                        String sql = "INSERT INTO fees (fees_id, name, price, description) VALUES (?, ?, ?, ?)";
                        PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                        ps.setInt(1, id);
                        ps.setString(2, name);
                        ps.setDouble(3, price);
                        ps.setString(4, description);

                        int affected = ps.executeUpdate();
                        if (affected > 0) {
                            ResultSet rs = ps.getGeneratedKeys();
                            if (rs.next()) {
                                Fees newFee = new Fees(id, name, price, description);
                                feesList.add(newFee);
                                tableKhoanThu.setItems(feesList);
                                showAlert("Thêm khoản thu thành công!");
                            }
                        } else {
                            showAlert("Không thể thêm khoản thu.");
                        }
                    }
                } catch (NumberFormatException e) {
                    showAlert("ID và Giá tiền phải là số.");
                } catch (Exception e) {
                    showAlert("Lỗi: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    //doi trang
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

    public void handleKhoanThuClick() {
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
        navigateTo("/main/java/sample/Views/TamVang.fxml", "Quản lý Tạm Vắng", lblTamVang);
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