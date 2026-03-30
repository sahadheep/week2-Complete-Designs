/**
 * First-Come-First-Served Dispatcher
 * Simple but inefficient - processes requests in order they were received
 */
public class FCFSDispatcher extends Dispatcher {

    public FCFSDispatcher(int minFloor, int maxFloor) {
        super(minFloor, maxFloor);
    }

    @Override
    public int getNextFloor(int currentFloor, Direction direction) {
        if (requestQueue.isEmpty()) {
            return currentFloor;
        }

        // Get the first request in queue
        Request firstRequest = requestQueue.peek();
        return firstRequest.getToFloor();
    }

    @Override
    public String getAlgorithmName() {
        return "FCFS (First-Come-First-Served)";
    }
}
