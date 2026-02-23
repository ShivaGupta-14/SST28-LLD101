public class DiscountCalculator {

    public static class DiscountBreakdown {
        public final double amount;

        public DiscountBreakdown(double amount) {
            this.amount = amount;
        }
    }

    public DiscountBreakdown calculate(String customerType, double subtotal, int distinctItems) {
        double disc = DiscountRules.discountAmount(customerType, subtotal, distinctItems);
        return new DiscountBreakdown(disc);
    }
}
