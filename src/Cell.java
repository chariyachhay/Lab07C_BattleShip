public class Cell {
    private boolean hasShip;
    private boolean clicked;
    private Ship ship;

    public Cell() {
        hasShip = false;
        clicked = false;
    }

    public boolean hasShip() {
        return hasShip;
    }

    public void setShip(Ship s) {
        hasShip = true;
        ship = s;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked() {
        clicked = true;
    }

    public Ship getShip() {
        return ship;
    }
}