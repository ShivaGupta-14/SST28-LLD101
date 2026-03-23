# Multilevel Parking Lot - Low Level Design

## Problem
Multilevel parking lot with different vehicle types, multiple floors, multiple entry gates, and billing based on slot type.

## Class Diagram

```
+------------------+       +-------------------+
| ParkingLotService|------>| SlotAssignment    |
+------------------+       | Strategy (intf)   |
| - floors         |       +-------------------+
| - gates          |       | + findSlot()      |
| - hourlyRates    |       +-------------------+
| - activeTickets  |                ^
+------------------+                |
| + park()         |       +-------------------+
| + printStatus()  |       | NearestSlot       |
| + exit()         |       | Strategy          |
+------------------+       +-------------------+
     |          |
     v          v
+----------+  +------------------+
| EntryGate|  |  ParkingFloor    |----> ParkingSlot
+----------+  +------------------+
| - gateId |  | - floorNumber    |
| - floor  |  | - slots          |
+----------+  +------------------+

+------------------+
|  ParkingTicket   |----> Vehicle, ParkingSlot
+------------------+
| - ticketId       |
| - vehicle        |
| - allocatedSlot  |
| - entryTime      |
+------------------+

+------------------+
|      Bill        |----> ParkingTicket
+------------------+
| - exitTime       |
| - hoursParked    |
| - totalAmount    |
+------------------+

Enums: VehicleType (TWO_WHEELER, CAR, BUS), SlotType (SMALL, MEDIUM, LARGE)
```

## Design Decisions

- Used Strategy Pattern for slot assignment so we can swap the algorithm without changing the service. Right now only NearestSlotStrategy exists.
- Nearest slot = `|slot floor - gate floor| * 100 + slotNumber`. Same floor slots get picked first, then lower slot numbers.
- If requested slot type is full, tries the next bigger type automatically. So a bike can end up in a medium slot if small is full.
- Billing goes by the slot type that got assigned, not the vehicle type.
- Bus requesting a small slot gets rejected since it cant fit.

## How to Run
```bash
cd src
javac *.java
java Main
```
