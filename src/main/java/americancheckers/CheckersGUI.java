package americancheckers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

public class CheckersGUI extends JFrame implements ActionListener {
    private static final int SIZE = 8;
    private JButton[][] buttons = new JButton[SIZE][SIZE];
    private CheckersGame game = new CheckersGame();
    private int selectedRow = -1, selectedCol = -1;
    private boolean playAgainstComputer;

    public CheckersGUI() {
        int option = JOptionPane.showConfirmDialog(this, "Play against computer?", "Choose Opponent", JOptionPane.YES_NO_OPTION);
        playAgainstComputer = (option == JOptionPane.YES_OPTION);

        setTitle("Checkers Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(SIZE, SIZE));

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 20));
                button.setFocusPainted(false);
                button.setBackground((row + col) % 2 == 0 ? Color.LIGHT_GRAY : Color.DARK_GRAY);
                button.setForeground(Color.WHITE);
                button.addActionListener(this);
                buttons[row][col] = button;
                add(button);
            }
        }
        updateBoard();
        setVisible(true);
    }

    private void updateBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                char piece = game.getPiece(row, col);
                buttons[row][col].setText(piece == '.' ? "" : String.valueOf(piece));
            }
        }
    }

    private void highlightValidMoves(int row, int col) {
        resetButtonColors();
        buttons[row][col].setBackground(Color.GREEN);
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (game.isValidMove(row, col, r, c)) {
                    buttons[r][c].setBackground(Color.YELLOW);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (e.getSource() == buttons[row][col]) {
                    if (selectedRow == -1) {
                        if (game.isPlayerPiece(row, col)) {
                            selectedRow = row;
                            selectedCol = col;
                            highlightValidMoves(row, col);
                        }
                    } else {
                        if (game.movePiece(selectedRow, selectedCol, row, col)) {
                            selectedRow = -1;
                            selectedCol = -1;
                            resetButtonColors();
                            updateBoard();

                            if (game.isGameOver()) {
                                JOptionPane.showMessageDialog(this, game.getWinner() + " wins!");
                                System.exit(0);
                            }

                            if (playAgainstComputer && !game.redTurn) {
                                makeComputerMove();
                            }
                        } else {
                            resetButtonColors();
                            selectedRow = -1;
                            selectedCol = -1;
                        }
                    }
                }
            }
        }
    }

    private void makeComputerMove() {
        List<int[]> moves = game.getAllValidMoves();
        if (!moves.isEmpty()) {
            int[] move = moves.get(new Random().nextInt(moves.size()));
            game.movePiece(move[0], move[1], move[2], move[3]);
            updateBoard();
            if (game.isGameOver()) {
                JOptionPane.showMessageDialog(this, game.getWinner() + " wins!");
                System.exit(0);
            }
        }
    }

    private void resetButtonColors() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                buttons[row][col].setBackground((row + col) % 2 == 0 ? Color.LIGHT_GRAY : Color.DARK_GRAY);
            }
        }
    }

}
