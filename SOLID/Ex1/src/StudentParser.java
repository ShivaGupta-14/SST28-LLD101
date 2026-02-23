import java.util.*;

public class StudentParser {
	private Database db;

	public StudentParser(final Database db) {
		this.db = db;
	}

	public Optional<StudentRecord> parse(final String raw) {
		System.out.println("INPUT: " + raw);

		Map<String, String> kv = new LinkedHashMap<>();
		String[] parts = raw.split(";");
		for (String p : parts) {
			String[] t = p.split("=", 2);
			if (t.length == 2)
				kv.put(t[0].trim(), t[1].trim());
		}

		String id = IdUtil.nextStudentId(db.count());
		String name = kv.getOrDefault("name", "");
		String email = kv.getOrDefault("email", "");
		String phone = kv.getOrDefault("phone", "");
		String program = kv.getOrDefault("program", "");

		StudentRecord rec = new StudentRecord(id, name, email, phone, program);

		List<String> validationErrors = StudentValidator.validate(rec);
		if (!validationErrors.isEmpty()) {
			StudentValidator.printValidationErrors(validationErrors);
			return Optional.empty();
		}

		return Optional.of(rec);
	}
}