import java.util.*;

public class Gameboard {
    private Cell[][] grid;
    private ArrayList<Ship> ships;

    public Gameboard() {
        grid = new Cell[10][10];
        ships = new ArrayList<>();
        initBoard();
    }

    public void initBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = new Cell();
            }
        }
        placeShips();
    }

    public void resetBoard() {
        ships.clear();
        initBoard();
    }

    public Cell getCell(int r, int c) {
        return grid[r][c];
    }

    private void placeShips() {
        int[] sizes = {5, 4, 3, 3, 2};
        Random rand = new Random();

        for (int size : sizes) {
            boolean placed = false;

            while (!placed) {
                boolean horizontal = rand.nextBoolean();
                int row = rand.nextInt(10);
                int col = rand.nextInt(10);

                if (canPlace(row, col, size, horizontal)) {
                    Ship ship = new Ship(size);

                    for (int i = 0; i < size; i++) {
                        int r = horizontal ? row : row + i;
                        int c = horizontal ? col + i : col;

                        grid[r][c].setShip(ship);
                    }

                    ships.add(ship);
                    placed = true;
                }
            }
        }
    }

    private boolean canPlace(int r, int c, int size, boolean horizontal) {
        if (horizontal && c + size > 10) return false;
        if (!horizontal && r + size > 10) return false;

        for (int i = 0; i < size; i++) {
            int row = horizontal ? r : r + i;
            int col = horizontal ? c + i : c;

            if (grid[row][col].hasShip()) return false;
        }

        return true;
    }
}