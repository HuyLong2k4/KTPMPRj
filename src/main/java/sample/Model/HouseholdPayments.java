package main.java.sample.Model;

import javafx.beans.property.*;

public class HouseholdPayments {
    private LongProperty householdPaymentsId;
    private IntegerProperty householdId;
    private DoubleProperty amount;
    private StringProperty paymentDate;
    private StringProperty note;

    public HouseholdPayments(long householdPaymentsId, int householdId, double amount, String paymentDate, String note) {
        this.householdPaymentsId = new SimpleLongProperty(householdPaymentsId);
        this.householdId = new SimpleIntegerProperty(householdId);
        this.amount = new SimpleDoubleProperty(amount);
        this.paymentDate = new SimpleStringProperty(paymentDate);
        this.note = new SimpleStringProperty(note);
    }
}
