package test;

import parsing.BoardParser;
import logic.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void checkNewBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row == 3 || row == 4) & (col == 3 || col == 4)) {
                    continue;
                } else {
                    assertEquals(Cell.EMPTY, board.get(row, col));
                }
            }
            assertEquals(Cell.WHITE, board.get(4, 4));
            assertEquals(Cell.WHITE, board.get(3, 3));
            assertEquals(Cell.BLACK, board.get(3, 4));
            assertEquals(Cell.BLACK, board.get(4, 3));

        }
    }

    @Test
    void setOnBoard() {
        board.set(1, 3, Cell.WHITE);
        board.set(2, 3, Cell.BLACK);
        assertEquals(Cell.WHITE, board.get(1, 3));
        assertEquals(Cell.BLACK, board.get(2, 3));
        assertThrows(IllegalArgumentException.class, () -> board.set(-2, 3, Cell.WHITE));
    }

    @Test
    void getFromBoard() {
        assertEquals(Cell.WHITE, board.get(3, 3));
        assertEquals(Cell.BLACK, board.get(4, 3));
        assertEquals(Cell.EMPTY, board.get(0, 3));
        assertThrows(IllegalArgumentException.class, () -> board.get(-2, 3));
    }

    @Test
    void getCellsQuantity() {
        assertEquals(2, board.getQuantityOfBlack());
        assertEquals(2, board.getQuantityOfWhite());
        assertEquals(60, board.getQuantityOfEmpty());

        board.set(2, 5, Cell.BLACK);
        assertEquals(3, board.getQuantityOfBlack());
        assertEquals(59, board.getQuantityOfEmpty());

        board.set(5, 5, Cell.BLACK);
        assertEquals(4, board.getQuantityOfBlack());
        assertEquals(58, board.getQuantityOfEmpty());

        board.set(5, 5, Cell.WHITE);
        assertEquals(3, board.getQuantityOfBlack());
        assertEquals(58, board.getQuantityOfEmpty());
    }

    @Test
    void testCopyBoard() {
        assertEquals(board, board.getBoardCopy());
    }

    @Test
    void testPlacePiece() {
        Board boardCopy1 = board.placePieceAndGetCopy(2, 4, Cell.WHITE);
        assertEquals(Cell.WHITE, boardCopy1.get(3, 4));
        assertEquals(Cell.WHITE, boardCopy1.get(2, 4));
        assertEquals(Cell.WHITE, boardCopy1.get(2, 4));
        board.set(2, 2, Cell.WHITE);
        board.set(4, 2, Cell.WHITE);
        board.set(4, 4, Cell.WHITE);
        board.set(2, 3, Cell.BLACK);
        board.set(3, 3, Cell.BLACK);
        board.set(3, 4, Cell.BLACK);
        Board boardCopy2 = board.placePieceAndGetCopy(2, 4, Cell.WHITE);
        assertEquals(Cell.WHITE, boardCopy2.get(2, 3));
        assertEquals(Cell.WHITE, boardCopy2.get(3, 3));
        assertEquals(Cell.WHITE, boardCopy2.get(3, 4));
        assertEquals(Cell.WHITE, boardCopy2.get(2, 4));
    }


    @Test
    void testIsValidMove() {
        assertTrue(board.isValidMove(3, 2, Cell.BLACK));
        assertFalse(board.isValidMove(3, 2, Cell.WHITE));
        assertTrue(board.isValidMove(2, 3, Cell.BLACK));
        assertFalse(board.isValidMove(2, 2, Cell.BLACK));
        board.set(5, 5, Cell.BLACK);
        assertTrue(board.isValidMove(2, 2, Cell.BLACK));
        final Board board1 = BoardParser.parse(
                """
                        _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ _\s
                        _ _ W W W _ _ _\s
                        _ _ W B W _ _ _\s
                        _ _ W W W _ _ _\s
                        _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ _\s
                        """, 'B', 'W','_');
        assertTrue(board1.isValidMove(1, 1, Cell.BLACK));
        assertFalse(board1.isValidMove(1, 2, Cell.BLACK));
        assertTrue(board1.isValidMove(1, 3, Cell.BLACK));
        assertFalse(board1.isValidMove(1, 4, Cell.BLACK));
        assertTrue(board1.isValidMove(1, 5, Cell.BLACK));
        assertFalse(board1.isValidMove(2, 1, Cell.BLACK));
        assertFalse(board1.isValidMove(2, 5, Cell.BLACK));
        assertTrue(board1.isValidMove(3, 1, Cell.BLACK));
        assertTrue(board1.isValidMove(3, 5, Cell.BLACK));
        assertFalse(board1.isValidMove(4, 1, Cell.BLACK));
        assertFalse(board1.isValidMove(4, 5, Cell.BLACK));
        assertTrue(board1.isValidMove(5, 1, Cell.BLACK));
        assertFalse(board1.isValidMove(5, 2, Cell.BLACK));
        assertTrue(board1.isValidMove(5, 3, Cell.BLACK));
        assertFalse(board1.isValidMove(5, 4, Cell.BLACK));
        assertTrue(board1.isValidMove(5, 5, Cell.BLACK));
        final Board board2 = BoardParser.parse(

                "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n" +
                        "_ _ W W W W _ _ \n" +
                        "_ _ W _ W _ _ _ \n" +
                        "_ _ W W W _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n"
                , 'B', 'W', '_');

        assertFalse(board2.isValidMove(1, 1, Cell.BLACK));
        assertFalse(board2.isValidMove(1, 2, Cell.BLACK));
        assertFalse(board2.isValidMove(1, 3, Cell.BLACK));
        assertFalse(board2.isValidMove(1, 4, Cell.BLACK));
        assertFalse(board2.isValidMove(1, 5, Cell.BLACK));
        assertFalse(board2.isValidMove(2, 1, Cell.BLACK));
        assertFalse(board2.isValidMove(2, 5, Cell.BLACK));
        assertFalse(board2.isValidMove(3, 1, Cell.BLACK));
        assertFalse(board2.isValidMove(3, 5, Cell.BLACK));
        assertFalse(board2.isValidMove(4, 1, Cell.BLACK));
        assertFalse(board2.isValidMove(4, 5, Cell.BLACK));
        assertFalse(board2.isValidMove(5, 1, Cell.BLACK));
        assertFalse(board2.isValidMove(5, 2, Cell.BLACK));
        assertFalse(board2.isValidMove(5, 3, Cell.BLACK));
        assertFalse(board2.isValidMove(5, 4, Cell.BLACK));
        assertFalse(board2.isValidMove(5, 5, Cell.BLACK));
        final Board board3 = BoardParser.parse(
                "w w w w w b b b \n" +
                        "w w b b b w b b \n" +
                        "w w w b b w w w \n" +
                        "w w w b w b w w \n" +
                        "w w b w w w w _ \n" +
                        "b w b w w w w w \n" +
                        "b b b w w w w w \n" +
                        "b b b b _ w w w \n"
                , 'b', 'w', '_');
        assertFalse(board3.isValidMove(4, 7, Cell.WHITE));
        assertFalse(board3.isValidMove(7, 4, Cell.WHITE));
        assertTrue(board3.isValidMove(4, 7, Cell.BLACK));
        assertTrue(board3.isValidMove(7, 4, Cell.BLACK));

        final Board board4 = BoardParser.parse(
                "b w w w _ w w b \n" +
                        "w w b w w w w b \n" +
                        "w w b w w w w b \n" +
                        "w w w w w w w b \n" +
                        "w w w b w b w b \n" +
                        "w w b w w w w w \n" +
                        "w b w w w w w w \n" +
                        "w w w w w w w _ \n"
                , 'b', 'w', '_');
        assertFalse(board4.isValidMove(7, 7, Cell.WHITE));
        assertTrue(board4.isValidMove(7, 7, Cell.BLACK));
        assertFalse(board4.isValidMove(0, 4, Cell.WHITE));
        assertTrue(board4.isValidMove(0, 4, Cell.BLACK));
    }

}