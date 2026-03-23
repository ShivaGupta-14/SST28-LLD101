public class EntryGate {
    private int gateId;
    private int floorNumber;

    public EntryGate(int gateId, int floorNumber) {
        this.gateId = gateId;
        this.floorNumber = floorNumber;
    }

    public int getGateId() {
        return gateId;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
