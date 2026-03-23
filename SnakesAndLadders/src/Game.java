import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private List<Player> players;
    private List<Player> activePlayers;
    private List<Player> finishedPlayers;
    private Dice dice;

    public Game(int n, int numPlayers, String difficulty) {
        this.board = new Board(n, difficulty);
        this.dice = new Dice();
        this.players = new ArrayList<>();
        this.activePlayers = new ArrayList<>();
        this.finishedPlayers = new ArrayList<>();

        for (int i = 1; i <= numPlayers; i++) {
            Player p = new Player("Player " + i);
            players.add(p);
            activePlayers.add(p);
        }
    }

    public void start() {
        System.out.println("========================================");
        System.out.println("       SNAKES AND LADDERS GAME");
        System.out.println("========================================");
        System.out.println("Board Size: " + board.getSize() + " (" + (int) Math.sqrt(board.getSize()) + "x" + (int) Math.sqrt(board.getSize()) + ")");
        System.out.println("Players: " + players.size());
        System.out.println();

        printSnakesAndLadders();
        System.out.println("----------------------------------------");
        System.out.println("Game begins!\n");

        int round = 1;
        while (activePlayers.size() > 1) {
            System.out.println("--- Round " + round + " ---");

            List<Player> toRemove = new ArrayList<>();

            for (int i = 0; i < activePlayers.size(); i++) {
                Player player = activePlayers.get(i);
                int diceVal = dice.roll();
                int oldPos = player.getPosition();
                int newPos = oldPos + diceVal;

                System.out.println(player.getName() + " (at " + oldPos + ") rolled " + diceVal);

                if (newPos > board.getSize()) {
                    System.out.println("  Can't move beyond the board. Stays at " + oldPos);
                } else if (newPos == board.getSize()) {
                    player.setPosition(newPos);
                    toRemove.add(player);
                    finishedPlayers.add(player);
                    System.out.println("  " + player.getName() + " reached position " + newPos + " and FINISHED!");
                } else {
                    newPos = board.getNewPosition(newPos);
                    player.setPosition(newPos);
                    System.out.println("  " + player.getName() + " is now at position " + newPos);
                }
                System.out.println();
            }

            activePlayers.removeAll(toRemove);
            round++;
        }

        if (activePlayers.size() == 1) {
            finishedPlayers.add(activePlayers.get(0));
        }

        printResults();
    }

    private void printSnakesAndLadders() {
        System.out.println("Snakes on the board:");
        for (Snake s : board.getSnakes()) {
            System.out.println("  " + s.getHead() + " --> " + s.getTail());
        }
        System.out.println("Ladders on the board:");
        for (Ladder l : board.getLadders()) {
            System.out.println("  " + l.getStart() + " --> " + l.getEnd());
        }
        System.out.println();
    }

    private void printResults() {
        System.out.println("========================================");
        System.out.println("           GAME OVER");
        System.out.println("========================================");
        System.out.println("Final Rankings:");
        for (int i = 0; i < finishedPlayers.size(); i++) {
            String rank;
            if (i == 0) rank = "1st";
            else if (i == 1) rank = "2nd";
            else if (i == 2) rank = "3rd";
            else rank = (i + 1) + "th";
            System.out.println("  " + rank + " - " + finishedPlayers.get(i).getName());
        }
        System.out.println("========================================");
    }
}
