public class ExternalServiceCaller {
    private final RateLimitManager rateLimitManager;

    public ExternalServiceCaller(RateLimitManager rateLimitManager) {
        this.rateLimitManager = rateLimitManager;
    }

    public void processRequest(String clientId, boolean needsExternalCall) {
        if (!needsExternalCall) {
            System.out.println("Business logic completed: No external call needed.");
            return;
        }

        // Logic to decide which algorithm to use (could also be fetched from config)
        IRateLimitStrategy strategy = new SlidingWindowStrategy(5, 60000); 

        if (rateLimitManager.checkLimit(clientId, strategy)) {
            callExternalResource();
        } else {
            System.out.println("429 Too Many Requests: Rate limit exceeded for " + clientId);
        }
    }

    private void callExternalResource() {
        System.out.println("External Resource called successfully (Paid Operation).");
    }
}