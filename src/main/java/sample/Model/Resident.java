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

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
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

    public int getHouseholdId() {
        return householdId.get();
    }

    public IntegerProperty householdIdProperty() {
        return householdId;
    }

    public void setHouseholdId(int householdId) {
        this.householdId.set(householdId);
    }

    public String getCitizenId() {
        return citizenId.get();
    }

    public StringProperty citizenIdProperty() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId.set(citizenId);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSex() {
        return sex.get();
    }

    public StringProperty sexProperty() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex.set(sex);
    }

    public String getRelationship() {
        return relationship.get();
    }

    public StringProperty relationshipProperty() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship.set(relationship);
    }

    public String getBirthday() {
        return birthday.get();
    }

    public StringProperty birthdayProperty() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }

    public String getBirthPlace() {
        return birthPlace.get();
    }

    public StringProperty birthPlaceProperty() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace.set(birthPlace);
    }

    public String getJob() {
        return job.get();
    }

    public StringProperty jobProperty() {
        return job;
    }

    public void setJob(String job) {
        this.job.set(job);
    }

    public String getWorkingAt() {
        return workingAt.get();
    }

    public StringProperty workingAtProperty() {
        return workingAt;
    }

    public void setWorkingAt(String workingAt) {
        this.workingAt.set(workingAt);
    }

    public String getEthnic() {
        return ethnic.get();
    }

    public StringProperty ethnicProperty() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic.set(ethnic);
    }

    public String getReligion() {
        return religion.get();
    }

    public StringProperty religionProperty() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion.set(religion);
    }

    public boolean isTemporaryAbsent() {
        return temporaryAbsent.get();
    }

    public BooleanProperty temporaryAbsentProperty() {
        return temporaryAbsent;
    }

    public void setTemporaryAbsent(boolean temporaryAbsent) {
        this.temporaryAbsent.set(temporaryAbsent);
    }

    public boolean isTemporaryResidence() {
        return temporaryResidence.get();
    }

    public BooleanProperty temporaryResidenceProperty() {
        return temporaryResidence;
    }

    public void setTemporaryResidence(boolean temporaryResidence) {
        this.temporaryResidence.set(temporaryResidence);
    }


}
