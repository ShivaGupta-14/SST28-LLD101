import java.util.LinkedList;

public class ElevatorCar {
    private int id;
    private int currentFloor;
    private ElevatorState state;
    private int weightLimit;
    private int currentWeight;
    private boolean doorOpen;
    private LinkedList<Integer> destinations;

    public ElevatorCar(int id, int weightLimit) {
        this.id = id;
        this.currentFloor = 0;
        this.state = ElevatorState.IDLE;
        this.weightLimit = weightLimit;
        this.currentWeight = 0;
        this.doorOpen = false;
        this.destinations = new LinkedList<>();
    }

    public void addDestination(int floor) {
        if (state == ElevatorState.UNDER_MAINTENANCE) {
            System.out.println("  Elevator " + id + " is under maintenance. Cannot accept requests.");
            return;
        }
        if (!destinations.contains(floor) && floor != currentFloor) {
            destinations.add(floor);
        }
    }

    public void move() {
        if (state == ElevatorState.UNDER_MAINTENANCE || destinations.isEmpty()) {
            if (destinations.isEmpty() && state != ElevatorState.UNDER_MAINTENANCE) {
                state = ElevatorState.IDLE;
            }
            return;
        }

        if (doorOpen) {
            closeDoor();
            if (doorOpen) return;
        }

        int target = destinations.peek();

        if (currentFloor < target) {
            state = ElevatorState.UP;
            currentFloor++;
            System.out.println("  Elevator " + id + " moving UP to floor " + currentFloor);
        } else if (currentFloor > target) {
            state = ElevatorState.DOWN;
            currentFloor--;
            System.out.println("  Elevator " + id + " moving DOWN to floor " + currentFloor);
        }

        if (currentFloor == target) {
            destinations.poll();
            openDoor();
            System.out.println("  Elevator " + id + " arrived at floor " + currentFloor);
            if (destinations.isEmpty()) {
                state = ElevatorState.IDLE;
            }
        }
    }

    public void openDoor() {
        doorOpen = true;
        System.out.println("  Elevator " + id + " doors OPENED at floor " + currentFloor);
    }

    public void closeDoor() {
        if (currentWeight > weightLimit) {
            System.out.println("  Elevator " + id + " OVERWEIGHT! Doors staying open. Please reduce load.");
            ringAlarm();
            return;
        }
        doorOpen = false;
        System.out.println("  Elevator " + id + " doors CLOSED");
    }

    public void ringAlarm() {
        System.out.println("  *** ALARM *** Elevator " + id + " alarm ringing!");
    }

    public void setWeight(int weight) {
        this.currentWeight = weight;
    }

    public void setMaintenance() {
        this.state = ElevatorState.UNDER_MAINTENANCE;
        this.destinations.clear();
        System.out.println("  Elevator " + id + " is now UNDER MAINTENANCE");
    }

    public void clearMaintenance() {
        this.state = ElevatorState.IDLE;
        System.out.println("  Elevator " + id + " is back in service");
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public ElevatorState getState() {
        return state;
    }

    public int getWeightLimit() {
        return weightLimit;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public boolean isDoorOpen() {
        return doorOpen;
    }

    public LinkedList<Integer> getDestinations() {
        return destinations;
    }

    public boolean isAvailable() {
        return state != ElevatorState.UNDER_MAINTENANCE;
    }
}
