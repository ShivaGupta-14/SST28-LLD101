import java.util.List;

public class ShortestSeekStrategy implements ElevatorSelectionStrategy {

    public ElevatorCar selectElevator(List<ElevatorCar> elevators, int floor, Direction direction) {
        ElevatorCar best = null;
        int minDist = Integer.MAX_VALUE;

        for (ElevatorCar car : elevators) {
            if (!car.isAvailable()) continue;
            int dist = Math.abs(car.getCurrentFloor() - floor);
            if (car.getState() == ElevatorState.IDLE && dist < minDist) {
                minDist = dist;
                best = car;
            }
        }

        if (best != null) return best;

        minDist = Integer.MAX_VALUE;
        for (ElevatorCar car : elevators) {
            if (!car.isAvailable()) continue;
            int dist = Math.abs(car.getCurrentFloor() - floor);
            boolean sameDir = false;

            if (direction == Direction.UP && car.getState() == ElevatorState.UP
                    && car.getCurrentFloor() <= floor) {
                sameDir = true;
            }
            if (direction == Direction.DOWN && car.getState() == ElevatorState.DOWN
                    && car.getCurrentFloor() >= floor) {
                sameDir = true;
            }

            if (sameDir && dist < minDist) {
                minDist = dist;
                best = car;
            }
        }

        if (best != null) return best;

        minDist = Integer.MAX_VALUE;
        for (ElevatorCar car : elevators) {
            if (!car.isAvailable()) continue;
            int dist = Math.abs(car.getCurrentFloor() - floor);
            if (dist < minDist) {
                minDist = dist;
                best = car;
            }
        }

        return best;
    }
}
