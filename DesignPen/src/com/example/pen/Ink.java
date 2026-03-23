package com.example.pen;

public class Ink {
    private InkColor color;
    private double quantity;

    public Ink(InkColor color, double quantity) {
        this.color = color;
        this.quantity = quantity;
    }

    public InkColor getColor() {
        return color;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public boolean hasInk() {
        return quantity > 0;
    }

    public void use(double amount) {
        this.quantity = Math.max(0, this.quantity - amount);
    }
}
