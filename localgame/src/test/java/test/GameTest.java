package test;

import parsing.BoardParser;
import io.deeplay.Game;
import logic.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    @Test
    public void testStartGame() {
        Board finalBoard = BoardParser.parse("b b b b b b b b \n" +
                "w b w w w b b b \n" +
                "w w b b w b w b \n" +
                "w b b b b b w b \n" +
                "w b b b b b w b \n" +
                "w b w b b b w b \n" +
                "w w w w w b w b \n" +
                "w w w b b b b b", 'b', 'w','_');
        Board preFinalBoard = BoardParser.parse("b b b b b b w   \n" +
                "w b w w w b w w \n" +
                "w w b b w w w w \n" +
                "w b b b b b w w \n" +
                "w b b b b b w w \n" +
                "w b w b b b w w \n" +
                "w w w w w b w w \n" +
                "w w w b b b b b ", 'b', 'w','_');
        new Game().startGame(preFinalBoard, new Player.BotPlayer(Cell.BLACK), new Player.BotPlayer(Cell.WHITE), 1 ,"f1", "f2");
        assertEquals(finalBoard, preFinalBoard);
    }
}