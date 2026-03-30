import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of floors: ");
        int numFloors = sc.nextInt();

        System.out.print("Enter number of elevators: ");
        int numElevators = sc.nextInt();

        System.out.print("Enter weight limit per elevator (kg): ");
        int weightLimit = sc.nextInt();

        String strategyChoice = "";
        while (true) {
            System.out.print("Enter strategy (fcfs/shortest): ");
            strategyChoice = sc.next().trim().toLowerCase();
            if (strategyChoice.equals("fcfs") || strategyChoice.equals("shortest")) {
                break;
            }
            System.out.println("Invalid input. Please enter 'fcfs' or 'shortest'.");
        }

        if (numFloors < 2) {
            System.out.println("Need at least 2 floors.");
            sc.close();
            return;
        }

        if (numElevators < 1) {
            System.out.println("Need at least 1 elevator.");
            sc.close();
            return;
        }

        ElevatorSelectionStrategy strategy;
        if (strategyChoice.equals("fcfs")) {
            strategy = new FCFSStrategy();
        } else {
            strategy = new ShortestSeekStrategy();
        }

        System.out.println();
        ElevatorController controller = new ElevatorController(numElevators, numFloors, weightLimit, strategy);
        controller.printStatus();

        boolean running = true;
        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Call elevator (floor button)");
            System.out.println("2. Select floor inside elevator");
            System.out.println("3. Step (move elevators)");
            System.out.println("4. Add floor");
            System.out.println("5. Set maintenance");
            System.out.println("6. Clear maintenance");
            System.out.println("7. Set elevator weight");
            System.out.println("8. Emergency stop");
            System.out.println("9. Show status");
            System.out.println("10. Exit");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    System.out.print("Enter floor number: ");
                    int floor = sc.nextInt();
                    String dir = "";
                    while (true) {
                        System.out.print("Direction (up/down): ");
                        dir = sc.next().trim().toLowerCase();
                        if (dir.equals("up") || dir.equals("down")) break;
                        System.out.println("Invalid. Enter 'up' or 'down'.");
                    }
                    Direction d = dir.equals("up") ? Direction.UP : Direction.DOWN;
                    controller.requestElevator(floor, d);
                    break;

                case 2:
                    System.out.print("Enter elevator id: ");
                    int eId = sc.nextInt();
                    System.out.print("Enter destination floor: ");
                    int destFloor = sc.nextInt();
                    controller.selectFloorInside(eId, destFloor);
                    break;

                case 3:
                    controller.step();
                    break;

                case 4:
                    controller.addFloor();
                    break;

                case 5:
                    System.out.print("Enter elevator id: ");
                    int mId = sc.nextInt();
                    controller.setMaintenance(mId);
                    break;

                case 6:
                    System.out.print("Enter elevator id: ");
                    int cmId = sc.nextInt();
                    controller.clearMaintenance(cmId);
                    break;

                case 7:
                    System.out.print("Enter elevator id: ");
                    int wId = sc.nextInt();
                    System.out.print("Enter weight (kg): ");
                    int weight = sc.nextInt();
                    controller.setWeight(wId, weight);
                    break;

                case 8:
                    controller.triggerEmergency();
                    break;

                case 9:
                    controller.printStatus();
                    break;

                case 10:
                    running = false;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }
}
