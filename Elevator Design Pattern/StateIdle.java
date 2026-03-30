/**
 * StateIdle: Elevator is stationary with doors closed, waiting for requests
 */
public class StateIdle extends ElevatorState {

    public StateIdle(Elevator elevator) {
        super(elevator);
    }

    @Override
    public void enter() {
        System.out.println("  [STATE] Elevator entering IDLE state at Floor " + elevator.getCurrentFloor());
        elevator.stopMotor();
        elevator.closeDoor();
    }

    @Override
    public void exit() {
        System.out.println("  [STATE] Elevator leaving IDLE state");
    }

    @Override
    public void move() {
        // From Idle, check if there are any pending requests
        int nextFloor = elevator.getDispatcher().getNextFloor(elevator.getCurrentFloor(), Direction.IDLE);
        if (nextFloor != elevator.getCurrentFloor()) {
            Direction direction = nextFloor > elevator.getCurrentFloor() ? Direction.UP : Direction.DOWN;
            elevator.setDirection(direction);
            
            if (direction == Direction.UP) {
                elevator.setState(new StateMovingUp(elevator));
            } else {
                elevator.setState(new StateMovingDown(elevator));
            }
        }
    }

    @Override
    public Direction getDirection() {
        return Direction.IDLE;
    }

    @Override
    public String getStateName() {
        return "IDLE";
    }
}
