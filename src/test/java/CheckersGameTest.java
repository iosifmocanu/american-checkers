import americancheckers.CheckersGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CheckersGameTest {
    private CheckersGame game;

    @BeforeEach
    void setUp() {
        game = new CheckersGame();
    }

    @Test
    void testInitialBoardSetup() {
        assertEquals('b', game.getPiece(0, 1));
        assertEquals('r', game.getPiece(5, 0));
        assertEquals('.', game.getPiece(4, 1));
    }

    @Test
    void testValidSimpleMove() {
        game.movePiece(5, 0, 4, 1);
        assertEquals('.', game.getPiece(5, 0));
        assertEquals('r', game.getPiece(4, 1));
    }

    @Test
    void testInvalidMoveToOccupiedSquare() {
        assertTrue(game.movePiece(5, 0, 4, 1)); // Already a piece there initially
    }

    @Test
    void testCaptureMove() {
        game.movePiece(5, 0, 4, 1); // red
        game.movePiece(2, 1, 3, 0); // black
        assertFalse(game.movePiece(4, 1, 2, 1)); // invalid: same spot
        assertFalse(game.movePiece(4, 1, 2, 3)); // red jumps over black
    }

    @Test
    void testGameOverDetection() {
        for (int row = 0; row < CheckersGame.SIZE; row++) {
            for (int col = 0; col < CheckersGame.SIZE; col++) {
                game.board[row][col] = '.';
            }
        }
        game.board[7][0] = 'r';
        game.redTurn = false;
        assertTrue(game.isGameOver());
    }

    @Test
    void testWinnerRed() {
        for (int row = 0; row < CheckersGame.SIZE; row++) {
            for (int col = 0; col < CheckersGame.SIZE; col++) {
                game.board[row][col] = '.';
            }
        }
        game.board[7][0] = 'r';
        game.redTurn = false;
        assertEquals("Red", game.getWinner());
    }

    @Test
    void testWinnerBlack() {
        for (int row = 0; row < CheckersGame.SIZE; row++) {
            for (int col = 0; col < CheckersGame.SIZE; col++) {
                game.board[row][col] = '.';
            }
        }
        game.board[0][1] = 'b';
        game.redTurn = true;
        assertEquals("Black", game.getWinner());
    }
}
