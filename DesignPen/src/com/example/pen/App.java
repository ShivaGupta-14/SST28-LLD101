package com.example.pen;

public class App {
    public static void main(String[] args) {

        Ink blueInk = new Ink(InkColor.BLUE, 100);
        Ink blackInk = new Ink(InkColor.BLACK, 100);

        Nib thinNib = new Nib(NibType.THIN, 0.5);
        Nib mediumNib = new Nib(NibType.MEDIUM, 0.7);

        Refill gelRefill = new Refill(blueInk);
        Refill ballRefill = new Refill(blackInk);
        Refill fountainRefill = new Refill(new Ink(InkColor.BLUE, 50));

        Pen gelPen = new GelPen("Pilot", "G2", gelRefill, thinNib);
        Pen ballPen = new BallPen("Reynolds", "045", ballRefill, mediumNib);
        Pen fountainPen = new FountainPen("Parker", "Vector", fountainRefill, mediumNib);

        System.out.println("=== Gel Pen ===");
        gelPen.write("Hello");
        gelPen.start();
        gelPen.write("Hello World");
        gelPen.close();

        System.out.println();
        System.out.println("=== Ball Pen ===");
        ballPen.start();
        ballPen.write("Testing ball pen");
        ballPen.refill(new Refill(new Ink(InkColor.RED, 100)));
        ballPen.close();

        System.out.println();
        System.out.println("=== Fountain Pen ===");
        fountainPen.start();
        fountainPen.write("Elegant writing");
        fountainPen.refill(new Refill(new Ink(InkColor.BLACK, 80)));
        fountainPen.write("With new ink");
        fountainPen.close();

        System.out.println();
        System.out.println("=== Refill Demo ===");
        Ink lowInk = new Ink(InkColor.RED, 0.5);
        Refill lowRefill = new Refill(lowInk);
        Pen testPen = new GelPen("Test", "LowInk", lowRefill, thinNib);
        testPen.start();
        testPen.write("This will use up ink");
        testPen.write("Trying again after ink ran out");
        testPen.refill(new Refill(new Ink(InkColor.GREEN, 100)));
        testPen.write("Writing after refill");
        testPen.close();
    }
}
