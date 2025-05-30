package main.java.sample.Model;

import javafx.beans.property.*;

public class HoKhau {
    private IntegerProperty soHoKhau;
    private StringProperty soNha;
    private StringProperty duong;
    private StringProperty phuong;
    private StringProperty quan;
    private ObjectProperty<java.time.LocalDate> ngayLamHoKhau;

    public HoKhau() {
        this.soHoKhau = new SimpleIntegerProperty();
        this.soNha = new SimpleStringProperty();
        this.duong = new SimpleStringProperty();
        this.phuong = new SimpleStringProperty();
        this.quan = new SimpleStringProperty();
        this.ngayLamHoKhau = new SimpleObjectProperty<>();
    }

    public int getSoHoKhau() { return soHoKhau.get(); }
    public void setSoHoKhau(int soHoKhau) { this.soHoKhau.set(soHoKhau); }
    public IntegerProperty soHoKhauProperty() { return soHoKhau; }

    public String getSoNha() { return soNha.get(); }
    public void setSoNha(String soNha) { this.soNha.set(soNha); }
    public StringProperty soNhaProperty() { return soNha; }

    public String getDuong() { return duong.get(); }
    public void setDuong(String duong) { this.duong.set(duong); }
    public StringProperty duongProperty() { return duong; }

    public String getPhuong() { return phuong.get(); }
    public void setPhuong(String phuong) { this.phuong.set(phuong); }
    public StringProperty phuongProperty() { return phuong; }

    public String getQuan() { return quan.get(); }
    public void setQuan(String quan) { this.quan.set(quan); }
    public StringProperty quanProperty() { return quan; }

    public java.time.LocalDate getNgayLamHoKhau() { return ngayLamHoKhau.get(); }
    public void setNgayLamHoKhau(java.time.LocalDate ngayLamHoKhau) { this.ngayLamHoKhau.set(ngayLamHoKhau); }
    public ObjectProperty<java.time.LocalDate> ngayLamHoKhauProperty() { return ngayLamHoKhau; }
}

