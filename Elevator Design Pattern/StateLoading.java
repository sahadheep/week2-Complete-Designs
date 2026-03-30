/**
 * StateLoading: Elevator is stationary at a floor with doors open
 */
public class StateLoading extends ElevatorState {
    private int floor;
    private long doorOpenTime;
    private static final long DOOR_OPEN_DURATION = 3000; // 3 seconds

    public StateLoading(Elevator elevator, int floor) {
        super(elevator);
        this.floor = floor;
    }

    @Override
    public void enter() {
        System.out.println("  [STATE] Elevator entering LOADING state at Floor " + floor);
        elevator.openDoor();
        doorOpenTime = System.currentTimeMillis();
    }

    @Override
    public void exit() {
        System.out.println("  [STATE] Elevator leaving LOADING state - Door closing");
        elevator.closeDoor();
    }

    @Override
    public void move() {
        // Check if we should close the door and move on
        long elapsedTime = System.currentTimeMillis() - doorOpenTime;
        
        if (!elevator.isDoorObstructed() && elapsedTime > DOOR_OPEN_DURATION) {
            // Door has been open long enough and is not obstructed, close it
            elevator.setState(new StateIdle(elevator));
            elevator.getCurrentState().move(); // Immediately check for next request
        } else if (elevator.isDoorObstructed()) {
            System.out.println("  [DOOR] Object detected - keeping door open!");
            doorOpenTime = System.currentTimeMillis(); // Reset timer
        }
    }

    @Override
    public Direction getDirection() {
        return Direction.IDLE;
    }

    @Override
    public String getStateName() {
        return "LOADING";
    }
}
