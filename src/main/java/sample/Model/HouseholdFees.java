package main.java.sample.Model;

import javafx.beans.property.*;
public class HouseholdFees {
    private IntegerProperty householdFeesId;
    private IntegerProperty householdId;
    private IntegerProperty feesId;
    private DoubleProperty amount;
    private StringProperty month;

    public HouseholdFees(int householdFeesId, int householdId, int feesId, double amount, String month) {
        this.householdFeesId = new SimpleIntegerProperty(householdFeesId);
        this.householdId = new SimpleIntegerProperty(householdId);
        this.feesId = new SimpleIntegerProperty(feesId);
        this.amount = new SimpleDoubleProperty(amount);
        this.month = new SimpleStringProperty(month);
    }
}
