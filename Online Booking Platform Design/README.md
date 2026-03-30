# Online Booking Platform Design

## Overview

This project is redesigned as a Java class-based system, similar in style to the elevator design folder.

## Architecture

### Core Components

1. State Machine - Booking lifecycle transitions (Pending, Paid, Confirmed, Timed Out, Cancelled, Refunded)
2. Search Service - Fast filtering by location, price, and category with cache
3. Inventory Service - Consistency-first slot management with optimistic locking
4. Payment Layer - Stripe and PayPal strategy implementations
5. Notification Observer - Async-style status updates to users

### Design Patterns Used

- State Pattern - BookingState, StatePending, StatePaid, StateConfirmed, StateTimedOut, StateCancelled, StateRefunded
- Strategy Pattern - PaymentGateway with StripeGateway and PayPalGateway
- Factory Pattern - PaymentGatewayFactory
- Observer Pattern - BookingObserver and EmailSmsNotifier

## Reliability Features

- Double booking protection with version-checked inventory holds
- Hold timeout support for pending reservations
- Explicit booking state transitions
- Idempotent-friendly flow structure (single booking id per lifecycle)

## How to Run

```bash
javac *.java
java OnlineBookingPlatformDemo
```

## Notes

- This is a design-focused simulation project.
- Replace simulated payment behavior with real gateway SDK/webhooks for production.
