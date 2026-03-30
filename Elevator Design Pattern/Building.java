/**
 * Building class manages the building structure with multiple floors
 * Composite Pattern for managing floors
 */
import java.util.*;

public class Building {
    private int minFloor;
    private int maxFloor;
    private Map<Integer, Floor> floors;
    private List<Elevator> elevators;

    public Building(int minFloor, int maxFloor) {
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.floors = new TreeMap<>();
        this.elevators = new ArrayList<>();
        
        // Initialize floors
        for (int i = minFloor; i <= maxFloor; i++) {
            floors.put(i, new Floor(i, minFloor, maxFloor));
        }
    }

    public void addElevator(Elevator elevator) {
        elevators.add(elevator);
        
        // Register elevator as button observer for all floors
        for (Floor floor : floors.values()) {
            floor.registerButtonObserver(elevator);
        }
    }

    public Floor getFloor(int floorNumber) {
        return floors.get(floorNumber);
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public int getMinFloor() {
        return minFloor;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public void displayBuildingLayout() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║          BUILDING LAYOUT               ║");
        System.out.println("╠════════════════════════════════════════╣");
        for (int i = maxFloor; i >= minFloor; i--) {
            System.out.printf("║ Floor %2d: [  ]                         ║%n", i);
        }
        System.out.println("╚════════════════════════════════════════╝\n");
    }

    @Override
    public String toString() {
        return "Building{" +
                "floors=" + (maxFloor - minFloor + 1) +
                ", elevators=" + elevators.size() +
                "}";
    }
}
