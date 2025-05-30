package main.java.sample.Model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class TamTruTamVang {
    private IntegerProperty id;
    private IntegerProperty nhanKhauId;
    private StringProperty trangThai;
    private StringProperty diaChiTamTruTamVang;
    private ObjectProperty<LocalDate> thoiGian;
    private StringProperty noiDungDeNghi;

    public TamTruTamVang() {
        this.id = new SimpleIntegerProperty();
        this.nhanKhauId = new SimpleIntegerProperty();
        this.trangThai = new SimpleStringProperty();
        this.diaChiTamTruTamVang = new SimpleStringProperty();
        this.thoiGian = new SimpleObjectProperty<>();
        this.noiDungDeNghi = new SimpleStringProperty();
    }

    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public int getNhanKhauId() { return nhanKhauId.get(); }
    public void setNhanKhauId(int nhanKhauId) { this.nhanKhauId.set(nhanKhauId); }
    public IntegerProperty nhanKhauIdProperty() { return nhanKhauId; }

    public String getTrangThai() { return trangThai.get(); }
    public void setTrangThai(String trangThai) { this.trangThai.set(trangThai); }
    public StringProperty trangThaiProperty() { return trangThai; }

    public String getDiaChiTamTruTamVang() { return diaChiTamTruTamVang.get(); }
    public void setDiaChiTamTruTamVang(String diaChiTamTruTamVang) { this.diaChiTamTruTamVang.set(diaChiTamTruTamVang); }
    public StringProperty diaChiTamTruTamVangProperty() { return diaChiTamTruTamVang; }

    public LocalDate getThoiGian() { return thoiGian.get(); }
    public void setThoiGian(LocalDate thoiGian) { this.thoiGian.set(thoiGian); }
    public ObjectProperty<LocalDate> thoiGianProperty() { return thoiGian; }

    public String getNoiDungDeNghi() { return noiDungDeNghi.get(); }
    public void setNoiDungDeNghi(String noiDungDeNghi) { this.noiDungDeNghi.set(noiDungDeNghi); }
    public StringProperty noiDungDeNghiProperty() { return noiDungDeNghi; }
}

