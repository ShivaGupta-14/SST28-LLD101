package com.example.pen;

public abstract class Pen {
    private String brand;
    private String name;
    private PenType type;
    protected Refill refill;
    private Nib nib;
    private boolean isOpen;

    public Pen(String brand, String name, PenType type, Refill refill, Nib nib) {
        this.brand = brand;
        this.name = name;
        this.type = type;
        this.refill = refill;
        this.nib = nib;
        this.isOpen = false;
    }

    public void start() {
        if (isOpen) {
            System.out.println(name + " is already started.");
            return;
        }
        isOpen = true;
        System.out.println(name + " is now ready to write.");
    }

    public void close() {
        if (!isOpen) {
            System.out.println(name + " is already closed.");
            return;
        }
        isOpen = false;
        System.out.println(name + " is now closed.");
    }

    public abstract void write(String text);

    public abstract void refill(Refill newRefill);

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public PenType getType() {
        return type;
    }

    public Nib getNib() {
        return nib;
    }

    public boolean isStarted() {
        return isOpen;
    }

    public Refill getRefill() {
        return refill;
    }
}
