public class StateRefunded implements BookingState {
    private final Booking booking;

    public StateRefunded(Booking booking) {
        this.booking = booking;
    }

    @Override
    public void enter() {
        booking.setStatus(BookingStatus.REFUNDED);
    }

    @Override
    public void onPaymentSuccess() {
        throw new IllegalStateException("Refunded booking cannot be paid");
    }

    @Override
    public void onTimeout() {
        System.out.println("  [STATE] Refunded booking cannot time out");
    }

    @Override
    public void onCancel() {
        System.out.println("  [STATE] Refunded booking cannot be cancelled");
    }

    @Override
    public void onRefund() {
        System.out.println("  [STATE] Booking already refunded");
    }

    @Override
    public String getStateName() {
        return "REFUNDED";
    }
}
