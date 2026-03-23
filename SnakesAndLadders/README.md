# Snakes and Ladders - Low Level Design

## Problem
Snakes and Ladders game with configurable board size (n x n), multiple players, and easy/hard difficulty.

## Class Diagram

```
+------------------+
|      Main        |
+------------------+
| + main(args)     |
+------------------+
        |
        | creates
        v
+------------------+       +------------------+
|      Game        |------>|      Board       |
+------------------+       +------------------+
| - board          |       | - size           |
| - players: List  |       | - snakes: List   |
| - activePlayers  |       | - ladders: List  |
| - finishedPlayers|       | - snakeMap: Map  |
| - dice           |       | - ladderMap: Map |
+------------------+       +------------------+
| + start()        |       | + getNewPosition()|
| - printResults() |       | + getSize()      |
+------------------+       | + getSnakes()    |
        |                  | + getLadders()   |
        |                  +------------------+
        |                      |          |
        v                      v          v
+------------------+   +----------+  +-----------+
|     Player       |   |  Snake   |  |  Ladder   |
+------------------+   +----------+  +-----------+
| - name           |   | - head   |  | - start   |
| - position       |   | - tail   |  | - end     |
+------------------+   +----------+  +-----------+
| + getName()      |   | + getHead|  | + getStart|
| + getPosition()  |   | + getTail|  | + getEnd  |
| + setPosition()  |   +----------+  +-----------+
+------------------+

+------------------+
|      Dice        |
+------------------+
| - random         |
+------------------+
| + roll()         |
+------------------+

Relationships:
  Game ---has one---> Board
  Game ---has one---> Dice
  Game ---has many--> Player
  Board --has many--> Snake
  Board --has many--> Ladder
```

## Design Decisions

- Board randomly generates n snakes and n ladders. I used an occupied set to make sure no cell is used twice and no cycles happen between snakes and ladders. Position 1 and n*n are reserved.
- Difficulty changes where snakes and ladders get placed. Easy mode spreads them randomly, hard mode puts snakes near the top with longer drops and ladders near the bottom with shorter reach. So hard mode keeps pushing you back near the end.
- Board.getNewPosition() handles all the snake/ladder checking in one place so Game doesn't need to know about the maps directly.
- Game loop runs until fewer than 2 players are left. Players who finish get ranked in order. If dice roll goes past the last cell, player doesn't move.
- Dice is a separate class for rolling 1-6.

## How to Run
```bash
cd src
javac *.java
java Main
```

## Sample Input
```
Enter board size (n for nxn board): 10
Enter number of players: 3
Enter difficulty level (easy/hard): hard
```
