import java.util.ArrayList;
import java.util.List;

public class Theater {
    private int id;
    private String name;
    private String city;
    private List<Auditorium> auditoriums;

    public Theater(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.auditoriums = new ArrayList<>();
    }

    public void addAuditorium(Auditorium auditorium) {
        auditoriums.add(auditorium);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public List<Auditorium> getAuditoriums() {
        return auditoriums;
    }
}
