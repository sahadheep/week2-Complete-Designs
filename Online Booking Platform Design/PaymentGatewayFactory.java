public class PaymentGatewayFactory {
    public static PaymentGateway createGateway(String provider) {
        if (provider == null) {
            throw new IllegalArgumentException("Payment provider cannot be null");
        }

        if (provider.equalsIgnoreCase("STRIPE")) {
            return new StripeGateway();
        }
        if (provider.equalsIgnoreCase("PAYPAL")) {
            return new PayPalGateway();
        }
        throw new IllegalArgumentException("Unsupported provider: " + provider);
    }
}
