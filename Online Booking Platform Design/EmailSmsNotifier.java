public class EmailSmsNotifier implements BookingObserver {
    @Override
    public void onBookingStatusChanged(Booking booking, String message) {
        System.out.println("  [NOTIFY] Email/SMS -> " + booking.getUser().getEmail() + " | " + message);
    }
}
