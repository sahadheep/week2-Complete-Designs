/**
 * StateMovingDown: Elevator is moving downward
 */
public class StateMovingDown extends ElevatorState {
    private int targetFloor;

    public StateMovingDown(Elevator elevator) {
        super(elevator);
        this.targetFloor = -1;
    }

    @Override
    public void enter() {
        System.out.println("  [STATE] Elevator entering MOVING DOWN state");
        elevator.startMotor(Direction.DOWN);
        targetFloor = elevator.getDispatcher().getNextFloor(elevator.getCurrentFloor(), Direction.DOWN);
    }

    @Override
    public void exit() {
        System.out.println("  [STATE] Elevator leaving MOVING DOWN state");
    }

    @Override
    public void move() {
        // Decelerate down to the next floor
        int currentFloor = elevator.getCurrentFloor();
        
        if (currentFloor > targetFloor) {
            // Move down gradually
            elevator.setCurrentFloor(currentFloor - 1);
            System.out.println("  [MOTION] ↓ Moving DOWN - Now at Floor " + elevator.getCurrentFloor());
        }
        
        if (currentFloor == targetFloor) {
            // Reached target floor, switch to loading state
            elevator.stopMotor();
            elevator.setState(new StateLoading(elevator, targetFloor));
        }
    }

    @Override
    public Direction getDirection() {
        return Direction.DOWN;
    }

    @Override
    public String getStateName() {
        return "MOVING DOWN";
    }
}
