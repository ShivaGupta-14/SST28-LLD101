import java.util.ArrayList;
import java.util.List;

public class Auditorium {
    private int id;
    private String name;
    private List<Seat> seats;

    public Auditorium(int id, String name) {
        this.id = id;
        this.name = name;
        this.seats = new ArrayList<>();
    }

    public void addSeat(Seat seat) {
        seats.add(seat);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
