import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotService {
    private List<ParkingFloor> floors;
    private List<EntryGate> gates;
    private Map<SlotType, Double> hourlyRates;
    private Map<String, ParkingTicket> activeTickets;
    private SlotAssignmentStrategy assignmentStrategy;
    private int ticketCounter;

    public ParkingLotService(SlotAssignmentStrategy strategy) {
        this.floors = new ArrayList<>();
        this.gates = new ArrayList<>();
        this.hourlyRates = new HashMap<>();
        this.activeTickets = new HashMap<>();
        this.assignmentStrategy = strategy;
        this.ticketCounter = 0;
    }

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public void addGate(EntryGate gate) {
        gates.add(gate);
    }

    public void setHourlyRate(SlotType type, double rate) {
        hourlyRates.put(type, rate);
    }

    public ParkingTicket park(Vehicle vehicle, LocalDateTime entryTime, SlotType requestedSlotType, int entryGateId) {
        SlotType minSlot = getMinSlotType(vehicle.getType());
        if (requestedSlotType.ordinal() < minSlot.ordinal()) {
            System.out.println("  " + vehicle.getType() + " cannot fit in " + requestedSlotType + " slot.");
            return null;
        }

        EntryGate gate = findGate(entryGateId);
        if (gate == null) {
            System.out.println("  Gate " + entryGateId + " not found.");
            return null;
        }

        ParkingSlot slot = assignmentStrategy.findSlot(floors, requestedSlotType, gate);
        if (slot == null) {
            System.out.println("  No available slot for " + vehicle.getLicensePlate());
            return null;
        }

        slot.setOccupied(true);
        ticketCounter++;
        String ticketId = "T-" + ticketCounter;

        ParkingTicket ticket = new ParkingTicket(ticketId, vehicle, slot, entryTime);
        activeTickets.put(ticketId, ticket);

        return ticket;
    }

    public void printStatus() {
        Map<SlotType, int[]> availability = new HashMap<>();
        for (SlotType type : SlotType.values()) {
            availability.put(type, new int[]{0, 0});
        }

        for (ParkingFloor floor : floors) {
            for (ParkingSlot slot : floor.getSlots()) {
                int[] counts = availability.get(slot.getSlotType());
                counts[1]++;
                if (!slot.isOccupied()) {
                    counts[0]++;
                }
            }
        }

        for (SlotType type : SlotType.values()) {
            int[] counts = availability.get(type);
            System.out.println("  " + type + ": " + counts[0] + " available / " + counts[1] + " total");
        }
    }

    public Bill exit(ParkingTicket ticket, LocalDateTime exitTime) {
        long minutes = Duration.between(ticket.getEntryTime(), exitTime).toMinutes();
        long hours = (long) Math.ceil(minutes / 60.0);
        if (hours == 0) hours = 1;

        double rate = hourlyRates.getOrDefault(ticket.getAllocatedSlot().getSlotType(), 0.0);
        double totalAmount = hours * rate;

        ticket.getAllocatedSlot().setOccupied(false);
        activeTickets.remove(ticket.getTicketId());

        return new Bill(ticket, exitTime, hours, totalAmount);
    }

    private SlotType getMinSlotType(VehicleType vehicleType) {
        switch (vehicleType) {
            case TWO_WHEELER: return SlotType.SMALL;
            case CAR: return SlotType.MEDIUM;
            case BUS: return SlotType.LARGE;
            default: return SlotType.LARGE;
        }
    }

    private EntryGate findGate(int gateId) {
        for (EntryGate gate : gates) {
            if (gate.getGateId() == gateId) {
                return gate;
            }
        }
        return null;
    }
}
