public class ParkingSlot {
    private int slotNumber;
    private int floorNumber;
    private SlotType slotType;
    private boolean occupied;

    public ParkingSlot(int slotNumber, int floorNumber, SlotType slotType) {
        this.slotNumber = slotNumber;
        this.floorNumber = floorNumber;
        this.slotType = slotType;
        this.occupied = false;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public SlotType getSlotType() {
        return slotType;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
