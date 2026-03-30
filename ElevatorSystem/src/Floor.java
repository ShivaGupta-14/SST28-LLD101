public class Floor {
    private int floorNumber;
    private boolean hasUpButton;
    private boolean hasDownButton;

    public Floor(int floorNumber, boolean hasUpButton, boolean hasDownButton) {
        this.floorNumber = floorNumber;
        this.hasUpButton = hasUpButton;
        this.hasDownButton = hasDownButton;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public boolean hasUpButton() {
        return hasUpButton;
    }

    public boolean hasDownButton() {
        return hasDownButton;
    }
}
