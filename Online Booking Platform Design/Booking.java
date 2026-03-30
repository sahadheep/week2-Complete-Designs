import java.time.LocalDate;
import java.time.LocalDateTime;

public class Booking {
    private final String id;
    private final UserAccount user;
    private final Resource resource;
    private final LocalDate bookingDate;
    private final double amount;
    private LocalDateTime holdExpiresAt;
    private BookingStatus status;
    private BookingState state;

    public Booking(String id, UserAccount user, Resource resource, LocalDate bookingDate, double amount, LocalDateTime holdExpiresAt) {
        this.id = id;
        this.user = user;
        this.resource = resource;
        this.bookingDate = bookingDate;
        this.amount = amount;
        this.holdExpiresAt = holdExpiresAt;
        this.state = new StatePending(this);
        this.state.enter();
    }

    public void setState(BookingState newState) {
        this.state = newState;
        this.state.enter();
    }

    public void paymentSucceeded() {
        state.onPaymentSuccess();
    }

    public void timeout() {
        state.onTimeout();
    }

    public void cancel() {
        state.onCancel();
    }

    public void refund() {
        state.onRefund();
    }

    public String getId() {
        return id;
    }

    public UserAccount getUser() {
        return user;
    }

    public Resource getResource() {
        return resource;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getHoldExpiresAt() {
        return holdExpiresAt;
    }

    public void setHoldExpiresAt(LocalDateTime holdExpiresAt) {
        this.holdExpiresAt = holdExpiresAt;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public String getStateName() {
        return state.getStateName();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", user=" + user.getName() +
                ", resource=" + resource.getId() +
                ", bookingDate=" + bookingDate +
                ", status=" + status +
                '}';
    }
}
