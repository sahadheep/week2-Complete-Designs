/**
 * StateMovingUp: Elevator is moving upward
 */
public class StateMovingUp extends ElevatorState {
    private int targetFloor;

    public StateMovingUp(Elevator elevator) {
        super(elevator);
        this.targetFloor = -1;
    }

    @Override
    public void enter() {
        System.out.println("  [STATE] Elevator entering MOVING UP state");
        elevator.startMotor(Direction.UP);
        targetFloor = elevator.getDispatcher().getNextFloor(elevator.getCurrentFloor(), Direction.UP);
    }

    @Override
    public void exit() {
        System.out.println("  [STATE] Elevator leaving MOVING UP state");
    }

    @Override
    public void move() {
        // Accelerate up to the next floor
        int currentFloor = elevator.getCurrentFloor();
        
        if (currentFloor < targetFloor) {
            // Move up gradually
            elevator.setCurrentFloor(currentFloor + 1);
            System.out.println("  [MOTION] ↑ Moving UP - Now at Floor " + elevator.getCurrentFloor());
        }
        
        if (currentFloor == targetFloor) {
            // Reached target floor, switch to loading state
            elevator.stopMotor();
            elevator.setState(new StateLoading(elevator, targetFloor));
        }
    }

    @Override
    public Direction getDirection() {
        return Direction.UP;
    }

    @Override
    public String getStateName() {
        return "MOVING UP";
    }
}
