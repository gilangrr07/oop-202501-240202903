package com.upb.agripos.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model Product dengan JavaFX Property untuk binding ke TableView
 * Integrasi: Bab 2 (Encapsulation), Bab 13 (JavaFX Properties)
 */
public class Product {
    private final StringProperty code = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();
    private final IntegerProperty stock = new SimpleIntegerProperty();

    public Product(String code, String name, double price, int stock) {
        this.code.set(code);
        this.name.set(name);
        this.price.set(price);
        this.stock.set(stock);
    }

    // ===== STANDARD GETTER (untuk DAO & Service) =====
    public String getCode() { return code.get(); }
    public String getName() { return name.get(); }
    public double getPrice() { return price.get(); }
    public int getStock() { return stock.get(); }

    // ===== STANDARD SETTER =====
    public void setCode(String code) { this.code.set(code); }
    public void setName(String name) { this.name.set(name); }
    public void setPrice(double price) { this.price.set(price); }
    public void setStock(int stock) { this.stock.set(stock); }

    // ===== PROPERTY GETTER (untuk TableView binding) =====
    public StringProperty codeProperty() { return code; }
    public StringProperty nameProperty() { return name; }
    public DoubleProperty priceProperty() { return price; }
    public IntegerProperty stockProperty() { return stock; }

    public void reduceStock(int qty) {
        this.stock.set(this.stock.get() - qty);
    }

    public void addStock(int qty) {
        this.stock.set(this.stock.get() + qty);
    }
}