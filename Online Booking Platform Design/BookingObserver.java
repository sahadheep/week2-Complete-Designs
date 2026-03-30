public interface BookingObserver {
    void onBookingStatusChanged(Booking booking, String message);
}
