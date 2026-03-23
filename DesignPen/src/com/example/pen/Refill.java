package com.example.pen;

public class Refill {
    private Ink ink;

    public Refill(Ink ink) {
        this.ink = ink;
    }

    public Ink getInk() {
        return ink;
    }

    public boolean canWrite() {
        return ink != null && ink.hasInk();
    }
}
