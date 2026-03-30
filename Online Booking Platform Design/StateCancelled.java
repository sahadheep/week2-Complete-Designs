public class StateCancelled implements BookingState {
    private final Booking booking;

    public StateCancelled(Booking booking) {
        this.booking = booking;
    }

    @Override
    public void enter() {
        booking.setStatus(BookingStatus.CANCELLED);
    }

    @Override
    public void onPaymentSuccess() {
        throw new IllegalStateException("Cancelled booking cannot be paid");
    }

    @Override
    public void onTimeout() {
        System.out.println("  [STATE] Cancelled booking cannot time out");
    }

    @Override
    public void onCancel() {
        System.out.println("  [STATE] Booking already cancelled");
    }

    @Override
    public void onRefund() {
        throw new IllegalStateException("Cancelled booking cannot be refunded");
    }

    @Override
    public String getStateName() {
        return "CANCELLED";
    }
}
