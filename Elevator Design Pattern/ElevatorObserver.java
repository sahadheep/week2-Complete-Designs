/**
 * Observer interface for the Observer Pattern
 * Allows components to observe elevator state changes and requests
 */
public interface ElevatorObserver {
    void onStateChange(String newState);
    void onFloorChange(int newFloor);
    void onRequestAdded(Request request);
    void onRequestCompleted(Request request);
    void onEmergency(String message);
}
