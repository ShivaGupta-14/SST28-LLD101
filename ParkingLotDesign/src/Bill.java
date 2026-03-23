import java.time.LocalDateTime;

public class Bill {
    private ParkingTicket ticket;
    private LocalDateTime exitTime;
    private long hoursParked;
    private double totalAmount;

    public Bill(ParkingTicket ticket, LocalDateTime exitTime, long hoursParked, double totalAmount) {
        this.ticket = ticket;
        this.exitTime = exitTime;
        this.hoursParked = hoursParked;
        this.totalAmount = totalAmount;
    }

    public ParkingTicket getTicket() {
        return ticket;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public long getHoursParked() {
        return hoursParked;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
