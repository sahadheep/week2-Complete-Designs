/**
 * Button Observer interface - Observes button presses
 * Part of the Observer Pattern implementation
 */
public interface ButtonObserver {
    void onButtonPressed(int floor, Direction direction);
}
