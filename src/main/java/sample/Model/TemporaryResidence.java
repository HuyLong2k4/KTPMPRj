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
}
