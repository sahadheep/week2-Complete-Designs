# Elevator System Design

## Overview

This is a complete elevator system design implementing industry-standard practices and design patterns.

## Architecture

### Core Components

1. **State Machine** - Manages elevator states (Idle, Moving, Loading)
2. **Dispatcher** - SCAN algorithm for efficient floor assignment
3. **Request Manager** - Collects and prioritizes requests
4. **Motor Controller** - Controls movement and acceleration
5. **Door Logic** - Manages door operations and safety
6. **Building** - Collection of floors with call buttons

### Design Patterns Used

- **State Pattern** - For elevator states (StateUp, StateDown, StateIdle, StateLoading)
- **Strategy Pattern** - For dispatch algorithms (SCAN, FCFS, SSTF)
- **Observer Pattern** - For button/sensor notifications
- **Singleton Pattern** - For Elevator system manager
- **Factory Pattern** - For creating elevator components

## Key Features

✅ Efficient SCAN algorithm (industry standard)
✅ Thread-safe request handling
✅ Safety mechanisms (overload detection, emergency stop)
✅ Door obstruction detection
✅ Power loss simulation
✅ Real-time status tracking
✅ Performance metrics (average wait time, travel time)

## How to Run

```bash
javac *.java
java ElevatorSystemDemo
```

## Algorithm: SCAN (Elevator Algorithm)

The car moves in one direction until:

- It reaches the top/bottom floor, OR
- It has no more requests in that direction

Then it reverses. This minimizes average wait time and prevents starvation.
