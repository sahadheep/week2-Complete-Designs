import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BookingService {
    private final InventoryService inventoryService;
    private final Map<String, Booking> bookings = new HashMap<>();
    private final List<BookingObserver> observers = new ArrayList<>();

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void addObserver(BookingObserver observer) {
        observers.add(observer);
    }

    public Booking createPendingBooking(UserAccount user, Resource resource, LocalDate date, int holdMinutes) {
        String bookingId = UUID.randomUUID().toString();

        // Retry optimistic locking a few times in case of concurrent updates.
        for (int attempt = 0; attempt < 3; attempt++) {
            int expectedVersion = inventoryService.getVersion(resource.getId(), date);
            boolean holdPlaced = inventoryService.holdWithOptimisticLock(
                    bookingId,
                    resource.getId(),
                    date,
                    expectedVersion,
                    holdMinutes
            );

            if (holdPlaced) {
                Booking booking = new Booking(
                        bookingId,
                        user,
                        resource,
                        date,
                        resource.getPrice(),
                        LocalDateTime.now().plusMinutes(holdMinutes)
                );
                bookings.put(bookingId, booking);
                notifyAllObservers(booking, "Booking moved to PENDING with active hold");
                return booking;
            }
        }

        throw new IllegalStateException("Unable to place hold due to contention or sold-out inventory");
    }

    public Booking processPayment(String bookingId, PaymentGateway gateway) {
        Booking booking = getBookingOrThrow(bookingId);

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException("Only PENDING bookings can be paid");
        }

        if (booking.getHoldExpiresAt().isBefore(LocalDateTime.now())) {
            inventoryService.releaseHold(bookingId);
            booking.timeout();
            notifyAllObservers(booking, "Booking timed out before payment");
            throw new IllegalStateException("Hold has expired");
        }

        PaymentResult result = gateway.charge(bookingId, booking.getAmount());
        if (!result.isSuccess()) {
            throw new IllegalStateException("Payment failed for booking " + bookingId);
        }

        booking.paymentSucceeded();
        inventoryService.consumeHold(bookingId);
        notifyAllObservers(booking, "Payment success via " + result.getProvider() + ", booking CONFIRMED");
        return booking;
    }

    public void sweepExpiredBookings() {
        inventoryService.releaseExpiredHolds();

        for (Booking booking : bookings.values()) {
            if (booking.getStatus() == BookingStatus.PENDING && booking.getHoldExpiresAt().isBefore(LocalDateTime.now())) {
                booking.timeout();
                notifyAllObservers(booking, "Booking timed out and inventory released");
            }
        }
    }

    public Booking cancelBooking(String bookingId) {
        Booking booking = getBookingOrThrow(bookingId);

        if (booking.getStatus() == BookingStatus.PENDING) {
            inventoryService.releaseHold(bookingId);
            booking.cancel();
            notifyAllObservers(booking, "Pending booking cancelled; hold released");
            return booking;
        }

        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            booking.refund();
            notifyAllObservers(booking, "Confirmed booking refunded as per policy");
            return booking;
        }

        throw new IllegalStateException("Booking cannot be cancelled in state: " + booking.getStatus());
    }

    private Booking getBookingOrThrow(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found: " + bookingId);
        }
        return booking;
    }

    private void notifyAllObservers(Booking booking, String message) {
        for (BookingObserver observer : observers) {
            observer.onBookingStatusChanged(booking, message);
        }
    }
}
