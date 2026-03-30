public class StateTimedOut implements BookingState {
    private final Booking booking;

    public StateTimedOut(Booking booking) {
        this.booking = booking;
    }

    @Override
    public void enter() {
        booking.setStatus(BookingStatus.TIMED_OUT);
    }

    @Override
    public void onPaymentSuccess() {
        throw new IllegalStateException("Timed out booking cannot be paid");
    }

    @Override
    public void onTimeout() {
        System.out.println("  [STATE] Booking already timed out");
    }

    @Override
    public void onCancel() {
        System.out.println("  [STATE] Timed out booking cannot be cancelled");
    }

    @Override
    public void onRefund() {
        throw new IllegalStateException("Timed out booking cannot be refunded");
    }

    @Override
    public String getStateName() {
        return "TIMED_OUT";
    }
}
