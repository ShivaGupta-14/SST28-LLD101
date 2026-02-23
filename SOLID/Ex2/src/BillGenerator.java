import java.util.*;

public class BillGenerator {

    public static class BillSummary {
        public final double subtotal;
        public final List<Double> lineTotals;
        public final double taxPct;
        public final double taxAmount;
        public final double discount;
        public final double total;

        public BillSummary(double subtotal, List<Double> lineTotals, double taxPct,
                double taxAmount, double discount, double total) {
            this.subtotal = subtotal;
            this.lineTotals = lineTotals;
            this.taxPct = taxPct;
            this.taxAmount = taxAmount;
            this.discount = discount;
            this.total = total;
        }
    }

    private final PricingCalculator pricing = new PricingCalculator();
    private final TaxCalculator taxCalc = new TaxCalculator();
    private final DiscountCalculator discountCalc = new DiscountCalculator();

    public BillSummary generate(String customerType, Map<String, MenuItem> menu, List<OrderLine> lines) {
        PricingCalculator.PriceBreakdown prices = pricing.calculate(menu, lines);
        TaxCalculator.TaxBreakdown tax = taxCalc.calculate(customerType, prices.subtotal);
        DiscountCalculator.DiscountBreakdown disc = discountCalc.calculate(customerType, prices.subtotal, lines.size());

        double total = prices.subtotal + tax.amount - disc.amount;

        return new BillSummary(prices.subtotal, prices.lineTotals,
                tax.percentage, tax.amount, disc.amount, total);
    }
}
