package main.java.sample.Model;
import javafx.beans.property.*;

public class Household {

    private IntegerProperty householdId;
    private StringProperty roomNumber;
    private DoubleProperty area;
    private IntegerProperty ownedBy;

    public Household(int householdId, String roomNumber, double area, int ownedBy) {
        this.householdId = new SimpleIntegerProperty(householdId);
        this.roomNumber = new SimpleStringProperty(roomNumber);
        this.area = new SimpleDoubleProperty(area);
        this.ownedBy = new SimpleIntegerProperty(ownedBy);
    }

    public int getHouseholdId() { return householdId.get(); }
    public void setHouseholdId(int value) { householdId.set(value); }
    public IntegerProperty householdIdProperty() { return householdId; }

    public String getRoomNumber() { return roomNumber.get(); }
    public void setRoomNumber(String value) { roomNumber.set(value); }
    public StringProperty roomNumberProperty() { return roomNumber; }

    public double getArea() { return area.get(); }
    public void setArea(double value) { area.set(value); }
    public DoubleProperty areaProperty() { return area; }

    public int getOwnedBy() { return ownedBy.get(); }
    public void setOwnedBy(int value) { ownedBy.set(value); }
    public IntegerProperty ownedByProperty() { return ownedBy; }
}