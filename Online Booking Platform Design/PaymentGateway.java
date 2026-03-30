public interface PaymentGateway {
    PaymentResult charge(String bookingId, double amount);
    String getProviderName();
}
