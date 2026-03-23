package com.example.pen;

public class GelPen extends Pen {

    public GelPen(String brand, String name, Refill refill, Nib nib) {
        super(brand, name, PenType.GEL, refill, nib);
    }

    @Override
    public void write(String text) {
        if (!isStarted()) {
            System.out.println("Cannot write. Start the pen first.");
            return;
        }
        if (refill == null || !refill.canWrite()) {
            System.out.println("Cannot write. Pen is out of ink.");
            return;
        }
        System.out.println(getName() + " [Gel] writes: " + text);
        refill.getInk().use(text.length() * 0.1);
    }

    @Override
    public void refill(Refill newRefill) {
        this.refill = newRefill;
        System.out.println(getName() + " refilled successfully.");
    }
}
