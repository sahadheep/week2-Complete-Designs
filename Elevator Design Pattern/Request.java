/**
 * Request class represents a button press from a user
 * Can be either a Hall Call (outside elevator) or Car Call (inside elevator)
 */
public class Request {
    private int fromFloor;
    private int toFloor;
    private Direction direction;
    private long timestamp;
    private RequestType type;

    public enum RequestType {
        HALL_CALL,      // Button pressed outside the elevator
        CAR_CALL        // Button pressed inside the elevator
    }

    public Request(int fromFloor, int toFloor, Direction direction, RequestType type) {
        this.fromFloor = fromFloor;
        this.toFloor = toFloor;
        this.direction = direction;
        this.type = type;
        this.timestamp = System.currentTimeMillis();
    }

    public int getFromFloor() {
        return fromFloor;
    }

    public int getToFloor() {
        return toFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public RequestType getType() {
        return type;
    }

    public long getWaitTime() {
        return System.currentTimeMillis() - timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] Floor %d → %d (Direction: %s, Wait: %dms)",
                type.name(), fromFloor, toFloor, direction.name(), getWaitTime());
    }
}
