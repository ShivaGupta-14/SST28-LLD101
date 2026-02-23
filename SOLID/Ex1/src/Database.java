import java.util.List;

public abstract class Database {
	public abstract void save(StudentRecord studentRecord);
	public abstract int count();
	public abstract List<StudentRecord> all();
}
