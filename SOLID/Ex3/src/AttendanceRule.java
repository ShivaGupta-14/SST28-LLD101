public class AttendanceRule extends Rule {
	@Override
	public Rule.Result check(StudentProfile s) {
		if (s.attendancePct < RuleInput.minAttendance) {
			return new Result(false, "attendance below " + RuleInput.minAttendance);
		}
		return new Result(true, null);
	}
}
