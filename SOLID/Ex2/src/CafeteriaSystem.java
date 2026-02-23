import java.util.*;

public class CafeteriaSystem {
	private final Map<String, MenuItem> menu = new LinkedHashMap<>();
	private final FileStore store = new FileStore();
	private final InvoiceGenerator invoiceGenerator = new InvoiceGenerator();

	public void addToMenu(MenuItem i) {
		menu.put(i.id, i);
	}

	public void checkout(String customerType, List<OrderLine> lines) {
		InvoiceGenerator.InvoiceResult invoice = invoiceGenerator.generate(customerType, menu, lines);

		System.out.print(invoice.content);

		store.save(invoice.id, invoice.content);
		System.out.println("Saved invoice: " + invoice.id + " (lines=" + store.countLines(invoice.id) + ")");
	}
}
