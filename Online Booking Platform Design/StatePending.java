public class StatePending implements BookingState {
    private final Booking booking;

    public StatePending(Booking booking) {
        this.booking = booking;
    }

    @Override
    public void enter() {
        booking.setStatus(BookingStatus.PENDING);
    }

    @Override
    public void onPaymentSuccess() {
        booking.setState(new StatePaid(booking));
    }

    @Override
    public void onTimeout() {
        booking.setState(new StateTimedOut(booking));
    }

    @Override
    public void onCancel() {
        booking.setState(new StateCancelled(booking));
    }

    @Override
    public void onRefund() {
        throw new IllegalStateException("Cannot refund a pending booking");
    }

    @Override
    public String getStateName() {
        return "PENDING";
    }
}
