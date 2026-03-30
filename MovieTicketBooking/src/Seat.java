public class Seat {
    private int id;
    private String seatNumber;
    private SeatType type;
    private int basePrice;

    public Seat(int id, String seatNumber, SeatType type, int basePrice) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.type = type;
        this.basePrice = basePrice;
    }

    public int getId() {
        return id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public SeatType getType() {
        return type;
    }

    public int getBasePrice() {
        return basePrice;
    }
}
