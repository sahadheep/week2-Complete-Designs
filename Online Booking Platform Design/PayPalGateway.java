public class PayPalGateway implements PaymentGateway {
    @Override
    public PaymentResult charge(String bookingId, double amount) {
        System.out.println("  [PAYMENT] PayPal charging booking " + bookingId + " for $" + amount);
        String txnId = "paypal-" + bookingId.substring(0, Math.min(8, bookingId.length()));
        return new PaymentResult(true, getProviderName(), txnId);
    }

    @Override
    public String getProviderName() {
        return "PAYPAL";
    }
}
