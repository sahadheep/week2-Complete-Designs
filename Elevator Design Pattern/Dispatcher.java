/**
 * Abstract Dispatcher class (Strategy Pattern)
 * Defines different algorithms for assigning the next floor to visit
 */
public abstract class Dispatcher {
    protected java.util.Queue<Request> requestQueue;
    protected int minFloor;
    protected int maxFloor;

    public Dispatcher(int minFloor, int maxFloor) {
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.requestQueue = new java.util.LinkedList<>();
    }

    public void addRequest(Request request) {
        requestQueue.offer(request);
    }

    public void removeRequest(Request request) {
        requestQueue.remove(request);
    }

    public java.util.Queue<Request> getRequestQueue() {
        return requestQueue;
    }

    public abstract int getNextFloor(int currentFloor, Direction direction);
    public abstract String getAlgorithmName();

    public int getPendingRequestCount() {
        return requestQueue.size();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}
