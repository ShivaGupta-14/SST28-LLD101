package com.example.pen;

public class Nib {
    private NibType type;
    private double thickness;

    public Nib(NibType type, double thickness) {
        this.type = type;
        this.thickness = thickness;
    }

    public NibType getType() {
        return type;
    }

    public double getThickness() {
        return thickness;
    }
}
