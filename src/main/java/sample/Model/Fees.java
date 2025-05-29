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

    public int getFeesId() {
        return feesId.get();
    }

    public IntegerProperty feesIdProperty() {
        return feesId;
    }

    public void setFeesId(int feesId) {
        this.feesId.set(feesId);
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

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
}