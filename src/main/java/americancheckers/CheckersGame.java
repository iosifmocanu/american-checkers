package americancheckers;

import java.util.ArrayList;
import java.util.List;

public class CheckersGame {
    public static final int SIZE = 8;
    static final char EMPTY = '.';
    static final char RED = 'r';
    static final char RED_KING = 'R';
    static final char BLACK = 'b';
    static final char BLACK_KING = 'B';
    public char[][] board = new char[SIZE][SIZE];
    public boolean redTurn = true;

    public CheckersGame() {
        setupBoard();
    }

    void setupBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if ((row + col) % 2 == 1) {
                    if (row < 3) board[row][col] = BLACK;
                    else if (row > 4) board[row][col] = RED;
                    else board[row][col] = EMPTY;
                } else {
                    board[row][col] = EMPTY;
                }
            }
        }
    }

    public char getPiece(int row, int col) {
        return board[row][col];
    }

    public boolean isPlayerPiece(int row, int col) {
        char piece = board[row][col];
        return redTurn && (piece == RED || piece == RED_KING) ||
                !redTurn && (piece == BLACK || piece == BLACK_KING);
    }

    public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        if (!isValidMove(fromRow, fromCol, toRow, toCol)) return false;

        char piece = board[fromRow][fromCol];
        board[fromRow][fromCol] = EMPTY;
        board[toRow][toCol] = piece;

        if (Math.abs(fromRow - toRow) == 2) {
            int midRow = (fromRow + toRow) / 2;
            int midCol = (fromCol + toCol) / 2;
            board[midRow][midCol] = EMPTY;
        }

        if (piece == RED && toRow == 0) board[toRow][toCol] = RED_KING;
        if (piece == BLACK && toRow == SIZE - 1) board[toRow][toCol] = BLACK_KING;

        redTurn = !redTurn;
        return true;
    }

    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        if (fromRow < 0 || fromRow >= SIZE || fromCol < 0 || fromCol >= SIZE ||
                toRow < 0 || toRow >= SIZE || toCol < 0 || toCol >= SIZE)
            return false;

        char piece = board[fromRow][fromCol];
        char target = board[toRow][toCol];

        if (target != EMPTY) return false;
        if (redTurn && (piece != RED && piece != RED_KING)) return false;
        if (!redTurn && (piece != BLACK && piece != BLACK_KING)) return false;

        int dir = redTurn ? -1 : 1;

        if (Math.abs(toCol - fromCol) == 1 && toRow - fromRow == dir) return true;

        if (Math.abs(toCol - fromCol) == 2 && Math.abs(toRow - fromRow) == 2) {
            int midRow = (fromRow + toRow) / 2;
            int midCol = (fromCol + toCol) / 2;
            char midPiece = board[midRow][midCol];
            if (redTurn && (midPiece == BLACK || midPiece == BLACK_KING)) return true;
            if (!redTurn && (midPiece == RED || midPiece == RED_KING)) return true;
        }

        if (piece == RED_KING || piece == BLACK_KING) {
            if (Math.abs(toRow - fromRow) == 1 && Math.abs(toCol - fromCol) == 1) return true;
            if (Math.abs(toRow - fromRow) == 2 && Math.abs(toCol - fromCol) == 2) {
                int midRow = (fromRow + toRow) / 2;
                int midCol = (fromCol + toCol) / 2;
                char midPiece = board[midRow][midCol];
                if (redTurn && (midPiece == BLACK || midPiece == BLACK_KING)) return true;
                if (!redTurn && (midPiece == RED || midPiece == RED_KING)) return true;
            }
        }

        return false;
    }

    public List<int[]> getAllValidMoves() {
        List<int[]> moves = new ArrayList<>();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (isPlayerPiece(row, col)) {
                    for (int dr = -2; dr <= 2; dr++) {
                        for (int dc = -2; dc <= 2; dc++) {
                            int newRow = row + dr;
                            int newCol = col + dc;
                            if (isValidMove(row, col, newRow, newCol)) {
                                moves.add(new int[]{row, col, newRow, newCol});
                            }
                        }
                    }
                }
            }
        }
        return moves;
    }

    public boolean isGameOver() {
        return getAllValidMoves().isEmpty();
    }

    public String getWinner() {
        return redTurn ? "Black" : "Red";
    }
}
