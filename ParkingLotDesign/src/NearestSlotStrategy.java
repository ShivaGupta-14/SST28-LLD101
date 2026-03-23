import java.util.List;

public class NearestSlotStrategy implements SlotAssignmentStrategy {

    public ParkingSlot findSlot(List<ParkingFloor> floors, SlotType requestedType, EntryGate gate) {
        SlotType[] allTypes = SlotType.values();

        for (SlotType type : allTypes) {
            if (type.ordinal() < requestedType.ordinal()) {
                continue;
            }

            ParkingSlot best = null;
            int bestDistance = Integer.MAX_VALUE;

            for (ParkingFloor floor : floors) {
                for (ParkingSlot slot : floor.getSlots()) {
                    if (!slot.isOccupied() && slot.getSlotType() == type) {
                        int dist = Math.abs(slot.getFloorNumber() - gate.getFloorNumber()) * 100
                                   + slot.getSlotNumber();
                        if (dist < bestDistance) {
                            bestDistance = dist;
                            best = slot;
                        }
                    }
                }
            }

            if (best != null) {
                return best;
            }
        }

        return null;
    }
}
