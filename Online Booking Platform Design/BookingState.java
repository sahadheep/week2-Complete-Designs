public interface BookingState {
    void enter();
    void onPaymentSuccess();
    void onTimeout();
    void onCancel();
    void onRefund();
    String getStateName();
}
