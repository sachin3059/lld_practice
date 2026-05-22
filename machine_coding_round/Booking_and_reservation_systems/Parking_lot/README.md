# Parking Lot Management System

## Problem Statement

Design and implement a Parking Lot Management System that supports multiple parking levels and different types of vehicles.

### Parking Lot Structure

- The parking lot consists of multiple levels.
- Each level consists of multiple rows.
- Each row contains multiple parking spots.
- Parking spots can be configured for different vehicle types.

### Vehicle Types

The parking lot should support the following vehicle types:

1. Motorcycle
2. Car
3. Electric Bike
4. Electric Car

### Parking Rules

- A motorcycle can park in any available spot (Motorcycle or Car spot).
- A car can park only in an available Car spot.
- Electric vehicles must be parked in their respective charging-enabled spots.
- The number of Electric Car and Electric Bike spots should be configurable.

### Payment Support

The parking lot should support vehicles with and without FastTag.

Supported payment methods:

- Cash
- FastTag

### Functional Requirements

Implement the following functionalities:

#### 1. Park a Vehicle

Given a vehicle, the system should:

- Find an appropriate parking spot.
- Allocate the spot to the vehicle.
- Generate and return a parking ticket.

#### 2. Unpark a Vehicle

Given a vehicle (or parking ticket), the system should:

- Calculate parking charges.
- Accept payment using Cash or FastTag.
- Release the occupied parking spot.
- Generate a payment receipt.

#### 3. Find Vehicle by Spot

Given a parking spot, the system should:

- Return the vehicle currently parked in that spot.

### Entities

The design should include (but is not limited to) the following entities:

- Vehicle
- Parking Spot
- Parking Level
- Parking Lot
- Ticket
- Receipt
- Payment

### Expectations

- Focus on object-oriented design.
- Keep the design extensible for future vehicle types and payment methods.
- Handle parking spot allocation efficiently.
- Write clean, maintainable, and working code.
- Time management and simplicity are important considerations.


