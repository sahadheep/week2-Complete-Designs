/**
 * SCAN Algorithm Dispatcher (Industry Standard)
 * The elevator moves in one direction until:
 * 1. It reaches the top/bottom floor, OR
 * 2. It has no more requests in that direction
 * Then it reverses direction.
 * 
 * This prevents "starvation" and minimizes average wait time.
 */
public class ScanDispatcher extends Dispatcher {
    private Direction currentDirection = Direction.UP;

    public ScanDispatcher(int minFloor, int maxFloor) {
        super(minFloor, maxFloor);
    }

    @Override
    public int getNextFloor(int currentFloor, Direction direction) {
        if (requestQueue.isEmpty()) {
            return currentFloor; // No requests, stay at current floor
        }

        // Find the next request in the current direction
        int nextFloor = findNextFloorInDirection(currentFloor, currentDirection);

        // If no request in current direction, reverse direction
        if (nextFloor == currentFloor) {
            currentDirection = (currentDirection == Direction.UP) ? Direction.DOWN : Direction.UP;
            nextFloor = findNextFloorInDirection(currentFloor, currentDirection);
        }

        return nextFloor;
    }

    /**
     * Find the closest request in the specified direction
     */
    private int findNextFloorInDirection(int currentFloor, Direction direction) {
        Integer closestFloor = null;

        for (Request request : requestQueue) {
            int targetFloor = request.getToFloor();
            
            if (direction == Direction.UP) {
                // Look for floors above current floor
                if (targetFloor >= currentFloor) {
                    if (closestFloor == null || targetFloor < closestFloor) {
                        closestFloor = targetFloor;
                    }
                }
            } else {
                // Look for floors below current floor
                if (targetFloor <= currentFloor) {
                    if (closestFloor == null || targetFloor > closestFloor) {
                        closestFloor = targetFloor;
                    }
                }
            }
        }

        return (closestFloor != null) ? closestFloor : currentFloor;
    }

    public void setCurrentDirection(Direction direction) {
        this.currentDirection = direction;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    @Override
    public String getAlgorithmName() {
        return "SCAN Algorithm";
    }
}
