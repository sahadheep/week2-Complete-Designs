/**
 * Abstract state class for the State Pattern
 * Defines behavior for each elevator state
 */
public abstract class ElevatorState {
    protected Elevator elevator;

    public ElevatorState(Elevator elevator) {
        this.elevator = elevator;
    }

    public abstract void enter();
    public abstract void exit();
    public abstract void move();
    public abstract Direction getDirection();
    public abstract String getStateName();

    @Override
    public String toString() {
        return getStateName();
    }
}
