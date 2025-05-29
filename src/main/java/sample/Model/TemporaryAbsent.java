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

    public String getReason() {
        return reason.get();
    }

    public StringProperty reasonProperty() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason.set(reason);
    }

    public int getResidentId() {
        return residentId.get();
    }

    public IntegerProperty residentIdProperty() {
        return residentId;
    }

    public void setResidentId(int residentId) {
        this.residentId.set(residentId);
    }

    public String getEndAt() {
        return endAt.get();
    }

    public StringProperty endAtProperty() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt.set(endAt);
    }

    public String getStartAt() {
        return startAt.get();
    }

    public StringProperty startAtProperty() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt.set(startAt);
    }

    public String getAbsentLocation() {
        return absentLocation.get();
    }

    public StringProperty absentLocationProperty() {
        return absentLocation;
    }

    public void setAbsentLocation(String absentLocation) {
        this.absentLocation.set(absentLocation);
    }

    public int getTemporaryAbsentId() {
        return temporaryAbsentId.get();
    }

    public IntegerProperty temporaryAbsentIdProperty() {
        return temporaryAbsentId;
    }

    public void setTemporaryAbsentId(int temporaryAbsentId) {
        this.temporaryAbsentId.set(temporaryAbsentId);
    }
}
