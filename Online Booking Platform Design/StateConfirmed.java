public class StateConfirmed implements BookingState {
    private final Booking booking;

    public StateConfirmed(Booking booking) {
        this.booking = booking;
    }

    @Override
    public void enter() {
        booking.setStatus(BookingStatus.CONFIRMED);
    }

    @Override
    public void onPaymentSuccess() {
        System.out.println("  [STATE] Booking is already confirmed");
    }

    @Override
    public void onTimeout() {
        throw new IllegalStateException("Confirmed booking cannot time out");
    }

    @Override
    public void onCancel() {
        booking.setState(new StateRefunded(booking));
    }

    @Override
    public void onRefund() {
        booking.setState(new StateRefunded(booking));
    }

    @Override
    public String getStateName() {
        return "CONFIRMED";
    }
}
