public class PrintingService {
	private final Database db;

	public PrintingService(final Database db) {
		this.db = db;
	}

	public void printStudentCreation(final StudentRecord rec) {
		System.out.println("OK: created student " + rec.id);
		System.out.println("Saved. Total students: " + db.count());
		System.out.println("CONFIRMATION:");
		System.out.println(rec);
	}
}
