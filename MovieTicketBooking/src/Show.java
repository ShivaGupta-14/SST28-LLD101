import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Show {
    private int id;
    private Movie movie;
    private Auditorium auditorium;
    private Theater theater;
    private String startTime;
    private Map<SeatType, Integer> priceIncrement;
    private Set<Integer> bookedSeatIds;

    public Show(int id, Movie movie, Auditorium auditorium, Theater theater, String startTime) {
        this.id = id;
        this.movie = movie;
        this.auditorium = auditorium;
        this.theater = theater;
        this.startTime = startTime;
        this.priceIncrement = new HashMap<>();
        this.bookedSeatIds = new HashSet<>();
    }

    public void setPriceIncrement(SeatType type, int increment) {
        priceIncrement.put(type, increment);
    }

    public int getSeatPrice(Seat seat) {
        int price = seat.getBasePrice();
        if (priceIncrement.containsKey(seat.getType())) {
            price += priceIncrement.get(seat.getType());
        }
        return price;
    }

    public boolean isSeatAvailable(int seatId) {
        return !bookedSeatIds.contains(seatId);
    }

    public void bookSeat(int seatId) {
        bookedSeatIds.add(seatId);
    }

    public void unbookSeat(int seatId) {
        bookedSeatIds.remove(seatId);
    }

    public int getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public Theater getTheater() {
        return theater;
    }

    public String getStartTime() {
        return startTime;
    }

    public Set<Integer> getBookedSeatIds() {
        return bookedSeatIds;
    }
}
