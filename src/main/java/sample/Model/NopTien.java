package main.java.sample.Model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class NopTien {
    private IntegerProperty id;
    private IntegerProperty hoKhauId;
    private IntegerProperty khoanThuId;
    private ObjectProperty<LocalDate> ngayNop;
    private StringProperty nguoiNop;
    private DoubleProperty soTien;

    public NopTien() {
        this.id = new SimpleIntegerProperty();
        this.hoKhauId = new SimpleIntegerProperty();
        this.khoanThuId = new SimpleIntegerProperty();
        this.ngayNop = new SimpleObjectProperty<>();
        this.nguoiNop = new SimpleStringProperty();
        this.soTien = new SimpleDoubleProperty();
    }

    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public int getHoKhauId() { return hoKhauId.get(); }
    public void setHoKhauId(int hoKhauId) { this.hoKhauId.set(hoKhauId); }
    public IntegerProperty hoKhauIdProperty() { return hoKhauId; }

    public int getKhoanThuId() { return khoanThuId.get(); }
    public void setKhoanThuId(int khoanThuId) { this.khoanThuId.set(khoanThuId); }
    public IntegerProperty khoanThuIdProperty() { return khoanThuId; }

    public LocalDate getNgayNop() { return ngayNop.get(); }
    public void setNgayNop(LocalDate ngayNop) { this.ngayNop.set(ngayNop); }
    public ObjectProperty<LocalDate> ngayNopProperty() { return ngayNop; }

    public String getNguoiNop() { return nguoiNop.get(); }
    public void setNguoiNop(String nguoiNop) { this.nguoiNop.set(nguoiNop); }
    public StringProperty nguoiNopProperty() { return nguoiNop; }

    public double getSoTien() { return soTien.get(); }
    public void setSoTien(double soTien) { this.soTien.set(soTien); }
    public DoubleProperty soTienProperty() { return soTien; }
}

