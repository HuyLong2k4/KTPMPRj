package main.java.sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.sample.Model.Household;
import database.DatabaseConnection;

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
        showAlert("Chức năng Thêm mới đang được phát triển.");
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

