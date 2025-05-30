package main.java.sample.Model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class KhoanThu {
    private IntegerProperty id;
    private ObjectProperty<LocalDate> ngayTao;
    private ObjectProperty<LocalDate> thoiHan;
    private StringProperty tenKhoanThu;
    private BooleanProperty batBuoc;
    private StringProperty ghiChu;

    public KhoanThu() {
        this.id = new SimpleIntegerProperty();
        this.ngayTao = new SimpleObjectProperty<>();
        this.thoiHan = new SimpleObjectProperty<>();
        this.tenKhoanThu = new SimpleStringProperty();
        this.batBuoc = new SimpleBooleanProperty();
        this.ghiChu = new SimpleStringProperty();
    }

    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public LocalDate getNgayTao() { return ngayTao.get(); }
    public void setNgayTao(LocalDate ngayTao) { this.ngayTao.set(ngayTao); }
    public ObjectProperty<LocalDate> ngayTaoProperty() { return ngayTao; }

    public LocalDate getThoiHan() { return thoiHan.get(); }
    public void setThoiHan(LocalDate thoiHan) { this.thoiHan.set(thoiHan); }
    public ObjectProperty<LocalDate> thoiHanProperty() { return thoiHan; }

    public String getTenKhoanThu() { return tenKhoanThu.get(); }
    public void setTenKhoanThu(String tenKhoanThu) { this.tenKhoanThu.set(tenKhoanThu); }
    public StringProperty tenKhoanThuProperty() { return tenKhoanThu; }

    public boolean isBatBuoc() { return batBuoc.get(); }
    public void setBatBuoc(boolean batBuoc) { this.batBuoc.set(batBuoc); }
    public BooleanProperty batBuocProperty() { return batBuoc; }

    public String getGhiChu() { return ghiChu.get(); }
    public void setGhiChu(String ghiChu) { this.ghiChu.set(ghiChu); }
    public StringProperty ghiChuProperty() { return ghiChu; }
}
