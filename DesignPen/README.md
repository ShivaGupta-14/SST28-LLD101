Design a Pen
============

Class Diagram
-------------
```
                  +-------------------+
                  |  Pen (abstract)   |
                  +-------------------+
                  | - brand: String   |
                  | - name: String    |
                  | - type: PenType   |
                  | # refill: Refill  |
                  | - nib: Nib        |
                  | - isOpen: boolean |
                  +-------------------+
                  | + start()         |
                  | + close()         |
                  | + write(text)     |
                  | + refill(refill)  |
                  +-------------------+
                   /       |       \
                  /        |        \
           GelPen     BallPen    FountainPen

  Pen *-- Nib       (has-a)
  Pen *-- Refill    (has-a)
  Refill *-- Ink    (has-a)

  Enums: PenType, InkColor, NibType
```

- Pen is abstract, write() and refill() are abstract since each pen type handles them differently
- BallPen cannot be refilled, GelPen and FountainPen can
- Ink tracks remaining quantity, writing uses ink based on text length
- start() and close() are in base class since they work the same for all pens

Build & Run
-----------
```
cd DesignPen/src
javac com/example/pen/*.java
java com.example.pen.App
```
