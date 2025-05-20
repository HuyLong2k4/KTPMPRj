module KTPMProject {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;
    exports main.java.sample.event;

    opens main.java.sample.event to javafx.fxml;
    opens main.java.sample;
}