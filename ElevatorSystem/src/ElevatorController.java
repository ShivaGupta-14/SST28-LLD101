import java.util.ArrayList;
import java.util.List;

public class ElevatorController {
    private List<ElevatorCar> elevators;
    private List<Floor> floors;
    private ElevatorSelectionStrategy strategy;
    private int totalFloors;

    public ElevatorController(int numElevators, int numFloors, int weightLimit, ElevatorSelectionStrategy strategy) {
        this.elevators = new ArrayList<>();
        this.floors = new ArrayList<>();
        this.strategy = strategy;
        this.totalFloors = numFloors;

        for (int i = 1; i <= numElevators; i++) {
            elevators.add(new ElevatorCar(i, weightLimit));
        }

        setupFloors();
    }

    private void setupFloors() {
        floors.clear();
        for (int i = 0; i < totalFloors; i++) {
            boolean hasUp = (i < totalFloors - 1);
            boolean hasDown = (i > 0);
            floors.add(new Floor(i, hasUp, hasDown));
        }
    }

    public void requestElevator(int floor, Direction direction) {
        if (floor < 0 || floor >= totalFloors) {
            System.out.println("Invalid floor number.");
            return;
        }

        Floor f = floors.get(floor);
        if (direction == Direction.UP && !f.hasUpButton()) {
            System.out.println("No UP button on floor " + floor + " (top floor).");
            return;
        }
        if (direction == Direction.DOWN && !f.hasDownButton()) {
            System.out.println("No DOWN button on floor " + floor + " (ground floor).");
            return;
        }

        ElevatorCar selected = strategy.selectElevator(elevators, floor, direction);
        if (selected == null) {
            System.out.println("No elevator available right now.");
            return;
        }

        selected.addDestination(floor);
        System.out.println("Elevator " + selected.getId() + " assigned to floor " + floor + " (" + direction + ")");
    }

    public void selectFloorInside(int elevatorId, int floor) {
        if (floor < 0 || floor >= totalFloors) {
            System.out.println("Invalid floor number.");
            return;
        }

        ElevatorCar car = getElevatorById(elevatorId);
        if (car == null) {
            System.out.println("Elevator " + elevatorId + " not found.");
            return;
        }

        car.addDestination(floor);
        System.out.println("Floor " + floor + " selected inside Elevator " + elevatorId);
    }

    public void step() {
        System.out.println("--- Processing step ---");
        for (ElevatorCar car : elevators) {
            car.move();
        }
    }

    public void addFloor() {
        if (floors.size() >= 2) {
            Floor oldTop = floors.get(floors.size() - 1);
            floors.set(floors.size() - 1, new Floor(oldTop.getFloorNumber(), true, oldTop.hasDownButton()));
        }
        totalFloors++;
        floors.add(new Floor(totalFloors - 1, false, true));
        System.out.println("Floor " + (totalFloors - 1) + " added. Total floors: " + totalFloors);
    }

    public void setMaintenance(int elevatorId) {
        ElevatorCar car = getElevatorById(elevatorId);
        if (car != null) {
            car.setMaintenance();
        } else {
            System.out.println("Elevator " + elevatorId + " not found.");
        }
    }

    public void clearMaintenance(int elevatorId) {
        ElevatorCar car = getElevatorById(elevatorId);
        if (car != null) {
            car.clearMaintenance();
        } else {
            System.out.println("Elevator " + elevatorId + " not found.");
        }
    }

    public void setWeight(int elevatorId, int weight) {
        ElevatorCar car = getElevatorById(elevatorId);
        if (car != null) {
            car.setWeight(weight);
            System.out.println("Elevator " + elevatorId + " weight set to " + weight + " kg");
            if (weight > car.getWeightLimit()) {
                System.out.println("  OVERWEIGHT! Limit is " + car.getWeightLimit() + " kg");
                car.openDoor();
                car.ringAlarm();
            }
        } else {
            System.out.println("Elevator " + elevatorId + " not found.");
        }
    }

    public void triggerEmergency() {
        System.out.println("*** EMERGENCY TRIGGERED ***");
        for (ElevatorCar car : elevators) {
            car.getDestinations().clear();
            car.ringAlarm();
            if (!car.isDoorOpen()) {
                car.openDoor();
            }
        }
        System.out.println("All elevators stopped and alarms activated.");
    }

    public void printStatus() {
        System.out.println("========================================");
        System.out.println("        ELEVATOR SYSTEM STATUS");
        System.out.println("========================================");
        System.out.println("Total Floors: " + totalFloors + " (0 to " + (totalFloors - 1) + ")");
        System.out.println("Total Elevators: " + elevators.size());
        System.out.println();
        for (ElevatorCar car : elevators) {
            System.out.println("Elevator " + car.getId() + ":");
            System.out.println("  Floor: " + car.getCurrentFloor());
            System.out.println("  State: " + car.getState());
            System.out.println("  Door: " + (car.isDoorOpen() ? "OPEN" : "CLOSED"));
            System.out.println("  Weight: " + car.getCurrentWeight() + "/" + car.getWeightLimit() + " kg");
            System.out.println("  Pending stops: " + car.getDestinations());
            System.out.println();
        }
        System.out.println("========================================");
    }

    private ElevatorCar getElevatorById(int id) {
        for (ElevatorCar car : elevators) {
            if (car.getId() == id) return car;
        }
        return null;
    }

    public List<ElevatorCar> getElevators() {
        return elevators;
    }

    public int getTotalFloors() {
        return totalFloors;
    }
}
