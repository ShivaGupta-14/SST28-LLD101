import java.util.*;

public class PricingCalculator {

    public static class PriceBreakdown {
        public final double subtotal;
        public final List<Double> lineTotals;

        public PriceBreakdown(double subtotal, List<Double> lineTotals) {
            this.subtotal = subtotal;
            this.lineTotals = lineTotals;
        }
    }

    public PriceBreakdown calculate(Map<String, MenuItem> menu, List<OrderLine> lines) {
        double subtotal = 0.0;
        List<Double> lineTotals = new ArrayList<>();
        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            double total = item.price * l.qty;
            lineTotals.add(total);
            subtotal += total;
        }
        return new PriceBreakdown(subtotal, lineTotals);
    }
}
