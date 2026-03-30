import java.util.List;

public class FCFSStrategy implements ElevatorSelectionStrategy {

    public ElevatorCar selectElevator(List<ElevatorCar> elevators, int floor, Direction direction) {
        for (ElevatorCar car : elevators) {
            if (car.isAvailable() && car.getState() == ElevatorState.IDLE) {
                return car;
            }
        }
        for (ElevatorCar car : elevators) {
            if (car.isAvailable()) {
                return car;
            }
        }
        return null;
    }
}
