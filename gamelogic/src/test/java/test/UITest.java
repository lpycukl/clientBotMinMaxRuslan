package test;

import logic.*;
import org.junit.jupiter.api.Test;
//import ui.UI;


final class UITest {
    @Test
    void testDisplayMove() {
        Board board = new Board();
        Player player = new Player.BotPlayer(Cell.BLACK);
        Move move = new Move(3, 2);
        int moveNumber = 1;
     //   UI.displayMove(moveNumber, board, player, move);
    }
}

