package test;

import parsing.BoardParser;
//import gamelogging.Logging;
import logic.*;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class LoggingTest {

    @Test
    void testLogMove() throws IOException {

        try {
            final PrintWriter pwHuman = new PrintWriter("fileForHumanTest");
            final PrintWriter pwBot = new PrintWriter("fileForBotTest");
            pwHuman.close();
            pwBot.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Board parsedBoard = BoardParser.parse(
                """
                        _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ _\s
                        _ _ + + + _ _ _\s
                        _ _ _ + - _ _ _\s
                        _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ _""", '+', '-', '_');
        final Player player = new Player.HumanPlayer(Cell.WHITE);
        FileWriter writeForHuman = new FileWriter("fileForHumanTest", true);
        FileWriter writerForBot = new FileWriter("fileForBotTest", true);

      //  Logging.logMove(parsedBoard, 2, 4, player, 12345, writeForHuman, writerForBot);

        final FileReader humanReader = new FileReader("fileForHumanTest");
        char[] massiveFromFile = new char[201];
        humanReader.read(massiveFromFile);
        final StringBuilder resultFromFile = new StringBuilder();
        for (char c : massiveFromFile
        ) {
            resultFromFile.append(c);
        }

        String expectedResult = """
                PlayerId: 1 WHITE placed his piece on 3 5 and spent on it 12345\r
                               \s
                               \s
                               \s
                    b b b      \s
                      b w      \s
                               \s
                               \s
                               \s
                """;
        assertEquals(resultFromFile.toString(), expectedResult);
    }

    @Test
    void testLogStart() throws IOException {
        FileWriter writeForHuman = new FileWriter("fileForHumanTest", true);
        FileWriter writerForBot = new FileWriter("fileForBotTest", true);
        try {
            final PrintWriter pwHuman = new PrintWriter("fileForHumanTest");
            pwHuman.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    //    Logging.logStart(1, writeForHuman, writerForBot);
        final FileReader humanReader = new FileReader("fileForHumanTest");
        final StringBuilder result = new StringBuilder();
        char[] massiveFromFile = new char[11];
        humanReader.read(massiveFromFile);
        for (char c : massiveFromFile
        ) {
            result.append(c);
        }
        final boolean res = Pattern.matches("Game id 1 \n", result);
        assertTrue(res);
    }

    @Test
    void testLogEnd() throws IOException {
        try {
            final PrintWriter pwHuman = new PrintWriter("fileForHumanTest");
            final PrintWriter pwBot = new PrintWriter("fileForBotTest");
            pwHuman.close();
            pwBot.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileWriter writeForHuman = new FileWriter("fileForHumanTest", true);
        FileWriter writerForBot = new FileWriter("fileForBotTest", true);
        final Board finalBoard = BoardParser.parse("""
                b b b b b b b b\s
                w b w w w b b b\s
                w w b b w b w b\s
                w b b b b b w b\s
                w b b b b b w b\s
                w b w b b b w b\s
                w w w w w b w b\s
                w w w b b b b b""", 'b', 'w', '_');
   //     Logging.logEnd(finalBoard, writeForHuman, writerForBot);
        final FileReader humanReader = new FileReader("fileForHumanTest");
        char[] massiveFromFile = new char[71];
        humanReader.read(massiveFromFile);
        final StringBuilder resultFromFile = new StringBuilder();
        for (char c : massiveFromFile
        ) {
            resultFromFile.append(c);
        }
        final String expectedResult = """
                Number of Black pieces: 38\r
                Number of white pieces: 26.\r
                Winner: Black
                """;
        assertEquals(resultFromFile.toString(), expectedResult);
    }
}
