public abstract class Exporter {

	// Base contract
	protected void validate(ExportRequest req) {
		if (req == null) {
			throw new IllegalArgumentException("request cannot be null");
		}
		if (req.title == null) {
			throw new IllegalArgumentException("title cannot be null");
		}
		if (req.body == null) {
			throw new IllegalArgumentException("body cannot be null");
		}
	}

	public ExportResult export(ExportRequest req) {
		validate(req);
		return doExport(req);
	}

	protected abstract ExportResult doExport(ExportRequest req);
}
