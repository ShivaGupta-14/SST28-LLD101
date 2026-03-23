import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Board {
    private int size;
    private List<Snake> snakes;
    private List<Ladder> ladders;
    private Map<Integer, Integer> snakeMap;
    private Map<Integer, Integer> ladderMap;

    public Board(int n, String difficulty) {
        this.size = n * n;
        this.snakes = new ArrayList<>();
        this.ladders = new ArrayList<>();
        this.snakeMap = new HashMap<>();
        this.ladderMap = new HashMap<>();
        placeSnakesAndLadders(n, difficulty);
    }

    private void placeSnakesAndLadders(int n, String difficulty) {
        Random rand = new Random();
        Set<Integer> occupied = new HashSet<>();
        occupied.add(1);
        occupied.add(size);

        int numSnakes = n;
        int numLadders = n;

        boolean isHard = difficulty.equalsIgnoreCase("hard");

        for (int i = 0; i < numSnakes; i++) {
            int head, tail;
            int attempts = 0;
            do {
                if (isHard) {
                    head = rand.nextInt(size / 2) + (size / 2) + 1;
                    if (head >= size) head = size - 1;
                    tail = rand.nextInt(head / 2) + 1;
                } else {
                    head = rand.nextInt(size - 3) + 3;
                    tail = rand.nextInt(head - 1) + 1;
                }
                attempts++;
                if (attempts > 1000) break;
            } while (occupied.contains(head) || occupied.contains(tail) || head >= size || tail < 1);

            if (attempts > 1000) break;

            occupied.add(head);
            occupied.add(tail);
            snakes.add(new Snake(head, tail));
            snakeMap.put(head, tail);
        }

        for (int i = 0; i < numLadders; i++) {
            int start, end;
            int attempts = 0;
            do {
                if (isHard) {
                    start = rand.nextInt(size / 3) + 2;
                    int range = Math.max(size / 4, 2);
                    end = start + rand.nextInt(range) + 1;
                    if (end >= size) end = size - 1;
                } else {
                    start = rand.nextInt(size - 3) + 2;
                    end = start + rand.nextInt(size - start - 1) + 1;
                    if (end >= size) end = size - 1;
                }
                attempts++;
                if (attempts > 1000) break;
            } while (occupied.contains(start) || occupied.contains(end) || end >= size || start < 2);

            if (attempts > 1000) break;

            occupied.add(start);
            occupied.add(end);
            ladders.add(new Ladder(start, end));
            ladderMap.put(start, end);
        }
    }

    public int getSize() {
        return size;
    }

    public List<Snake> getSnakes() {
        return snakes;
    }

    public List<Ladder> getLadders() {
        return ladders;
    }

    public int getNewPosition(int position) {
        if (snakeMap.containsKey(position)) {
            int tail = snakeMap.get(position);
            System.out.println("  Bitten by snake at " + position + "! Slid down to " + tail);
            return tail;
        }
        if (ladderMap.containsKey(position)) {
            int top = ladderMap.get(position);
            System.out.println("  Found ladder at " + position + "! Climbed up to " + top);
            return top;
        }
        return position;
    }
}
