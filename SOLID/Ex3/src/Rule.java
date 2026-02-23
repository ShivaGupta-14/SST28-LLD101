public abstract class Rule {
	public static class Result {
		public boolean pass;
		public String reason;

		public Result(boolean pass, String reason) {
			this.pass = pass;
			this.reason = reason;
		}
	}

	public abstract Result check(StudentProfile s);
}
