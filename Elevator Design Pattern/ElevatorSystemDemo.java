/**
 * ElevatorSystemDemo - Comprehensive demonstration of the elevator system
 * Shows the full lifecycle with SCAN algorithm, safety features, and performance metrics
 */
public class ElevatorSystemDemo {
    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("       ELEVATOR SYSTEM DESIGN - STRUCTURAL DESIGN PATTERNS");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        // 1. CREATE BUILDING
        Building building = new Building(0, 20);
        System.out.println("[SETUP] Building created: " + building);

        // 2. CREATE ELEVATOR WITH SCAN DISPATCHER (INDUSTRY STANDARD)
        ScanDispatcher dispatcher = new ScanDispatcher(0, 20);
        Elevator elevator = new Elevator(1, 0, 20, 1000, dispatcher);
        // Alternative dispatchers:
        // Dispatcher dispatcher = new FCFSDispatcher(0, 20);
        // Dispatcher dispatcher = new SSTFDispatcher(0, 20);
        
        building.addElevator(elevator);
        System.out.println("[SETUP] Elevator 1 created with " + dispatcher.getAlgorithmName());
        System.out.println("[SETUP] Building structure:");
        building.displayBuildingLayout();

        // 3. SIMULATE REQUESTS
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("PHASE 1: SIMULATING USER REQUESTS");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        // Users pressing buttons from different floors
        elevator.requestFloor(5, Direction.UP, Request.RequestType.CAR_CALL);
        try { Thread.sleep(500); } catch (InterruptedException e) { }
        
        elevator.requestFloor(12, Direction.UP, Request.RequestType.CAR_CALL);
        try { Thread.sleep(500); } catch (InterruptedException e) { }
        
        elevator.requestFloor(3, Direction.DOWN, Request.RequestType.CAR_CALL);
        try { Thread.sleep(500); } catch (InterruptedException e) { }
        
        elevator.requestFloor(18, Direction.UP, Request.RequestType.CAR_CALL);
        try { Thread.sleep(500); } catch (InterruptedException e) { }

        // 4. SIMULATE MOVEMENT (Accelerated - 1 second per floor)
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("PHASE 2: MOVEMENT & STATE TRANSITIONS");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        // Simulate 100 time steps
        for (int i = 1; i <= 100; i++) {
            elevator.update();
            
            // Simulate load updates
            if (i % 25 == 0 && i < 100) {
                elevator.updateLoad(200); // Simulate passengers boarding
            }

            // Simulate door obstruction
            if (i == 40) {
                System.out.println("  [SIMULATION] Someone entering, blocking door!");
                elevator.setDoorObstructed(true);
            }
            if (i == 45) {
                System.out.println("  [SIMULATION] Door obstruction cleared");
                elevator.setDoorObstructed(false);
            }

            // Small delay between steps
            try { Thread.sleep(100); } catch (InterruptedException e) { }

            // Show status every 20 steps
            if (i % 20 == 0) {
                elevator.displayStatus();
            }

            // Stop after reaching idle state with no requests
            if (elevator.getCurrentState().getStateName().equals("IDLE") && 
                !dispatcher.hasPendingRequests() && i > 30) {
                System.out.println("[SIMULATION] All requests served. Elevator at rest.");
                break;
            }
        }

        // 5. SAFETY FEATURES DEMONSTRATION
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("PHASE 3: SAFETY FEATURES DEMONSTRATION");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        // Add more requests
        elevator.requestFloor(15, Direction.UP, Request.RequestType.CAR_CALL);
        try { Thread.sleep(300); } catch (InterruptedException e) { }
        
        // Simulate for a bit
        for (int i = 0; i < 20; i++) {
            elevator.update();
            try { Thread.sleep(100); } catch (InterruptedException e) { }
        }

        // Trigger emergency stop
        System.out.println("\n[SAFETY TEST] Triggering Emergency Stop!");
        elevator.triggerEmergencyStop();
        elevator.update(); // Try to move - should not work
        
        try { Thread.sleep(1000); } catch (InterruptedException e) { }

        // Reset emergency
        System.out.println("\n[SAFETY TEST] Resetting Emergency Stop");
        elevator.resetEmergencyStop();

        // 6. PERFORMANCE REPORT
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("PHASE 4: PERFORMANCE METRICS & ANALYSIS");
        System.out.println("═══════════════════════════════════════════════════════════════");
        
        elevator.displayStatus();

        // 7. ALGORITHM COMPARISON
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("ALGORITHM ANALYSIS");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        System.out.println("SCAN Algorithm (Used):");
        System.out.println("  ✓ Prevents starvation - all floors eventually served");
        System.out.println("  ✓ Industry standard for modern elevators");
        System.out.println("  ✓ Minimizes average wait time");
        System.out.println("  ✓ Predictable behavior\n");

        System.out.println("FCFS Algorithm (Alternative):");
        System.out.println("  ✗ Simple but inefficient");
        System.out.println("  ✗ Can waste time traveling between distant floors");
        System.out.println("  ✗ Good for low-traffic scenarios\n");

        System.out.println("SSTF Algorithm (Alternative):");
        System.out.println("  ? Responsive to nearest requests");
        System.out.println("  ✗ Can cause starvation for distant floors");
        System.out.println("  ✗ Not recommended for real systems\n");

        // 8. DESIGN PATTERNS USED
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("DESIGN PATTERNS IMPLEMENTED");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        System.out.println("1. STATE PATTERN");
        System.out.println("   Classes: ElevatorState, StateIdle, StateMovingUp, StateMovingDown, StateLoading");
        System.out.println("   Purpose: Manages elevator state transitions\n");

        System.out.println("2. STRATEGY PATTERN");
        System.out.println("   Classes: Dispatcher, ScanDispatcher, FCFSDispatcher, SSTFDispatcher");
        System.out.println("   Purpose: Interchangeable dispatch algorithms\n");

        System.out.println("3. OBSERVER PATTERN");
        System.out.println("   Classes: Floor, Button interactions");
        System.out.println("   Purpose: Buttons notify elevator of requests\n");

        System.out.println("4. COMPOSITE PATTERN");
        System.out.println("   Classes: Building, Floor");
        System.out.println("   Purpose: Manage building structure\n");

        System.out.println("5. SINGLETON PATTERN (Optional)");
        System.out.println("   Could be applied to: Building, ElevatorController");
        System.out.println("   Purpose: Ensure single instance of building/controller\n");

        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("SIMULATION COMPLETE");
        System.out.println("═══════════════════════════════════════════════════════════════");
    }
}
