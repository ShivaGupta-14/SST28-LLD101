public class CreditRule extends Rule {
	@Override
	public Rule.Result check(StudentProfile s) {
		if (s.earnedCredits < RuleInput.minCredits) {
			return new Result(false, "credits below " + RuleInput.minCredits);
		}
		return new Result(true, null);
	}
}
