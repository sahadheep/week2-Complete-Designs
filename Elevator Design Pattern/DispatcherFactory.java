/**
 * Dispatcher Factory - Factory Pattern Implementation
 * Creates different types of dispatchers based on the algorithm type
 */
public class DispatcherFactory {
    
    public enum DispatcherType {
        SCAN,
        FCFS,
        SSTF
    }

    /**
     * Creates a dispatcher based on the specified type
     */
    public static Dispatcher createDispatcher(DispatcherType type, int minFloor, int maxFloor) {
        switch (type) {
            case SCAN:
                return new ScanDispatcher(minFloor, maxFloor);
            case FCFS:
                return new FCFSDispatcher(minFloor, maxFloor);
            case SSTF:
                return new SSTFDispatcher(minFloor, maxFloor);
            default:
                return new ScanDispatcher(minFloor, maxFloor); // Default to SCAN
        }
    }

    /**
     * Creates a SCAN dispatcher (industry standard)
     */
    public static Dispatcher createScanDispatcher(int minFloor, int maxFloor) {
        return createDispatcher(DispatcherType.SCAN, minFloor, maxFloor);
    }

    /**
     * Creates an FCFS dispatcher
     */
    public static Dispatcher createFCFSDispatcher(int minFloor, int maxFloor) {
        return createDispatcher(DispatcherType.FCFS, minFloor, maxFloor);
    }

    /**
     * Creates an SSTF dispatcher
     */
    public static Dispatcher createSSTFDispatcher(int minFloor, int maxFloor) {
        return createDispatcher(DispatcherType.SSTF, minFloor, maxFloor);
    }
}
