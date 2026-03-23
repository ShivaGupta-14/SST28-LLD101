import java.time.LocalDateTime;

public class ParkingTicket {
    private String ticketId;
    private Vehicle vehicle;
    private ParkingSlot allocatedSlot;
    private LocalDateTime entryTime;

    public ParkingTicket(String ticketId, Vehicle vehicle, ParkingSlot allocatedSlot, LocalDateTime entryTime) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.allocatedSlot = allocatedSlot;
        this.entryTime = entryTime;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getAllocatedSlot() {
        return allocatedSlot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }
}
