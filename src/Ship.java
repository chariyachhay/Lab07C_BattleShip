public class Ship {
    private int size;
    private int hits;

    public Ship(int size) {
        this.size = size;
        hits = 0;
    }

    public void hit() {
        hits++;
    }

    public boolean isSunk() {
        return hits == size;
    }
}