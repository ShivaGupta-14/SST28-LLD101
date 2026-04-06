public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Rate Limiting System Demo ===\n");

        ExternalService external = new ExternalService();

        System.out.println("--- Fixed Window Counter (5 requests per 2 seconds) ---\n");
        RateLimiter fixedWindow = new FixedWindowCounter(5, 2000);
        InternalService service = new InternalService(fixedWindow, external);

        for (int i = 1; i <= 7; i++) {
            String result = service.handleRequest("T1", "data-" + i, true);
            System.out.println("    Result: " + result + "\n");
        }

        System.out.println("--- Request with no external call (skips rate limiter) ---\n");
        service.handleRequest("T1", "data-local", false);
        System.out.println();

        System.out.println("--- Waiting 2 seconds for window reset ---\n");
        Thread.sleep(2100);

        String result = service.handleRequest("T1", "data-after-reset", true);
        System.out.println("    Result: " + result + "\n");

        System.out.println("--- Switching to Sliding Window Counter (no code change in service) ---\n");
        RateLimiter slidingWindow = new SlidingWindowCounter(5, 2000);
        service.setRateLimiter(slidingWindow);

        for (int i = 1; i <= 7; i++) {
            String r = service.handleRequest("T1", "sliding-" + i, true);
            System.out.println("    Result: " + r + "\n");
        }

        System.out.println("--- Different keys (per tenant isolation) ---\n");
        RateLimiter perTenant = new FixedWindowCounter(3, 5000);
        InternalService service2 = new InternalService(perTenant, external);

        service2.handleRequest("tenant-A", "req1", true);
        System.out.println();
        service2.handleRequest("tenant-A", "req2", true);
        System.out.println();
        service2.handleRequest("tenant-B", "req1", true);
        System.out.println();
        service2.handleRequest("tenant-A", "req3", true);
        System.out.println();
        service2.handleRequest("tenant-A", "req4", true);
        System.out.println();
        service2.handleRequest("tenant-B", "req2", true);
        System.out.println();
    }
}
