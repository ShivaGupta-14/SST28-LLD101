public class InternalService {
    private RateLimiter rateLimiter;
    private ExternalService externalService;

    public InternalService(RateLimiter rateLimiter, ExternalService externalService) {
        this.rateLimiter = rateLimiter;
        this.externalService = externalService;
    }

    public void setRateLimiter(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    public String handleRequest(String clientId, String data, boolean needsExternalCall) {
        System.out.println("Request from " + clientId + " [data=" + data + "]");

        if (!needsExternalCall) {
            System.out.println("    No external call needed. Returning cached result.");
            return "cached-result";
        }

        if (!rateLimiter.allowRequest(clientId)) {
            System.out.println("    RATE LIMITED! External call denied for " + clientId);
            return "rate-limited";
        }

        String result = externalService.call(data);
        return result;
    }
}
