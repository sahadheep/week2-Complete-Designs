/**
 * ElevatorController - Singleton Pattern Implementation
 * Manages the entire elevator system as a single point of control
 */
public class ElevatorController {
    private static ElevatorController instance;
    private Building building;
    private java.util.List<ElevatorObserver> observers;
    private boolean isRunning;

    /**
     * Private constructor - Singleton Pattern
     */
    private ElevatorController() {
        this.observers = new java.util.ArrayList<>();
        this.isRunning = false;
    }

    /**
     * Get the singleton instance
     */
    public static synchronized ElevatorController getInstance() {
        if (instance == null) {
            instance = new ElevatorController();
        }
        return instance;
    }

    /**
     * Initialize the building with a specified number of floors and elevators
     */
    public void initializeBuilding(int minFloor, int maxFloor, int elevatorCount) {
        this.building = new Building(minFloor, maxFloor);
        
        for (int i = 0; i < elevatorCount; i++) {
            // Create elevator with SCAN dispatcher (industry standard)
            Dispatcher dispatcher = DispatcherFactory.createScanDispatcher(minFloor, maxFloor);
            Elevator elevator = new Elevator(i + 1, minFloor, maxFloor, 1000, dispatcher);
            building.addElevator(elevator);
        }
    }

    /**
     * Add an observer to receive system notifications
     */
    public void addObserver(ElevatorObserver observer) {
        observers.add(observer);
    }

    /**
     * Remove an observer
     */
    public void removeObserver(ElevatorObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notify all observers of a state change
     */
    public void notifyStateChange(String newState) {
        for (ElevatorObserver observer : observers) {
            observer.onStateChange(newState);
        }
    }

    /**
     * Start the elevator system
     */
    public void startSystem() {
        isRunning = true;
        System.out.println("[CONTROLLER] Elevator system started");
    }

    /**
     * Stop the elevator system
     */
    public void stopSystem() {
        isRunning = false;
        System.out.println("[CONTROLLER] Elevator system stopped");
    }

    /**
     * Get the building
     */
    public Building getBuilding() {
        return building;
    }

    /**
     * Update all elevators - called in the main control loop
     */
    public void updateAllElevators() {
        if (building != null && isRunning) {
            for (Elevator elevator : building.getElevators()) {
                elevator.update();
            }
        }
    }

    /**
     * Check if system is running
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Reset the system (clears all elevators and building)
     */
    public synchronized void reset() {
        instance = null;
    }
}
