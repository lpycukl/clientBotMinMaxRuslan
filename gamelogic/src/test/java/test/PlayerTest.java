package test;

import logic.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    public void testBotPlayerMakeMove() {
        Board board = new Board();
        Player player = new Player.BotPlayer(Cell.BLACK);

        List<Move> availableMoves = board.getAllAvailableMoves(Cell.BLACK);
        Move move = player.makeMove(board);
        assertTrue(availableMoves.contains(move));
    }
    @Test
    void testPlayerId() {
        Cell blackCell = Cell.BLACK;
        Cell whiteCell = Cell.WHITE;

        Player player1 = new Player.HumanPlayer(blackCell);
        Player player2 = new Player.BotPlayer(whiteCell);
        Player player3 = new Player.HumanPlayer(blackCell);

        assertNotEquals(player1.playerId, player2.playerId);
        assertNotEquals(player1.playerId, player3.playerId);
        assertNotEquals(player2.playerId, player3.playerId);

        assertEquals(1, player1.playerId);
        assertEquals(2, player2.playerId);
        assertEquals(3, player3.playerId);
    }
}