public class CgrRule extends Rule {
	@Override
	public Rule.Result check(StudentProfile s) {
		if (s.cgr < RuleInput.minCgr) {
			return new Result(false, "CGR below " + RuleInput.minCgr);
		}
		return new Result(true, null);
	}
}
