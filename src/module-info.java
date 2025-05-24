module KTPMProject {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;
    exports main.java.sample.Controller;

    opens main.java.sample.MainApp to javafx.fxml; // Cho phép FXML load controller
    exports main.java.sample.MainApp;
}