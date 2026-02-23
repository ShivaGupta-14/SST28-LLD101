import java.util.*;

public class OnboardingService {
	private final Database db;
	private final StudentParser studentParser;
	private final PrintingService printingService;

	public OnboardingService(Database db) {
		this.db = db;
		this.studentParser = new StudentParser(this.db);
		this.printingService = new PrintingService(this.db);
	}

	public void registerFromRawInput(String raw) {
		Optional<StudentRecord> recOptional = studentParser.parse(raw);
		if (recOptional.isEmpty())
			return;

		StudentRecord rec = recOptional.get();

		db.save(rec);

		printingService.printStudentCreation(rec);
	}
}
