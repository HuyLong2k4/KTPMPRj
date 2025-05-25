package main.java.sample.Model;

import javafx.beans.property.*;

public class Fees {

    private IntegerProperty feesId;
    private StringProperty name;
    private DoubleProperty price;
    private StringProperty description;

    public Fees(int feesId, String name, double price, String description) {
        this.feesId = new SimpleIntegerProperty(feesId);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.description = new SimpleStringProperty(description);
    }
}