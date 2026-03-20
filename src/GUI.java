import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private JButton[][] buttons;
    private JLabel missLabel, strikeLabel, totalMissLabel, totalHitLabel;

    private Gameboard board;
    private StatisticsTracker stats;

    private boolean gameOver = false;

    public GUI(Gameboard board, StatisticsTracker stats) {
        this.board = board;
        this.stats = stats;

        setTitle("Battleship Game");
        setSize(600, 650);
        setLayout(new BorderLayout());

        // TOP PANEL (buttons)
        JPanel topPanel = new JPanel();

        JButton playAgainBtn = new JButton("Play Again");
        JButton quitBtn = new JButton("Quit");

        playAgainBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Are you sure?",
                    "Play Again",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                resetGame();
            }
        });

        quitBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Quit Game?",
                    "Quit",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        topPanel.add(playAgainBtn);
        topPanel.add(quitBtn);
        add(topPanel, BorderLayout.NORTH);

        // GRID
        JPanel gridPanel = new JPanel(new GridLayout(10, 10));
        buttons = new JButton[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton btn = new JButton("~");
                int r = i;
                int c = j;

                btn.addActionListener(e -> handleClick(r, c, btn));
                buttons[i][j] = btn;
                gridPanel.add(btn);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        // STATS PANEL
        JPanel statsPanel = new JPanel();

        missLabel = new JLabel("Miss: 0");
        strikeLabel = new JLabel("Strikes: 0");
        totalMissLabel = new JLabel("Total Miss: 0");
        totalHitLabel = new JLabel("Total Hit: 0");

        statsPanel.add(missLabel);
        statsPanel.add(strikeLabel);
        statsPanel.add(totalMissLabel);
        statsPanel.add(totalHitLabel);

        add(statsPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void handleClick(int r, int c, JButton btn) {
        if (gameOver) return;

        Cell cell = board.getCell(r, c);

        if (cell.isClicked()) return;

        cell.setClicked();
        btn.setEnabled(false);

        if (cell.hasShip()) {
            btn.setText("X");
            btn.setBackground(Color.RED);

            stats.addHit();
            cell.getShip().hit();

            if (cell.getShip().isSunk()) {
                JOptionPane.showMessageDialog(this, "Ship Sunk!");
            }

            if (stats.getTotalHit() == 17) {
                gameOver = true;

                int choice = JOptionPane.showConfirmDialog(this,
                        "You Win! Play Again?",
                        "Game Over",
                        JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    resetGame();
                }
            }

        } else {
            btn.setText("M");
            btn.setBackground(Color.YELLOW);

            stats.addMiss();

            if (stats.getStrikes() == 3) {
                gameOver = true;

                int choice = JOptionPane.showConfirmDialog(this,
                        "You Lost! Play Again?",
                        "Game Over",
                        JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    resetGame();
                }
            }
        }

        updateLabels();
    }

    private void updateLabels() {
        missLabel.setText("Miss: " + stats.getMiss());
        strikeLabel.setText("Strikes: " + stats.getStrikes());
        totalMissLabel.setText("Total Miss: " + stats.getTotalMiss());
        totalHitLabel.setText("Total Hit: " + stats.getTotalHit());
    }

    private void resetGame() {
        board.resetBoard();
        stats.reset();
        gameOver = false;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                buttons[i][j].setText("~");
                buttons[i][j].setEnabled(true);
                buttons[i][j].setBackground(null);
            }
        }

        updateLabels();
    }
}