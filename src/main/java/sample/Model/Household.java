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
}