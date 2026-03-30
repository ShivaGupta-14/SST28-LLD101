public class Movie {
    private int id;
    private String name;
    private int durationMinutes;

    public Movie(int id, String name, int durationMinutes) {
        this.id = id;
        this.name = name;
        this.durationMinutes = durationMinutes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }
}
