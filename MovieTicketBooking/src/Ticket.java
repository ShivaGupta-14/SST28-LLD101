import java.util.List;

public class Ticket {
    private int id;
    private Show show;
    private List<Seat> bookedSeats;
    private int totalAmount;
    private BookingStatus status;
    private String paymentId;

    public Ticket(int id, Show show, List<Seat> bookedSeats, int totalAmount, String paymentId) {
        this.id = id;
        this.show = show;
        this.bookedSeats = bookedSeats;
        this.totalAmount = totalAmount;
        this.status = BookingStatus.CONFIRMED;
        this.paymentId = paymentId;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Show getShow() {
        return show;
    }

    public List<Seat> getBookedSeats() {
        return bookedSeats;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public String getPaymentId() {
        return paymentId;
    }
}
