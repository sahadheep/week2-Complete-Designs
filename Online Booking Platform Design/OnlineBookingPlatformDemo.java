import java.time.LocalDate;
import java.util.List;

public class OnlineBookingPlatformDemo {
    public static void main(String[] args) {
        SearchService searchService = new SearchService();
        InventoryService inventoryService = new InventoryService();
        BookingService bookingService = new BookingService(inventoryService);
        bookingService.addObserver(new EmailSmsNotifier());

        Resource parisLuxury = new Resource("R-101", "HotelRoom", "Paris", "Luxury", 220.0, "City center suite");
        Resource parisBudget = new Resource("R-102", "HotelRoom", "Paris", "Budget", 95.0, "Compact room");
        searchService.addResource(parisLuxury);
        searchService.addResource(parisBudget);

        LocalDate bookingDate = LocalDate.now().plusDays(10);
        inventoryService.seedInventory(parisLuxury.getId(), bookingDate, 1);
        inventoryService.seedInventory(parisBudget.getId(), bookingDate, 3);

        UserAccount user = new UserAccount("U-1", "Sahadheep", "sahadheep@example.com");

        System.out.println("[PHASE 1] Search and discovery");
        List<Resource> results = searchService.search("Paris", bookingDate, 80, 300, "Luxury");
        for (Resource result : results) {
            System.out.println("  [SEARCH] " + result);
        }

        System.out.println("\n[PHASE 2] Create pending booking with hold");
        Booking booking = bookingService.createPendingBooking(user, parisLuxury, bookingDate, 15);
        System.out.println("  [BOOKING] " + booking + " | state=" + booking.getStateName());
        System.out.println("  [INVENTORY] Remaining luxury slots: " + inventoryService.getRemaining(parisLuxury.getId(), bookingDate));

        System.out.println("\n[PHASE 3] Payment and confirmation (Strategy + Factory)");
        PaymentGateway gateway = PaymentGatewayFactory.createGateway("STRIPE");
        Booking confirmed = bookingService.processPayment(booking.getId(), gateway);
        System.out.println("  [BOOKING] " + confirmed + " | state=" + confirmed.getStateName());

        System.out.println("\n[PHASE 4] Concurrency behavior (last slot)");
        try {
            bookingService.createPendingBooking(new UserAccount("U-2", "User2", "u2@example.com"), parisLuxury, bookingDate, 15);
        } catch (Exception ex) {
            System.out.println("  [EXPECTED] " + ex.getMessage());
        }

        System.out.println("\n[PHASE 5] Cancellation and refund policy");
        Booking refundable = bookingService.createPendingBooking(user, parisBudget, bookingDate, 15);
        PaymentGateway paypal = PaymentGatewayFactory.createGateway("PAYPAL");
        bookingService.processPayment(refundable.getId(), paypal);
        bookingService.cancelBooking(refundable.getId());
        System.out.println("  [BOOKING] " + refundable + " | state=" + refundable.getStateName());
        System.out.println("DESIGN PATTERNS IMPLEMENTED");
        System.out.println("1. STATE PATTERN -> BookingState + concrete states");
        System.out.println("2. STRATEGY PATTERN -> PaymentGateway implementations");
        System.out.println("3. FACTORY PATTERN -> PaymentGatewayFactory");
        System.out.println("4. OBSERVER PATTERN -> BookingObserver notifications");
        System.out.println("5. SERVICE LAYER -> Search, Inventory, Booking services");
    }
}
