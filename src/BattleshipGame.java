public class BattleshipGame {
    private Gameboard board;
    private StatisticsTracker stats;
    private GUI gui;

    public BattleshipGame() {
        board = new Gameboard();
        stats = new StatisticsTracker();
        gui = new GUI(board, stats);
    }

    public static void main(String[] args) {
        new BattleshipGame();
    }
}