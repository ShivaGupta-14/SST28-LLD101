import java.util.*;

public class EligibilityEngine {
	private final FakeEligibilityStore store;
	private final List<Rule> eligibilityRules = new ArrayList<>();

	public EligibilityEngine(FakeEligibilityStore store) {
		this.store = store;

		eligibilityRules.add(new DisciplinaryRule());
		eligibilityRules.add(new CgrRule());
		eligibilityRules.add(new CreditRule());
		eligibilityRules.add(new AttendanceRule());
	}

	public void runAndPrint(StudentProfile s) {
		ReportPrinter p = new ReportPrinter();
		EligibilityEngineResult r = evaluate(s);
		p.print(s, r);
		store.save(s.rollNo, r.status);
	}

	public EligibilityEngineResult evaluate(StudentProfile s) {
		List<String> reasons = new ArrayList<>();
		String status = "ELIGIBLE";

		for (Rule rule : eligibilityRules) {
			Rule.Result result = rule.check(s);
			if (!result.pass) {
				status = "NOT_ELIGIBLE";
				reasons.add(result.reason);
			}
		}

		return new EligibilityEngineResult(status, reasons);
	}
}

class EligibilityEngineResult {
	public final String status;
	public final List<String> reasons;

	public EligibilityEngineResult(String status, List<String> reasons) {
		this.status = status;
		this.reasons = reasons;
	}
}
