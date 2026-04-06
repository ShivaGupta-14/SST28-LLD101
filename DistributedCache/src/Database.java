import java.util.HashMap;
import java.util.Map;

public class Database {
    private Map<String, String> data;

    public Database() {
        this.data = new HashMap<>();
    }

    public String get(String key) {
        System.out.println("    [DB] Fetching '" + key + "' from database");
        return data.get(key);
    }

    public void put(String key, String value) {
        data.put(key, value);
    }
}
