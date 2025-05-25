package main.java.sample.Model;


import javafx.beans.property.*;

public class User {
    private IntegerProperty usersId;
    private StringProperty username;
    private StringProperty passwordHash;
    private StringProperty name;
    private StringProperty role;
    private StringProperty createdAt;
    private StringProperty updatedAt;

    public User(int usersId, String username, String passwordHash, String name, String role, String createdAt, String updatedAt) {
        this.usersId = new SimpleIntegerProperty(usersId);
        this.username = new SimpleStringProperty(username);
        this.passwordHash = new SimpleStringProperty(passwordHash);
        this.name = new SimpleStringProperty(name);
        this.role = new SimpleStringProperty(role);
        this.createdAt = new SimpleStringProperty(createdAt);
        this.updatedAt = new SimpleStringProperty(updatedAt);
    }
}
