import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private JButton[][] buttons;
    private JLabel missLabel, strikeLabel, totalMissLabel, totalHitLabel;

    private Gameboard board;
    private StatisticsTracker stats;

    public GUI(BattleshipGame game, Gameboard board, StatisticsTracker stats) {
        this.board = board;
        this.stats = stats;

        setTitle("Battleship Game");
        setSize(600,600);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(10,10));
        buttons = new JButton[10][10];

        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                JButton btn = new JButton("~");
                int r = i;
                int c = j;

                btn.addActionListener(e -> handleClick(r,c,btn));
                buttons[i][j] = btn;
                gridPanel.add(btn);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

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

        setVisible(true);
    }

    private void handleClick(int r, int c, JButton btn) {
        Cell cell = board.getCell(r,c);

        if(cell.isClicked()) return;

        cell.setClicked();
        btn.setEnabled(false);

        if(cell.hasShip()){
            btn.setText("X");
            btn.setBackground(Color.RED);

            stats.addHit();

            cell.getShip().hit();

            if(cell.getShip().isSunk()){
                JOptionPane.showMessageDialog(this,"Ship Sunk!");
            }

        } else {
            btn.setText("M");
            btn.setBackground(Color.YELLOW);

            stats.addMiss();

            if(stats.getStrikes() == 3){
                JOptionPane.showMessageDialog(this,"You Lost!");
            }
        }

        updateLabels();
    }

    private void updateLabels(){
        missLabel.setText("Miss: " + stats.getMiss());
        strikeLabel.setText("Strikes: " + stats.getStrikes());
        totalMissLabel.setText("Total Miss: " + stats.getTotalMiss());
        totalHitLabel.setText("Total Hit: " + stats.getTotalHit());
    }

    public void refreshBoard(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                buttons[i][j].setText("~");
                buttons[i][j].setEnabled(true);
            }
        }
    }
}