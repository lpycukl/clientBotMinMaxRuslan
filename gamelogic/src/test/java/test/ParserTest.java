package test;

import parsing.BoardParser;
import logic.Board;
import logic.Cell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {

    @Test
    void testParsing() {
        final Board expectedBoard1 = new Board();
        final Board parsedBoard1 = BoardParser.parse(
                "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ - + _ _ _ \n" +
                        "_ _ _ + - _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n", '+', '-','_');
        final Board expectedBoard2 = new Board();
        final Board parsedBoard2 = BoardParser.parse(
                "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ - + _ _ _ \n" +
                        "_ _ _ + - _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ _ _ _ _ _", '+', '-','_');
        final Board expectedBoard3 = new Board();
        expectedBoard3.set(3, 2, Cell.BLACK);
        expectedBoard3.set(3, 3, Cell.BLACK);
        final Board parsedBoard3 = BoardParser.parse(
                "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n" +
                        "_ _ + + + _ _ _ \n" +
                        "_ _ _ + - _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ _ _ _ _ _", '+', '-','_');
        final Board expectedBoard4 = new Board();
        expectedBoard4.set(3, 2, Cell.BLACK);
        expectedBoard4.set(3, 3, Cell.BLACK);
        expectedBoard4.set(2, 4, Cell.WHITE);
        expectedBoard4.set(3, 4, Cell.WHITE);
        final Board parsedBoard4 = BoardParser.parse(
                "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ _ - _ _ _ \n" +
                        "_ _ + + - _ _ _ \n" +
                        "_ _ _ + - _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ \n" +
                        "_ _ _ _ _ _ _ _ ", '+', '-','_');

        assertEquals(expectedBoard1, parsedBoard1);
        assertEquals(expectedBoard2, parsedBoard2);
        assertEquals(expectedBoard3, parsedBoard3);
        assertEquals(expectedBoard4, parsedBoard4);
    }
    @Test
    void testParsingOnException() {
        assertThrows(IllegalArgumentException.class, () -> BoardParser.parse(
                "_ _ _ _ _ _ _ _ \n" +
                            "_ _ _ _ _ _ _ _ \n" +
                            "_ _ _ _ _ _ _ _ \n" +
                            "_ _ _ - + _ _ _ \n" +
                            "_ _ _ + - _ _ _ \n" +
                            "_ _ _ _ _ _ _ _ \n" +
                            "_ _ _ _ _ _ _ _ \n" +
                            "_ _ _ _ _ _ _ ", '+', '-','_'));

    }
}