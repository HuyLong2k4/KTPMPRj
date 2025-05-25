package main.java.sample.Model;

import javafx.beans.property.*;

public class Resident {
    private IntegerProperty residentId;
    private IntegerProperty householdId;
    private StringProperty citizenId;
    private StringProperty phone;
    private StringProperty name;
    private StringProperty sex;
    private StringProperty relationship;
    private StringProperty birthday;
    private StringProperty birthPlace;
    private StringProperty job;
    private StringProperty workingAt;
    private StringProperty ethnic;
    private StringProperty religion;
    private BooleanProperty temporaryAbsent;
    private BooleanProperty temporaryResidence;

    public Resident(int residentId, int householdId, String citizenId, String phone, String name, String sex, String relationship, String birthday, String birthPlace, String job, String workingAt, String ethnic, String religion, boolean temporaryAbsent, boolean temporaryResidence) {
        this.residentId = new SimpleIntegerProperty(residentId);
        this.householdId = new SimpleIntegerProperty(householdId);
        this.citizenId = new SimpleStringProperty(citizenId);
        this.phone = new SimpleStringProperty(phone);
        this.name = new SimpleStringProperty(name);
        this.sex = new SimpleStringProperty(sex);
        this.relationship = new SimpleStringProperty(relationship);
        this.birthday = new SimpleStringProperty(birthday);
        this.birthPlace = new SimpleStringProperty(birthPlace);
        this.job = new SimpleStringProperty(job);
        this.workingAt = new SimpleStringProperty(workingAt);
        this.ethnic = new SimpleStringProperty(ethnic);
        this.religion = new SimpleStringProperty(religion);
        this.temporaryAbsent = new SimpleBooleanProperty(temporaryAbsent);
        this.temporaryResidence = new SimpleBooleanProperty(temporaryResidence);
    }
}
