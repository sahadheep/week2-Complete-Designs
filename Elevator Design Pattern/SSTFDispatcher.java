/**
 * Shortest Seek Time First Dispatcher
 * Moves to the closest floor with a pending request
 * Efficient in the moment but can cause starvation for distant floors
 */
public class SSTFDispatcher extends Dispatcher {

    public SSTFDispatcher(int minFloor, int maxFloor) {
        super(minFloor, maxFloor);
    }

    @Override
    public int getNextFloor(int currentFloor, Direction direction) {
        if (requestQueue.isEmpty()) {
            return currentFloor;
        }

        // Find the closest floor with a pending request
        int closestFloor = currentFloor;
        int minDistance = Integer.MAX_VALUE;

        for (Request request : requestQueue) {
            int targetFloor = request.getToFloor();
            int distance = Math.abs(targetFloor - currentFloor);

            if (distance < minDistance) {
                minDistance = distance;
                closestFloor = targetFloor;
            }
        }

        return closestFloor;
    }

    @Override
    public String getAlgorithmName() {
        return "SSTF (Shortest Seek Time First)";
    }
}
