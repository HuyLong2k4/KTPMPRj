module KTPMProject {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;
    exports main.java.sample.backend;

    opens main.java.sample.backend.controller to javafx.fxml; // Cho phép FXML load controller
    exports main.java.sample.backend.controller;
}