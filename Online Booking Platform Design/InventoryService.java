import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class InventoryService {
    private static class Slot {
        int totalAvailable;
        int remaining;
        int version;

        Slot(int totalAvailable) {
            this.totalAvailable = totalAvailable;
            this.remaining = totalAvailable;
            this.version = 0;
        }
    }

    private static class Hold {
        String resourceId;
        LocalDate date;
        LocalDateTime expiresAt;

        Hold(String resourceId, LocalDate date, LocalDateTime expiresAt) {
            this.resourceId = resourceId;
            this.date = date;
            this.expiresAt = expiresAt;
        }
    }

    private final Map<String, Slot> slots = new HashMap<>();
    private final Map<String, Hold> activeHolds = new HashMap<>();

    public synchronized void seedInventory(String resourceId, LocalDate date, int totalAvailable) {
        slots.put(slotKey(resourceId, date), new Slot(totalAvailable));
    }

    public synchronized int getVersion(String resourceId, LocalDate date) {
        Slot slot = slots.get(slotKey(resourceId, date));
        return slot == null ? -1 : slot.version;
    }

    public synchronized int getRemaining(String resourceId, LocalDate date) {
        Slot slot = slots.get(slotKey(resourceId, date));
        return slot == null ? 0 : slot.remaining;
    }

    public synchronized boolean holdWithOptimisticLock(
            String bookingId,
            String resourceId,
            LocalDate date,
            int expectedVersion,
            int holdMinutes
    ) {
        Slot slot = slots.get(slotKey(resourceId, date));
        if (slot == null) {
            return false;
        }

        if (slot.version != expectedVersion || slot.remaining <= 0) {
            return false;
        }

        slot.remaining -= 1;
        slot.version += 1;
        activeHolds.put(bookingId, new Hold(resourceId, date, LocalDateTime.now().plusMinutes(holdMinutes)));
        return true;
    }

    public synchronized void releaseHold(String bookingId) {
        Hold hold = activeHolds.remove(bookingId);
        if (hold == null) {
            return;
        }

        Slot slot = slots.get(slotKey(hold.resourceId, hold.date));
        if (slot != null && slot.remaining < slot.totalAvailable) {
            slot.remaining += 1;
            slot.version += 1;
        }
    }

    public synchronized boolean consumeHold(String bookingId) {
        Hold hold = activeHolds.remove(bookingId);
        return hold != null;
    }

    public synchronized void releaseExpiredHolds() {
        LocalDateTime now = LocalDateTime.now();
        Iterator<Map.Entry<String, Hold>> iterator = activeHolds.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Hold> entry = iterator.next();
            Hold hold = entry.getValue();
            if (!hold.expiresAt.isAfter(now)) {
                Slot slot = slots.get(slotKey(hold.resourceId, hold.date));
                if (slot != null && slot.remaining < slot.totalAvailable) {
                    slot.remaining += 1;
                    slot.version += 1;
                }
                iterator.remove();
            }
        }
    }

    private String slotKey(String resourceId, LocalDate date) {
        return resourceId + "|" + date;
    }
}
