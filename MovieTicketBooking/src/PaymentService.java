import java.util.Random;

public class PaymentService {
    private Random random;

    public PaymentService() {
        this.random = new Random();
    }

    public String processPayment(int amount) {
        String txnId = "TXN" + (random.nextInt(90000) + 10000);
        System.out.println("  Payment of Rs." + amount + " processed. Transaction ID: " + txnId);
        return txnId;
    }

    public String processRefund(String paymentId, int amount) {
        String refundId = "REF" + (random.nextInt(90000) + 10000);
        System.out.println("  Refund of Rs." + amount + " for " + paymentId + " processed. Refund ID: " + refundId);
        return refundId;
    }
}
