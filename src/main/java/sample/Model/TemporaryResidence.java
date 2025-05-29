package main.java.sample.Model;

import javafx.beans.property.*;

public class TemporaryResidence {
    private IntegerProperty temporaryResidenceId;
    private StringProperty startAt;
    private StringProperty endAt;
    private IntegerProperty tempResidentId;
    private StringProperty reason;

    public TemporaryResidence(int temporaryResidenceId, String startAt, String endAt, int tempResidentId, String reason) {
        this.temporaryResidenceId = new SimpleIntegerProperty(temporaryResidenceId);
        this.startAt = new SimpleStringProperty(startAt);
        this.endAt = new SimpleStringProperty(endAt);
        this.tempResidentId = new SimpleIntegerProperty(tempResidentId);
        this.reason = new SimpleStringProperty(reason);
    }

    public int getTemporaryResidenceId() {
        return temporaryResidenceId.get();
    }

    public IntegerProperty temporaryResidenceIdProperty() {
        return temporaryResidenceId;
    }

    public void setTemporaryResidenceId(int temporaryResidenceId) {
        this.temporaryResidenceId.set(temporaryResidenceId);
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

    public String getEndAt() {
        return endAt.get();
    }

    public StringProperty endAtProperty() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt.set(endAt);
    }

    public int getTempResidentId() {
        return tempResidentId.get();
    }

    public IntegerProperty tempResidentIdProperty() {
        return tempResidentId;
    }

    public void setTempResidentId(int tempResidentId) {
        this.tempResidentId.set(tempResidentId);
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
}
