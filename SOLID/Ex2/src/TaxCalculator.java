public class TaxCalculator {

    public static class TaxBreakdown {
        public final double percentage;
        public final double amount;

        public TaxBreakdown(double percentage, double amount) {
            this.percentage = percentage;
            this.amount = amount;
        }
    }

    public TaxBreakdown calculate(String customerType, double subtotal) {
        double pct = TaxRules.taxPercent(customerType);
        double amt = subtotal * (pct / 100.0);
        return new TaxBreakdown(pct, amt);
    }
}
