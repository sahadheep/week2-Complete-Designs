public class PaymentResult {
    private final boolean success;
    private final String provider;
    private final String transactionId;

    public PaymentResult(boolean success, String provider, String transactionId) {
        this.success = success;
        this.provider = provider;
        this.transactionId = transactionId;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getProvider() {
        return provider;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
