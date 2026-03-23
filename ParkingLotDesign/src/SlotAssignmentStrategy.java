import java.util.List;

public interface SlotAssignmentStrategy {
    ParkingSlot findSlot(List<ParkingFloor> floors, SlotType requestedType, EntryGate gate);
}
