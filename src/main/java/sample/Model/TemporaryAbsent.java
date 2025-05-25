package main.java.sample.Model;

import javafx.beans.property.*;

public class TemporaryAbsent {
    private IntegerProperty temporaryAbsentId;
    private StringProperty absentLocation;
    private StringProperty startAt;
    private StringProperty endAt;
    private IntegerProperty residentId;
    private StringProperty reason;

    public TemporaryAbsent(int temporaryAbsentId, String absentLocation, String startAt, String endAt, int residentId, String reason) {
        this.temporaryAbsentId = new SimpleIntegerProperty(temporaryAbsentId);
        this.absentLocation = new SimpleStringProperty(absentLocation);
        this.startAt = new SimpleStringProperty(startAt);
        this.endAt = new SimpleStringProperty(endAt);
        this.residentId = new SimpleIntegerProperty(residentId);
        this.reason = new SimpleStringProperty(reason);
    }
}
