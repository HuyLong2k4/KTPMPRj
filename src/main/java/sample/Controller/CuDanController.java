package main.java.sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.util.StringConverter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Pos;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.DefaultStringConverter;

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
import main.java.sample.Model.Household;
import database.DatabaseConnection;

import javafx.geometry.Insets;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import main.java.sample.Model.Resident;



public class CuDanController {

    @FXML
    private TextField txtGlobalSearch;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<Resident> tableResidents;
    //cac column
    @FXML
    private TableColumn<Resident, Number> colID;

    @FXML
    private TableColumn<Resident, Number> colMaHo;

    @FXML
    private TableColumn<Resident, String> colCMND;

    @FXML
    private TableColumn<Resident, String> colSDT;

    @FXML
    private TableColumn<Resident, String> colHoTen;

    @FXML
    private TableColumn<Resident, String> colGioiTinh;

    @FXML
    private TableColumn<Resident, String> colQuanHe;

    @FXML
    private TableColumn<Resident, String> colNgaySinh;

    @FXML
    private TableColumn<Resident, String> colNoiSinh;

    @FXML
    private TableColumn<Resident, String> colNgheNghiep;

    @FXML
    private TableColumn<Resident, String> colNoiLamViec;

    @FXML
    private TableColumn<Resident, String> colDanToc;

    @FXML
    private TableColumn<Resident, String> colTonGiao;



    private ObservableList<Resident> residentList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Resident, Void> colAction;

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
    private Label lblTamVang;

    public void initialize() {
        colID.setCellValueFactory(cellData -> cellData.getValue().residentIdProperty());
        colMaHo.setCellValueFactory(cellData -> cellData.getValue().householdIdProperty());
        colCMND.setCellValueFactory(cellData -> cellData.getValue().citizenIdProperty());
        colSDT.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        colHoTen.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colGioiTinh.setCellValueFactory(cellData -> cellData.getValue().sexProperty());
        colQuanHe.setCellValueFactory(cellData -> cellData.getValue().relationshipProperty());
        colNgaySinh.setCellValueFactory(cellData -> cellData.getValue().birthdayProperty());
        colNoiSinh.setCellValueFactory(cellData -> cellData.getValue().birthPlaceProperty());
        colNgheNghiep.setCellValueFactory(cellData -> cellData.getValue().jobProperty());
        colNoiLamViec.setCellValueFactory(cellData -> cellData.getValue().workingAtProperty());
        colDanToc.setCellValueFactory(cellData -> cellData.getValue().ethnicProperty());
        colTonGiao.setCellValueFactory(cellData -> cellData.getValue().religionProperty());
        addActionButtonsToTable();
        loadResidents();
    }

    private void addActionButtonsToTable() {
        colAction.setCellFactory(col -> new TableCell<>() {
            private final Button btnDelete = new Button("Xóa");
            private final Button btnEdit = new Button("Sửa");
            private final HBox hbox = new HBox(5, btnEdit, btnDelete);

            {
                btnDelete.setOnAction(e -> {
                    Resident h = getTableView().getItems().get(getIndex());
                    deleteResident(h);
                });

                btnEdit.setOnAction(e -> {
                    Resident h = getTableView().getItems().get(getIndex());
                    editResident(h);
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
    private void editResident(Resident h) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Sửa thông tin cư dân");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TextField mahoField = new TextField(String.valueOf(h.getHouseholdId()));
        TextField cmndField = new TextField(h.getCitizenId());
        TextField sdtField = new TextField(String.valueOf(h.getPhone()));
        TextField hoTenField = new TextField(h.getName());
        TextField gioiTinhField = new TextField(h.getSex());
        TextField quanHeField = new TextField(h.getRelationship());
        TextField ngaySinhField = new TextField(h.getBirthday());
        TextField noiSinhField = new TextField(h.getBirthPlace());
        TextField ngheNghiepField = new TextField(h.getJob());
        TextField noiLamViecField = new TextField(h.getWorkingAt());
        TextField danTocField = new TextField(h.getEthnic());
        TextField tonGiaoField = new TextField(h.getReligion());


        vbox.getChildren().addAll(
                new Label("Mã hộ:"), mahoField,
                new Label("CMND:"), cmndField,
                new Label("SDT:"), sdtField,
                new Label("Ho Ten:"), hoTenField,
                new Label("Gioi Tinh:"), gioiTinhField,
                new Label("Quan hệ với chủ hộ:"), quanHeField,
                new Label("Ngày sinh:"), ngaySinhField,
                new Label("Nơi sinh:"), noiSinhField,
                new Label("Nghề nghiệp:"), ngheNghiepField,
                new Label("Nơi làm việc:"), noiLamViecField,
                new Label("Dân tộc:"), danTocField,
                new Label("Tôn giáo:"), tonGiaoField);


        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String mahoStr = mahoField.getText().trim();
                String cmnd = cmndField.getText().trim();
                String sdt = sdtField.getText().trim();
                String hoten = hoTenField.getText().trim();
                String gioiTinh = gioiTinhField.getText().trim();
                String quanhe = quanHeField.getText().trim();
                String ngaysinh = ngaySinhField.getText().trim();
                String noisinh = noiSinhField.getText().trim();
                String nghenghiep = ngheNghiepField.getText().trim();
                String noilamviec = noiLamViecField.getText().trim();
                String dantoc = danTocField.getText().trim();
                String tongiao = tonGiaoField.getText().trim();


                try {
                    int maho = Integer.parseInt(mahoStr);


                    try (Connection conn = DatabaseConnection.getConnection()) {
                        String sql = "UPDATE resident SET household_id = ?, citizen_id = ?, phone = ?, name = ?, sex = ?, relationship = ?, birthday = ?, birth_place = ?, job = ?, working_at = ?, ethnic = ?, religion = ?  WHERE resident_id = ?";
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setInt(1, maho);
                        ps.setString(2, cmnd);
                        ps.setString(3, sdt);
                        ps.setString(4,  hoten);
                        ps.setString(5,  gioiTinh);
                        ps.setString(6,  quanhe);
                        ps.setString(7,  ngaysinh);
                        ps.setString(8,  noisinh);
                        ps.setString(9,  nghenghiep);
                        ps.setString(10, noilamviec);
                        ps.setString(11, dantoc);
                        ps.setString(12, tongiao);

                        ps.setInt(13, h.getResidentId());

                        if (ps.executeUpdate() > 0) {
                            h.setHouseholdId(maho);
                            h.setCitizenId(cmnd);
                            h.setPhone(sdt);
                            h.setName(hoten);
                            h.setSex(gioiTinh);
                            h.setRelationship(quanhe);
                            h.setBirthday(ngaysinh);
                            h.setBirthPlace(noisinh);
                            h.setJob(nghenghiep);
                            h.setWorkingAt(noilamviec);
                            h.setEthnic(dantoc);
                            h.setReligion(tongiao);

                            tableResidents.refresh();
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

    private void deleteResident(Resident h) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM resident WHERE resident_id = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, h.getResidentId());
            if (ps.executeUpdate() > 0) {
                residentList.remove(h);
                tableResidents.refresh();
                showAlert("Đã xóa Resident: " + h.getResidentId());
            }
        } catch (Exception e) {
            showAlert("Lỗi khi xóa: " + e.getMessage());
        }
    }

    public void loadResidents() {
        residentList.clear();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM resident";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Resident h = new Resident(
                        rs.getInt("resident_id"),
                        rs.getInt("household_id"),
                        rs.getString("citizen_id"),
                        rs.getString("phone"),
                        rs.getString("name"),
                        rs.getString("sex"),
                        rs.getString("relationship"),
                        rs.getString("birthday"),
                        rs.getString("birth_place"),
                        rs.getString("job"),
                        rs.getString("working_at"),
                        rs.getString("ethnic"),
                        rs.getString("religion")
                );
                residentList.add(h);
            }
            tableResidents.setItems(residentList);
        } catch (Exception e) {
            showAlert("Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    @FXML
    public void onSearch() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            tableResidents.setItems(residentList);
            return;
        }

        ObservableList<Resident> filtered = FXCollections.observableArrayList();
        for (Resident h : residentList) {
            // Tìm kiếm theo nhiều tiêu chí
            if (h.getName().toLowerCase().contains(keyword) ||
                    String.valueOf(h.getHouseholdId()).contains(keyword) ||
                    h.getCitizenId().toLowerCase().contains(keyword) ||
                    h.getPhone().toLowerCase().contains(keyword)) {
                filtered.add(h);
            }
        }
        tableResidents.setItems(filtered);
    }

    @FXML
    public void onGlobalSearch() {
        String keyword = txtGlobalSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            tableResidents.setItems(residentList);
            return;
        }

        ObservableList<Resident> filtered = FXCollections.observableArrayList();
        for (Resident h : residentList) {
            // Tìm kiếm toàn diện hơn
            if (h.getName().toLowerCase().contains(keyword) ||
                    String.valueOf(h.getHouseholdId()).contains(keyword) ||
                    h.getCitizenId().toLowerCase().contains(keyword) ||
                    h.getPhone().toLowerCase().contains(keyword) ||
                    h.getBirthday().toLowerCase().contains(keyword) ||
                    h.getJob().toLowerCase().contains(keyword) ||
                    h.getEthnic().toLowerCase().contains(keyword)) {
                filtered.add(h);
            }
        }
        tableResidents.setItems(filtered);
    }

    @FXML
    public void onRefresh() {
        txtSearch.clear();
        loadResidents();
    }

    @FXML
    public void onAdd() {
        Dialog<Resident> dialog = new Dialog<>();
        dialog.setTitle("Thêm cư dân mới");
        dialog.setHeaderText("Nhập thông tin cư dân");

        // Thiết lập button types
        ButtonType addButtonType = new ButtonType("Thêm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Tạo ScrollPane chứa form
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(400);

        // Tạo GridPane để sắp xếp các trường nhập liệu
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        // Tạo các trường nhập liệu
        TextField mahoField = new TextField();
        mahoField.setPromptText("Mã hộ");

        TextField cmndField = new TextField();
        cmndField.setPromptText("Số CMND/CCCD");

        TextField sdtField = new TextField();
        sdtField.setPromptText("Số điện thoại");

        TextField hoTenField = new TextField();
        hoTenField.setPromptText("Họ và tên");

        ComboBox<String> gioiTinhCombo = new ComboBox<>();
        gioiTinhCombo.getItems().addAll("Nam", "Nữ");
        gioiTinhCombo.setValue("Nam");

        TextField quanHeField = new TextField();
        quanHeField.setPromptText("Quan hệ với chủ hộ");

        DatePicker ngaySinhPicker = new DatePicker();
        ngaySinhPicker.setPromptText("Ngày sinh");

        TextField noiSinhField = new TextField();
        noiSinhField.setPromptText("Nơi sinh");

        TextField ngheNghiepField = new TextField();
        ngheNghiepField.setPromptText("Nghề nghiệp");

        TextField noiLamViecField = new TextField();
        noiLamViecField.setPromptText("Nơi làm việc");

        TextField danTocField = new TextField();
        danTocField.setPromptText("Dân tộc");

        TextField tonGiaoField = new TextField();
        tonGiaoField.setPromptText("Tôn giáo");

        // Thêm các trường vào GridPane
        int row = 0;
        grid.add(new Label("Mã hộ*:"), 0, row);
        grid.add(mahoField, 1, row++);

        grid.add(new Label("CMND*:"), 0, row);
        grid.add(cmndField, 1, row++);

        grid.add(new Label("SĐT:"), 0, row);
        grid.add(sdtField, 1, row++);

        grid.add(new Label("Họ tên*:"), 0, row);
        grid.add(hoTenField, 1, row++);

        grid.add(new Label("Giới tính:"), 0, row);
        grid.add(gioiTinhCombo, 1, row++);

        grid.add(new Label("Quan hệ chủ hộ:"), 0, row);
        grid.add(quanHeField, 1, row++);

        grid.add(new Label("Ngày sinh:"), 0, row);
        grid.add(ngaySinhPicker, 1, row++);

        grid.add(new Label("Nơi sinh:"), 0, row);
        grid.add(noiSinhField, 1, row++);

        grid.add(new Label("Nghề nghiệp:"), 0, row);
        grid.add(ngheNghiepField, 1, row++);

        grid.add(new Label("Nơi làm việc:"), 0, row);
        grid.add(noiLamViecField, 1, row++);

        grid.add(new Label("Dân tộc:"), 0, row);
        grid.add(danTocField, 1, row++);

        grid.add(new Label("Tôn giáo:"), 0, row);
        grid.add(tonGiaoField, 1, row++);



        // Thêm GridPane vào ScrollPane
        scrollPane.setContent(grid);
        dialog.getDialogPane().setContent(scrollPane);
        dialog.getDialogPane().setPrefSize(600, 500);

        // Chuyển đổi kết quả thành đối tượng Resident khi nhấn nút Thêm
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                // Validate các trường bắt buộc
                if (mahoField.getText().isEmpty() || cmndField.getText().isEmpty() || hoTenField.getText().isEmpty()) {
                    showAlert("Vui lòng nhập các trường bắt buộc (đánh dấu *)");
                    return null;
                }

                try {
                    return new Resident(
                            0, // ID sẽ được tạo tự động
                            Integer.parseInt(mahoField.getText()),
                            cmndField.getText(),
                            sdtField.getText(),
                            hoTenField.getText(),
                            gioiTinhCombo.getValue(),
                            quanHeField.getText(),
                            ngaySinhPicker.getValue() != null ? ngaySinhPicker.getValue().toString() : "",
                            noiSinhField.getText(),
                            ngheNghiepField.getText(),
                            noiLamViecField.getText(),
                            danTocField.getText(),
                            tonGiaoField.getText()

                    );
                } catch (NumberFormatException e) {
                    showAlert("Mã hộ phải là số");
                    return null;
                }
            }
            return null;
        });
        // Class converter cho ComboBox boolean
        class BooleanStringConverter extends StringConverter<Boolean> {
            @Override
            public String toString(Boolean value) {
                return value ? "Có" : "Không";
            }

            @Override
            public Boolean fromString(String string) {
                return string.equals("Có");
            }
        }

        // Xử lý kết quả
        dialog.showAndWait().ifPresent(newResident -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO resident (household_id, citizen_id, phone, name, sex, relationship, " +
                        "birthday, birth_place, job, working_at, ethnic, religion) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setInt(1, newResident.getHouseholdId());
                ps.setString(2, newResident.getCitizenId());
                ps.setString(3, newResident.getPhone());
                ps.setString(4, newResident.getName());
                ps.setString(5, newResident.getSex());
                ps.setString(6, newResident.getRelationship());
                ps.setString(7, newResident.getBirthday());
                ps.setString(8, newResident.getBirthPlace());
                ps.setString(9, newResident.getJob());
                ps.setString(10, newResident.getWorkingAt());
                ps.setString(11, newResident.getEthnic());
                ps.setString(12, newResident.getReligion());


                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        newResident.setResidentId(rs.getInt(1));
                        residentList.add(newResident);
                        showAlert("Thêm cư dân thành công!");
                    }
                }
            } catch (Exception e) {
                showAlert("Lỗi khi thêm cư dân: " + e.getMessage());
                e.printStackTrace();
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

}
