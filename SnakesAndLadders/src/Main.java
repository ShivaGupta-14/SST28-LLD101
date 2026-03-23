import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter board size (n for nxn board): ");
        int n = sc.nextInt();

        System.out.print("Enter number of players: ");
        int numPlayers = sc.nextInt();

        String difficulty = "";
        while (true) {
            System.out.print("Enter difficulty level (easy/hard): ");
            difficulty = sc.next().trim().toLowerCase();
            if (difficulty.equals("easy") || difficulty.equals("hard")) {
                break;
            }
            System.out.println("Invalid input. Please enter 'easy' or 'hard'.");
        }

        if (n < 5) {
            System.out.println("Board size should be at least 5.");
            sc.close();
            return;
        }

        if (numPlayers < 2) {
            System.out.println("Need at least 2 players to play.");
            sc.close();
            return;
        }

        System.out.println();
        Game game = new Game(n, numPlayers, difficulty);
        game.start();

        sc.close();
    }
}
