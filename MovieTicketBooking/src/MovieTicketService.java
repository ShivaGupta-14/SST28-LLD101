import java.util.ArrayList;
import java.util.List;

public class MovieTicketService {
    private List<Movie> movies;
    private List<Theater> theaters;
    private List<Show> shows;
    private List<Ticket> tickets;
    private PaymentService paymentService;
    private int nextMovieId;
    private int nextTheaterId;
    private int nextAuditoriumId;
    private int nextSeatId;
    private int nextShowId;
    private int nextTicketId;

    public MovieTicketService() {
        this.movies = new ArrayList<>();
        this.theaters = new ArrayList<>();
        this.shows = new ArrayList<>();
        this.tickets = new ArrayList<>();
        this.paymentService = new PaymentService();
        this.nextMovieId = 1;
        this.nextTheaterId = 1;
        this.nextAuditoriumId = 1;
        this.nextSeatId = 1;
        this.nextShowId = 1;
        this.nextTicketId = 1;
    }

    public Movie addMovie(String name, int durationMinutes) {
        Movie movie = new Movie(nextMovieId++, name, durationMinutes);
        movies.add(movie);
        return movie;
    }

    public Theater addTheater(String name, String city, int numAuditoriums, int rows, int seatsPerRow) {
        Theater theater = new Theater(nextTheaterId++, name, city);

        for (int a = 1; a <= numAuditoriums; a++) {
            Auditorium aud = new Auditorium(nextAuditoriumId++, "Screen " + a);
            int platinumRows = Math.max(1, rows / 3);
            int goldRows = Math.max(1, rows / 3);

            for (int r = 0; r < rows; r++) {
                char rowChar = (char) ('A' + r);
                SeatType type;
                int basePrice;

                if (r < platinumRows) {
                    type = SeatType.PLATINUM;
                    basePrice = 300;
                } else if (r < platinumRows + goldRows) {
                    type = SeatType.GOLD;
                    basePrice = 200;
                } else {
                    type = SeatType.SILVER;
                    basePrice = 100;
                }

                for (int s = 1; s <= seatsPerRow; s++) {
                    String seatNum = "" + rowChar + s;
                    Seat seat = new Seat(nextSeatId++, seatNum, type, basePrice);
                    aud.addSeat(seat);
                }
            }

            theater.addAuditorium(aud);
        }

        theaters.add(theater);
        return theater;
    }

    public Show addShow(int movieId, int theaterId, int auditoriumId, String startTime,
                        int platinumInc, int goldInc, int silverInc) {
        Movie movie = getMovieById(movieId);
        Theater theater = getTheaterById(theaterId);

        if (movie == null || theater == null) {
            System.out.println("Movie or Theater not found.");
            return null;
        }

        Auditorium aud = null;
        for (Auditorium a : theater.getAuditoriums()) {
            if (a.getId() == auditoriumId) {
                aud = a;
                break;
            }
        }

        if (aud == null) {
            System.out.println("Auditorium not found in this theater.");
            return null;
        }

        Show show = new Show(nextShowId++, movie, aud, theater, startTime);
        show.setPriceIncrement(SeatType.PLATINUM, platinumInc);
        show.setPriceIncrement(SeatType.GOLD, goldInc);
        show.setPriceIncrement(SeatType.SILVER, silverInc);
        shows.add(show);
        return show;
    }

    public List<Theater> showTheaters(String city) {
        List<Theater> result = new ArrayList<>();
        for (Theater t : theaters) {
            if (t.getCity().equalsIgnoreCase(city)) {
                result.add(t);
            }
        }
        return result;
    }

    public List<Show> showMovies(String city) {
        List<Show> result = new ArrayList<>();
        for (Show s : shows) {
            if (s.getTheater().getCity().equalsIgnoreCase(city)) {
                result.add(s);
            }
        }
        return result;
    }

    public Ticket bookTicket(int showId, List<Integer> seatIds) {
        Show show = getShowById(showId);
        if (show == null) {
            System.out.println("Show not found.");
            return null;
        }

        List<Seat> seatsToBook = new ArrayList<>();
        int totalAmount = 0;

        for (int seatId : seatIds) {
            if (!show.isSeatAvailable(seatId)) {
                System.out.println("Seat " + seatId + " is already booked.");
                return null;
            }

            Seat seat = getSeatFromAuditorium(show.getAuditorium(), seatId);
            if (seat == null) {
                System.out.println("Seat " + seatId + " not found in this auditorium.");
                return null;
            }

            seatsToBook.add(seat);
            totalAmount += show.getSeatPrice(seat);
        }

        System.out.println("  Total amount: Rs." + totalAmount);
        String paymentId = paymentService.processPayment(totalAmount);

        for (Seat seat : seatsToBook) {
            show.bookSeat(seat.getId());
        }

        Ticket ticket = new Ticket(nextTicketId++, show, seatsToBook, totalAmount, paymentId);
        tickets.add(ticket);
        return ticket;
    }

    public void cancelTicket(int ticketId) {
        Ticket ticket = getTicketById(ticketId);
        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }

        if (ticket.getStatus() == BookingStatus.CANCELLED) {
            System.out.println("Ticket is already cancelled.");
            return;
        }

        for (Seat seat : ticket.getBookedSeats()) {
            ticket.getShow().unbookSeat(seat.getId());
        }

        ticket.setStatus(BookingStatus.CANCELLED);
        paymentService.processRefund(ticket.getPaymentId(), ticket.getTotalAmount());
        System.out.println("  Ticket " + ticketId + " cancelled successfully.");
    }

    public void printShowSeats(int showId) {
        Show show = getShowById(showId);
        if (show == null) {
            System.out.println("Show not found.");
            return;
        }

        System.out.println("Seats for Show " + showId + " (" + show.getMovie().getName()
                + " at " + show.getStartTime() + "):");
        for (Seat seat : show.getAuditorium().getSeats()) {
            String status = show.isSeatAvailable(seat.getId()) ? "AVAILABLE" : "BOOKED";
            int price = show.getSeatPrice(seat);
            System.out.println("  [" + seat.getId() + "] " + seat.getSeatNumber()
                    + " (" + seat.getType() + ") - Rs." + price + " - " + status);
        }
    }

    public void printAllMovies() {
        if (movies.isEmpty()) {
            System.out.println("No movies added yet.");
            return;
        }
        System.out.println("Movies:");
        for (Movie m : movies) {
            System.out.println("  [" + m.getId() + "] " + m.getName() + " (" + m.getDurationMinutes() + " min)");
        }
    }

    public void printAllTheaters() {
        if (theaters.isEmpty()) {
            System.out.println("No theaters added yet.");
            return;
        }
        System.out.println("Theaters:");
        for (Theater t : theaters) {
            System.out.println("  [" + t.getId() + "] " + t.getName() + " - " + t.getCity());
            for (Auditorium a : t.getAuditoriums()) {
                System.out.println("    Auditorium [" + a.getId() + "] " + a.getName()
                        + " (" + a.getSeats().size() + " seats)");
            }
        }
    }

    public void printTicket(Ticket ticket) {
        System.out.println("========================================");
        System.out.println("              TICKET");
        System.out.println("========================================");
        System.out.println("Ticket ID: " + ticket.getId());
        System.out.println("Movie: " + ticket.getShow().getMovie().getName());
        System.out.println("Theater: " + ticket.getShow().getTheater().getName());
        System.out.println("Auditorium: " + ticket.getShow().getAuditorium().getName());
        System.out.println("Show Time: " + ticket.getShow().getStartTime());
        System.out.print("Seats: ");
        for (int i = 0; i < ticket.getBookedSeats().size(); i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(ticket.getBookedSeats().get(i).getSeatNumber());
        }
        System.out.println();
        System.out.println("Total: Rs." + ticket.getTotalAmount());
        System.out.println("Status: " + ticket.getStatus());
        System.out.println("Payment ID: " + ticket.getPaymentId());
        System.out.println("========================================");
    }

    private Movie getMovieById(int id) {
        for (Movie m : movies) {
            if (m.getId() == id) return m;
        }
        return null;
    }

    private Theater getTheaterById(int id) {
        for (Theater t : theaters) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    private Show getShowById(int id) {
        for (Show s : shows) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    private Ticket getTicketById(int id) {
        for (Ticket t : tickets) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    private Seat getSeatFromAuditorium(Auditorium aud, int seatId) {
        for (Seat s : aud.getSeats()) {
            if (s.getId() == seatId) return s;
        }
        return null;
    }
}
