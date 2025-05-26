package main.java.sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
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
    public void initialize() {
        colId.setCellValueFactory(cellData -> cellData.getValue().householdIdProperty());
        colRoom.setCellValueFactory(cellData -> cellData.getValue().roomNumberProperty());
        colArea.setCellValueFactory(cellData -> cellData.getValue().areaProperty());
        colOwner.setCellValueFactory(cellData -> cellData.getValue().ownedByProperty());

        loadApartments();
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
                                showAlert("✅ Thêm căn hộ thành công!");
                            }
                        } else {
                            showAlert("❌ Không thể thêm căn hộ.");
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
}

