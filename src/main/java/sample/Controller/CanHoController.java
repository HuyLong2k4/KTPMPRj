package main.java.sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.sample.Model.Household;
import database.DatabaseConnection;

import javafx.geometry.Insets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CanHoController {

    @FXML
    private TextField txtGlobalSearch;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<Household> tableApartments;

    @FXML
    private TableColumn<Household, Number> colId;

    @FXML
    private TableColumn<Household, String> colRoom;

    @FXML
    private TableColumn<Household, Number> colArea;

    @FXML
    private TableColumn<Household, Number> colOwner;

    private ObservableList<Household> apartmentList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Household, Void> colAction;

    @FXML
    private Label lblKhoanThu;

    @FXML
    private Label lblCuDan;

    @FXML
    private Label lblThongke;

    @FXML
    private Label lblTrangChu;


    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData -> cellData.getValue().householdIdProperty());
        colRoom.setCellValueFactory(cellData -> cellData.getValue().roomNumberProperty());
        colArea.setCellValueFactory(cellData -> cellData.getValue().areaProperty());
        colOwner.setCellValueFactory(cellData -> cellData.getValue().ownedByProperty());
        addActionButtonsToTable();
        loadApartments();
    }

    private void addActionButtonsToTable() {
        colAction.setCellFactory(col -> new TableCell<>() {
            private final Button btnDelete = new Button("Xóa");
            private final Button btnEdit = new Button("Sửa");
            private final HBox hbox = new HBox(5, btnEdit, btnDelete);

            {
                btnDelete.setOnAction(e -> {
                    Household h = getTableView().getItems().get(getIndex());
                    deleteHousehold(h);
                });

                btnEdit.setOnAction(e -> {
                    Household h = getTableView().getItems().get(getIndex());
                    editHousehold(h);
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

    // Trong CanHoController.java

    @FXML
    private void editHousehold(Household h) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Sửa thông tin căn hộ");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TextField roomField = new TextField(h.getRoomNumber());
        TextField areaField = new TextField(String.valueOf(h.getArea()));
        TextField ownerField = new TextField(String.valueOf(h.getOwnedBy()));

        vbox.getChildren().addAll(new Label("Số căn hộ:"), roomField,
                new Label("Diện tích:"), areaField,
                new Label("ID chủ hộ:"), ownerField);

        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String room = roomField.getText().trim();
                String areaStr = areaField.getText().trim();
                String ownerStr = ownerField.getText().trim();

                try {
                    double area = Double.parseDouble(areaStr);
                    int owner = Integer.parseInt(ownerStr);

                    try (Connection conn = DatabaseConnection.getConnection()) {
                        String sql = "UPDATE household SET room_number = ?, area = ?, owned_by = ? WHERE household_id = ?";
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setString(1, room);
                        ps.setDouble(2, area);
                        ps.setInt(3, owner);
                        ps.setInt(4, h.getHouseholdId());

                        if (ps.executeUpdate() > 0) {
                            h.setRoomNumber(room);
                            h.setArea(area);
                            h.setOwnedBy(owner);
                            tableApartments.refresh();
                            showAlert(" Đã cập nhật căn hộ.");
                        }
                    }
                } catch (Exception e) {
                    showAlert("Lỗi khi cập nhật: " + e.getMessage());
                }
            }
        });
    }


    private void deleteHousehold(Household h) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM household WHERE household_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, h.getHouseholdId());
            if (ps.executeUpdate() > 0) {
                apartmentList.remove(h);
                tableApartments.refresh();
                showAlert("Đã xóa căn hộ: " + h.getRoomNumber());
            }
        } catch (Exception e) {
            showAlert("Lỗi khi xóa: " + e.getMessage());
        }
    }


    public void loadApartments() {
        apartmentList.clear();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM household";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Household h = new Household(
                        rs.getInt("household_id"),
                        rs.getString("room_number"),
                        rs.getDouble("area"),
                        rs.getInt("owned_by")
                );
                apartmentList.add(h);
            }
            tableApartments.setItems(apartmentList);
        } catch (Exception e) {
            showAlert("Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    @FXML
    public void onSearch() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            tableApartments.setItems(apartmentList);
            return;
        }

        ObservableList<Household> filtered = FXCollections.observableArrayList();
        for (Household h : apartmentList) {
            if (h.getRoomNumber().toLowerCase().contains(keyword)) {
                filtered.add(h);
            }
        }
        tableApartments.setItems(filtered);
    }

    @FXML
    public void onRefresh() {
        txtSearch.clear();
        loadApartments();
    }

    @FXML
    public void onAdd() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Thêm căn hộ mới");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TextField roomField = new TextField();
        roomField.setPromptText("Số căn hộ");

        TextField areaField = new TextField();
        areaField.setPromptText("Diện tích (m²)");

        TextField ownerField = new TextField();
        ownerField.setPromptText("ID chủ hộ (optional)");

        vbox.getChildren().addAll(new Label("Số căn hộ:"), roomField,
                new Label("Diện tích:"), areaField,
                new Label("ID chủ hộ:"), ownerField);

        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String room = roomField.getText().trim();
                String areaStr = areaField.getText().trim();
                String ownerStr = ownerField.getText().trim();

                if (room.isEmpty() || areaStr.isEmpty()) {
                    showAlert("Vui lòng nhập số căn hộ và diện tích.");
                    return;
                }

                try {
                    double area = Double.parseDouble(areaStr);
                    Integer owner = ownerStr.isEmpty() ? null : Integer.parseInt(ownerStr);

                    try (Connection conn = DatabaseConnection.getConnection()) {
                        String sql = "INSERT INTO household (room_number, area, owned_by) VALUES (?, ?, ?)";
                        PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                        ps.setString(1, room);
                        ps.setDouble(2, area);
                        if (owner != null) {
                            ps.setInt(3, owner);
                        } else {
                            ps.setNull(3, java.sql.Types.INTEGER);
                        }

                        int affected = ps.executeUpdate();
                        if (affected > 0) {
                            ResultSet rs = ps.getGeneratedKeys();
                            if (rs.next()) {
                                int newId = rs.getInt(1);
                                Household newHousehold = new Household(newId, room, area, owner != null ? owner : 0);
                                apartmentList.add(newHousehold);
                                tableApartments.setItems(apartmentList);
                                showAlert("Thêm căn hộ thành công!");
                            }
                        } else {
                            showAlert("Không thể thêm căn hộ.");
                        }
                    }
                } catch (NumberFormatException e) {
                    showAlert("⚠ Diện tích và ID chủ hộ phải là số.");
                } catch (Exception e) {
                    showAlert("Lỗi: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
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

}

