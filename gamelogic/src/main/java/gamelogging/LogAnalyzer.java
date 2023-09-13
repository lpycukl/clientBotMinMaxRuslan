package gamelogging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Класс LogAnalyzer дает возможность анализировать файл с записаью игр, показывать статистику по победам/поражениям,
 * количеству выигранных игр.
 */
public class LogAnalyzer {

    private final static Logger logger = LogManager.getLogger(LogAnalyzer.class);

    /**
     * Метод parseLog записывает статистику по каждой игре в файле с логами игры.
     *
     * @param fileNameReadLog  Название файла в который будет записан ход понятный человеку.
     * @param fileNameWriteLog Название файла с укороченной записью.
     */
    public static void parseLog(final String fileNameReadLog, final String fileNameWriteLog) {
        try (final FileReader file = new FileReader(fileNameReadLog)) {
            final BufferedReader reader = new BufferedReader(file);
            int currentNumberOfLine = 1;
            int idFirstPlayer = 0;
            int idSecondPlayer = 0;
            String line = reader.readLine();
            while (line != null) {
                if (currentNumberOfLine == 2) {
                    idFirstPlayer = calculatePlayerId(line, 'B');
                }
                if (currentNumberOfLine == 11) {
                    idSecondPlayer = calculatePlayerId(line, 'W');
                }
                if (line.startsWith("Winner")) {
                    final String winner = line.substring(8, 13);
                    if (winner.equals("Black")) {
                        addAnalysis(idFirstPlayer, winner, idSecondPlayer, fileNameWriteLog);
                    }
                    if (winner.equals("White")) {
                        addAnalysis(idSecondPlayer, winner, idFirstPlayer, fileNameWriteLog);
                    }

                    currentNumberOfLine = 0;
                }
                currentNumberOfLine++;
                line = reader.readLine();
            }

        } catch (IOException ex) {
            logger.log(Level.ERROR, "Ошибка при попытке парсирования логов.");
        }
    }

    /**
     * Метод calculatePlayerId парсит строку и выдает id игрока
     *
     * @param line  строка.
     * @param color цвет.
     */
    private static int calculatePlayerId(String line, char color) {
        return Integer.parseInt(line.substring(10, line.indexOf(color) - 1));
    }

    /**
     * Метод writeAnalysis записывает статистику по игрокам, которые играют друг с дргуом первый раз.
     *
     * @param idPlayer         id игрока который победил.
     * @param color            цвет победившего игрока.
     * @param idOpponent       id проигравшего.
     * @param fileNameWriteLog Название файла с анализом логов.
     */
    private static void writeAnalysis(final int idPlayer, final String color, final int idOpponent, final String fileNameWriteLog) {
        try (final FileWriter writeForHuman = new FileWriter(fileNameWriteLog, true)) {
            final String textForHuman = String.format("PlayerId %d win vs PlayerId %d by %s %d times%n", idPlayer, idOpponent, color, 1);
            writeForHuman.write(textForHuman);
            writeForHuman.flush();
        } catch (IOException ex) {
            logger.log(Level.ERROR, "Ошибка при попытке использовать метод writeAnalysis.");
        }
    }

    /**
     * Метод addAnalysis записывает статистику по игрокам, которые уже играли друг с дргуом.
     *
     * @param playerId         id игрока который победил.
     * @param color            цвет победившего игрока.
     * @param opponentId       id проигравшего.
     * @param fileNameWriteLog Название файла с анализом логов.
     */
    private static void addAnalysis(final int playerId, final String color, final int opponentId, final String fileNameWriteLog) {
        final Path path = Paths.get(fileNameWriteLog);

        try (final Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8);
             final FileReader file = new FileReader(fileNameWriteLog)) {
            boolean flag = false;
            final BufferedReader reader = new BufferedReader(file);
            String lineFromAnalysis = reader.readLine();
            while (lineFromAnalysis != null) {
                if (lineFromAnalysis.startsWith(String.format("PlayerId %d win vs PlayerId %d", playerId, opponentId))) {
                    flag = true;
                }
                lineFromAnalysis = reader.readLine();
            }
            if (flag) {
                final String lineStart = String.format("PlayerId %d win vs PlayerId %d", playerId, opponentId);
                List<String> list = stream.map(line -> line.startsWith(lineStart) ? changedLine(playerId, opponentId, line, color) : line)
                        .collect(Collectors.toList());
                Files.write(path, list, StandardCharsets.UTF_8);
            } else {
                writeAnalysis(playerId, color, opponentId, fileNameWriteLog);
            }
        } catch (IOException ex) {
            logger.log(Level.ERROR, "Ошибка при попытке использовать метод addAnalysis.");
        }
    }

    /**
     * Метод changedLine создает люнию для перезаписи в файл анализатора логов.
     *
     * @param playerId   id игрока который победил.
     * @param opponentId id проигравшего.
     * @param line       линия для измененияю.
     * @param color      цвет победившег.
     */
    private static String changedLine(int playerId, int opponentId, final String line, final String color) {
        final int parseId = (Integer.parseInt(line.substring(38, line.indexOf("times") - 1)) + 1);
        return String.format("PlayerId %d win vs PlayerId %d by %s %d times", playerId, opponentId, color, parseId);
    }
}
