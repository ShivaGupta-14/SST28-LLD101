import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MovieTicketService service = new MovieTicketService();

        System.out.println("========================================");
        System.out.println("    MOVIE TICKET BOOKING SYSTEM");
        System.out.println("========================================");

        boolean running = true;
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Customer");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Login as: ");

            int role = sc.nextInt();
            sc.nextLine();
            System.out.println();

            if (role == 1) {
                customerMenu(sc, service);
            } else if (role == 2) {
                adminMenu(sc, service);
            } else if (role == 3) {
                running = false;
                System.out.println("Exiting...");
            } else {
                System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }

    private static void customerMenu(Scanner sc, MovieTicketService service) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- CUSTOMER MENU ---");
            System.out.println("1. Show theaters in a city");
            System.out.println("2. Show movies/shows in a city");
            System.out.println("3. Book ticket");
            System.out.println("4. Cancel ticket");
            System.out.println("5. Back");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    System.out.print("Enter city: ");
                    String city = sc.nextLine().trim();
                    List<Theater> theaters = service.showTheaters(city);
                    if (theaters.isEmpty()) {
                        System.out.println("No theaters found in " + city);
                    } else {
                        System.out.println("Theaters in " + city + ":");
                        for (Theater t : theaters) {
                            System.out.println("  [" + t.getId() + "] " + t.getName());
                            for (Auditorium a : t.getAuditoriums()) {
                                System.out.println("    " + a.getName() + " (" + a.getSeats().size() + " seats)");
                            }
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter city: ");
                    String city2 = sc.nextLine().trim();
                    List<Show> shows = service.showMovies(city2);
                    if (shows.isEmpty()) {
                        System.out.println("No shows found in " + city2);
                    } else {
                        System.out.println("Shows in " + city2 + ":");
                        for (Show s : shows) {
                            System.out.println("  [Show " + s.getId() + "] " + s.getMovie().getName()
                                    + " at " + s.getTheater().getName()
                                    + " (" + s.getAuditorium().getName() + ")"
                                    + " - " + s.getStartTime());
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter show id: ");
                    int showId = sc.nextInt();
                    sc.nextLine();
                    service.printShowSeats(showId);
                    System.out.print("Enter seat ids (comma separated): ");
                    String seatInput = sc.nextLine().trim();
                    String[] parts = seatInput.split(",");
                    List<Integer> seatIds = new ArrayList<>();
                    for (String p : parts) {
                        seatIds.add(Integer.parseInt(p.trim()));
                    }
                    Ticket ticket = service.bookTicket(showId, seatIds);
                    if (ticket != null) {
                        service.printTicket(ticket);
                    }
                    break;

                case 4:
                    System.out.print("Enter ticket id: ");
                    int ticketId = sc.nextInt();
                    sc.nextLine();
                    service.cancelTicket(ticketId);
                    break;

                case 5:
                    inMenu = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void adminMenu(Scanner sc, MovieTicketService service) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. Add movie");
            System.out.println("2. Add theater");
            System.out.println("3. Add show");
            System.out.println("4. View all movies");
            System.out.println("5. View all theaters");
            System.out.println("6. Back");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    System.out.print("Enter movie name: ");
                    String mName = sc.nextLine().trim();
                    System.out.print("Enter duration (minutes): ");
                    int dur = sc.nextInt();
                    sc.nextLine();
                    Movie m = service.addMovie(mName, dur);
                    System.out.println("Movie added with ID: " + m.getId());
                    break;

                case 2:
                    System.out.print("Enter theater name: ");
                    String tName = sc.nextLine().trim();
                    System.out.print("Enter city: ");
                    String tCity = sc.nextLine().trim();
                    System.out.print("Enter number of auditoriums: ");
                    int numAud = sc.nextInt();
                    System.out.print("Enter rows per auditorium: ");
                    int rows = sc.nextInt();
                    System.out.print("Enter seats per row: ");
                    int seatsPerRow = sc.nextInt();
                    sc.nextLine();
                    Theater t = service.addTheater(tName, tCity, numAud, rows, seatsPerRow);
                    System.out.println("Theater added with ID: " + t.getId());
                    for (Auditorium a : t.getAuditoriums()) {
                        System.out.println("  " + a.getName() + " (ID: " + a.getId() + ") - "
                                + a.getSeats().size() + " seats");
                    }
                    break;

                case 3:
                    service.printAllMovies();
                    System.out.print("Enter movie id: ");
                    int movieId = sc.nextInt();
                    service.printAllTheaters();
                    System.out.print("Enter theater id: ");
                    int theaterId = sc.nextInt();
                    System.out.print("Enter auditorium id: ");
                    int audId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter start time (e.g. 14:00): ");
                    String time = sc.nextLine().trim();
                    System.out.print("Price increment for PLATINUM seats: ");
                    int pInc = sc.nextInt();
                    System.out.print("Price increment for GOLD seats: ");
                    int gInc = sc.nextInt();
                    System.out.print("Price increment for SILVER seats: ");
                    int sInc = sc.nextInt();
                    sc.nextLine();
                    Show show = service.addShow(movieId, theaterId, audId, time, pInc, gInc, sInc);
                    if (show != null) {
                        System.out.println("Show added with ID: " + show.getId());
                    }
                    break;

                case 4:
                    service.printAllMovies();
                    break;

                case 5:
                    service.printAllTheaters();
                    break;

                case 6:
                    inMenu = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
