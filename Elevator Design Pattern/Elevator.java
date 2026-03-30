/**
 * Elevator class - The main controller that orchestrates all components
 * Uses State Pattern to manage states and implements ButtonObserver for Observer Pattern
 */
public class Elevator implements ButtonObserver {
    private int id;
    private int currentFloor;
    private int maxCapacity;
    private int currentLoad; // in kg
    private boolean isMotorRunning = false;
    private boolean isDoorOpen = false;
    private boolean isDoorObstructed = false;
    private boolean isEmergencyStopped = false;
    private Direction currentDirection = Direction.IDLE;
    private ElevatorState currentState;
    private Dispatcher dispatcher;
    private int minFloor;
    private int maxFloor;
    
    // Performance metrics
    private long totalTravelTime = 0;
    private int totalRequestsServed = 0;
    private long totalWaitTime = 0;

    public Elevator(int id, int minFloor, int maxFloor, int maxCapacity, Dispatcher dispatcher) {
        this.id = id;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.currentFloor = minFloor;
        this.maxCapacity = maxCapacity;
        this.dispatcher = dispatcher;
        this.currentState = new StateIdle(this);
        this.currentState.enter();
    }

    // State management
    public void setState(ElevatorState newState) {
        if (currentState != null) {
            currentState.exit();
        }
        this.currentState = newState;
        this.currentState.enter();
    }

    public ElevatorState getCurrentState() {
        return currentState;
    }

    // Motor control
    public void startMotor(Direction direction) {
        if (!isEmergencyStopped) {
            isMotorRunning = true;
            currentDirection = direction;
            System.out.println("  [MOTOR] Started - Direction: " + direction);
        }
    }

    public void stopMotor() {
        isMotorRunning = false;
        currentDirection = Direction.IDLE;
        System.out.println("  [MOTOR] Stopped");
    }

    public boolean isMotorRunning() {
        return isMotorRunning;
    }

    // Door control
    public void openDoor() {
        if (!isEmergencyStopped) {
            isDoorOpen = true;
            System.out.println("  [DOOR] Opening at Floor " + currentFloor);
        }
    }

    public void closeDoor() {
        isDoorOpen = false;
        isDoorObstructed = false;
        System.out.println("  [DOOR] Closed");
    }

    public boolean isDoorOpen() {
        return isDoorOpen;
    }

    public void setDoorObstructed(boolean obstructed) {
        this.isDoorObstructed = obstructed;
    }

    public boolean isDoorObstructed() {
        return isDoorObstructed;
    }

    // Floor movement
    public void setCurrentFloor(int floor) {
        if (floor >= minFloor && floor <= maxFloor) {
            this.currentFloor = floor;
        }
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    // Request management
    public void requestFloor(int floor, Direction direction, Request.RequestType type) {
        if (floor >= minFloor && floor <= maxFloor && floor != currentFloor) {
            Request request = new Request(currentFloor, floor, direction, type);
            dispatcher.addRequest(request);
            System.out.println("[REQUEST] " + request);
        }
    }

    /**
     * Observer Pattern: Button press notification from Floor
     */
    @Override
    public void onButtonPressed(int floor, Direction direction) {
        if (floor >= minFloor && floor <= maxFloor && floor != currentFloor) {
            System.out.println("  [OBSERVER] Floor " + floor + " button press detected by Elevator " + id);
            requestFloor(floor, direction, Request.RequestType.HALL_CALL);
        }
    }

    // Direction
    public void setDirection(Direction direction) {
        this.currentDirection = direction;
    }

    public Direction getDirection() {
        return currentDirection;
    }

    // Safety features
    public void triggerEmergencyStop() {
        isEmergencyStopped = true;
        stopMotor();
        closeDoor();
        System.out.println("[EMERGENCY] Elevator STOPPED!");
    }

    public void resetEmergencyStop() {
        isEmergencyStopped = false;
        System.out.println("[EMERGENCY] Emergency stop reset");
    }

    public boolean isEmergencyStopped() {
        return isEmergencyStopped;
    }

    // Load management
    public void updateLoad(int additionalWeight) {
        currentLoad += additionalWeight;
        if (currentLoad > maxCapacity) {
            System.out.println("[WARNING] Overload detected! Current: " + currentLoad + " kg, Max: " + maxCapacity + " kg");
        } else {
            System.out.println("[INFO] Load: " + currentLoad + " / " + maxCapacity + " kg");
        }
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public boolean isOverload() {
        return currentLoad > maxCapacity;
    }

    // Dispatcher access
    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    // Performance metrics
    public void recordRequest(long waitTime) {
        totalRequestsServed++;
        totalWaitTime += waitTime;
    }

    public void recordTravelTime(long travelTime) {
        totalTravelTime += travelTime;
    }

    public double getAverageWaitTime() {
        return totalRequestsServed > 0 ? (double) totalWaitTime / totalRequestsServed : 0;
    }

    public double getAverageTravelTime() {
        return totalRequestsServed > 0 ? (double) totalTravelTime / totalRequestsServed : 0;
    }

    // Main control loop
    public void update() {
        if (!isEmergencyStopped && currentState != null) {
            currentState.move();
        }
    }

    public void displayStatus() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         ELEVATOR " + id + " STATUS REPORT         ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ Current Floor: " + currentFloor + (currentFloor < 10 ? "                    " : "                   ") + "║");
        System.out.println("║ State: " + String.format("%-31s║", currentState.getStateName()));
        System.out.println("║ Direction: " + String.format("%-27s║", currentDirection.name()));
        System.out.println("║ Door Status: " + (isDoorOpen ? "OPEN" : "CLOSED") + (isDoorOpen && isDoorObstructed ? " (OBSTRUCTED)" : "") + String.format("%19s║", ""));
        System.out.println("║ Load: " + currentLoad + " / " + maxCapacity + " kg" + String.format("%25s║", ""));
        System.out.println("║ Pending Requests: " + String.format("%-20d║", dispatcher.getPendingRequestCount()));
        System.out.println("║ Motor: " + (isMotorRunning ? "RUNNING" : "STOPPED") + String.format("%33s║", ""));
        System.out.println("║ Algorithm: " + String.format("%-27s║", dispatcher.getAlgorithmName()));
        System.out.println("║ Avg Wait Time: " + String.format("%.2f ms", getAverageWaitTime()) + String.format("%18s║", ""));
        System.out.println("╚════════════════════════════════════════╝\n");
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "id=" + id +
                ", floor=" + currentFloor +
                ", state=" + currentState.getStateName() +
                ", direction=" + currentDirection +
                "}";
    }
}
