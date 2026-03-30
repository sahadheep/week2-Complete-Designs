public class StatePaid implements BookingState {
    private final Booking booking;

    public StatePaid(Booking booking) {
        this.booking = booking;
    }

    @Override
    public void enter() {
        booking.setStatus(BookingStatus.PAID);
        booking.setState(new StateConfirmed(booking));
    }

    @Override
    public void onPaymentSuccess() {
        System.out.println("  [STATE] Booking is already paid");
    }

    @Override
    public void onTimeout() {
        throw new IllegalStateException("Paid booking cannot time out");
    }

    @Override
    public void onCancel() {
        throw new IllegalStateException("Paid booking cannot be cancelled directly");
    }

    @Override
    public void onRefund() {
        booking.setState(new StateRefunded(booking));
    }

    @Override
    public String getStateName() {
        return "PAID";
    }
}
