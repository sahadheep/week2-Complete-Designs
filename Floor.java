/**
 * Floor class represents a floor in the building
 * Includes up/down call buttons implementing the Observer Pattern
 */
public class Floor {
    private int floorNumber;
    private UpButton upButton;
    private DownButton downButton;
    private java.util.List<ButtonObserver> buttonObservers;
    private int minFloor;
    private int maxFloor;

    public Floor(int floorNumber, int minFloor, int maxFloor) {
        this.floorNumber = floorNumber;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.buttonObservers = new java.util.ArrayList<>();
        this.upButton = new UpButton(this);
        this.downButton = new DownButton(this);
    }

    /**
     * Register a button observer (typically the elevator)
     */
    public void registerButtonObserver(ButtonObserver observer) {
        buttonObservers.add(observer);
    }

    /**
     * Unregister a button observer
     */
    public void unregisterButtonObserver(ButtonObserver observer) {
        buttonObservers.remove(observer);
    }

    /**
     * Notify all observers when a button is pressed
     */
    private void notifyButtonPressed(Direction direction) {
        for (ButtonObserver observer : buttonObservers) {
            observer.onButtonPressed(floorNumber, direction);
        }
    }

    public void pressUpButton() {
        if (floorNumber < maxFloor) {
            System.out.println("  [BUTTON] UP button pressed at Floor " + floorNumber);
            upButton.press();
            notifyButtonPressed(Direction.UP);
        }
    }

    public void pressDownButton() {
        if (floorNumber > minFloor) {
            System.out.println("  [BUTTON] DOWN button pressed at Floor " + floorNumber);
            downButton.press();
            notifyButtonPressed(Direction.DOWN);
        }
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    /**
     * Inner class representing the Up Button
     */
    private class UpButton {
        private Floor floor;
        private boolean isPressed = false;

        public UpButton(Floor floor) {
            this.floor = floor;
        }

        public void press() {
            isPressed = true;
        }

        public void release() {
            isPressed = false;
        }

        public boolean isPressed() {
            return isPressed;
        }
    }

    /**
     * Inner class representing the Down Button
     */
    private class DownButton {
        private Floor floor;
        private boolean isPressed = false;

        public DownButton(Floor floor) {
            this.floor = floor;
        }

        public void press() {
            isPressed = true;
        }

        public void release() {
            isPressed = false;
        }

        public boolean isPressed() {
            return isPressed;
        }
    }

    @Override
    public String toString() {
        return "Floor " + floorNumber;
    }
}
