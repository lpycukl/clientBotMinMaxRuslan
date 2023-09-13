package logic;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Board {
    private final Cell[][] board;
    private static final int BOARD_SIZE = 8;
    private int quantityOfWhite = 2;
    private int quantityOfBlack = 2;
    private final Logger logger = LogManager.getLogger(Board.class);


    public void setQuantityOfWhite(int quantityOfWhite) {
        this.quantityOfWhite = quantityOfWhite;
    }

    public void setQuantityOfBlack(int quantityOfBlack) {
        this.quantityOfBlack = quantityOfBlack;
    }

    /**
     * Создает доску и четыре фишки по центру карты.
     */
    public Board() {

        board = new Cell[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int cow = 0; cow < BOARD_SIZE; cow++) {
                board[row][cow] = Cell.EMPTY;
            }
        }

        board[3][3] = Cell.WHITE;
        board[4][4] = Cell.WHITE;
        board[3][4] = Cell.BLACK;
        board[4][3] = Cell.BLACK;
    }


    /**
     * Установка диска в указанное поле.
     *
     * @param row  - строка.
     * @param col  - колонка.
     * @param cell - клетка, который нужно поставить в поле.
     */
    public void set(int row, int col, Cell cell) {
        checkArgument(row, col);

        if (!board[row][col].equals(cell)) {
            if (board[row][col] == Cell.BLACK) {
                quantityOfWhite++;
                quantityOfBlack--;
            }
            if (board[row][col] == Cell.WHITE) {
                quantityOfWhite--;
                quantityOfBlack++;
            }
            if (board[row][col] == Cell.EMPTY) {
                if (cell == Cell.WHITE) quantityOfWhite++;
                if (cell == Cell.BLACK) quantityOfBlack++;
            }
        }

        board[row][col] = cell;

    }

    /**
     * Возвращает значение cell, которое лежит в поле.
     *
     * @param row - строка.
     * @param col - колонка.
     * @return возвращает клетку.
     */
    public Cell get(int row, int col) {
        checkArgument(row, col);
        return board[row][col];
    }

    public boolean isValidMove(int row, int col, Cell cell) {

        checkArgument(row, col);
        if (board[row][col] != Cell.EMPTY) {
            return false;
        }

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) {
                    continue;
                }

                int r = row + dr;
                int c = col + dc;
                boolean isValidDirection = false;

                while (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE && board[r][c] == cell.reverse()) {
                    r += dr;
                    c += dc;
                    isValidDirection = true;
                }

                if (isValidDirection && r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE && board[r][c] == cell) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Метод getAllAvailableMoves в классе Board предназначен для получения списка всех доступных ходов для указанной
     * фишки (цвета) на текущей доске.
     *
     * @param cell тип фишки (цвет), для которой нужно получить доступные ходы.
     * @return список ходов типа List<Move>, представляющий все доступные ходы для указанной фишки.
     */
    public List<Move> getAllAvailableMoves(Cell cell) {
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (isValidMove(i, j, cell)) {
                    moves.add(new Move(i, j));
                }
            }
        }
        return moves;
    }


    /**
     * Возвращает количество белых клеток.
     */
    public int getQuantityOfWhite() {
        return quantityOfWhite;
    }

    /**
     * Возвращает количество черных клеток.
     */
    public int getQuantityOfBlack() {
        return quantityOfBlack;
    }

    /**
     * Возвращает количество пустых клеток.
     */
    public int getQuantityOfEmpty() {
        return BOARD_SIZE * BOARD_SIZE - quantityOfBlack - quantityOfWhite;
    }

    private void checkArgument(int row, int col) {
        if (row >= BOARD_SIZE || row < 0 || col >= BOARD_SIZE || col < 0) {
            logger.log(Level.ERROR, "Ошибка в передачи координат на доску.");
            throw new IllegalArgumentException();
        }
    }

    /**
     * Возвращает размер доски.
     */
    public int getSize() {
        return BOARD_SIZE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board board1)) return false;
        return Arrays.deepEquals(board, board1.board);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(BOARD_SIZE);
        result = 31 * result + Arrays.deepHashCode(board);
        return result;
    }

    /**
     * Метод создает копию доски.
     *
     * @return копия доски.
     */
    public Board getBoardCopy() {
        Board copy = new Board();
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.arraycopy(this.board[i], 0, copy.board[i], 0, BOARD_SIZE);
        }
        copy.setQuantityOfBlack(this.getQuantityOfBlack());
        copy.setQuantityOfWhite(this.getQuantityOfWhite());
        return copy;
    }

    /**
     * Устанавливает фишку в указанное место, переворачивая другие фишки в соответсвии с логикой игры.
     *
     * @param row - строка.
     * @param col - колонна.
     */
    public void placePiece(int row, int col, Cell playerCell) {
        set(row, col, playerCell);

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) {
                    continue;
                }

                int r = row + dr;
                int c = col + dc;
                boolean isValidDirection = false;
                boolean hasOpponentPiece = false;

                while (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE && board[r][c] == playerCell.reverse()) {
                    r += dr;
                    c += dc;
                    isValidDirection = true;
                    hasOpponentPiece = true;
                }

                if (isValidDirection && r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE && board[r][c] == playerCell && hasOpponentPiece) {
                    while (r != row || c != col) {
                        r -= dr;
                        c -= dc;
                        set(r, c, playerCell);
                    }
                }
            }
        }
    }

    /**
     * Выпоняет метод placePiece, создает копию доски после хода.
     *
     * @param row        - строка.
     * @param col        - колонна.
     * @param playerCell - клетка цвета игрока, осуществляющего ход.
     * @return возвращает копию доски после хода.
     */
    public Board placePieceAndGetCopy(int row, int col, Cell playerCell) {
        Board copy = getBoardCopy();
        copy.placePiece(row, col, playerCell);
        return copy;
    }

    public boolean isGameOver() {
        List<Move> blackMoves = getAllAvailableMoves(Cell.BLACK);
        List<Move> whiteMoves = getAllAvailableMoves(Cell.WHITE);

        return blackMoves.isEmpty() && whiteMoves.isEmpty();
    }

    public Cell getWinner() {
        int blackCount = 0;
        int whiteCount = 0;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (get(row, col) == Cell.BLACK) {
                    blackCount++;
                } else if (get(row, col) == Cell.WHITE) {
                    whiteCount++;
                }
            }
        }

        if (blackCount > whiteCount) {
            return Cell.BLACK;
        } else if (whiteCount > blackCount) {
            return Cell.WHITE;
        } else {
            return Cell.EMPTY;
        }
    }

    public static String displayBoardOnClientWithoutNumbers(final Board board) {
        final int size = board.getSize();

        String boardInSrting = "";
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                final Cell cell = board.get(row, col);
                final String cellSymbol = cell == Cell.BLACK ? "B" : cell == Cell.WHITE ? "W" : "-";
                boardInSrting += cellSymbol + " ";
            }
            boardInSrting += "\n";
        }
        return boardInSrting;
    }
    public static String displayBoardOnClient(final Board board) {
        final int size = board.getSize();

        String boardInSrting = "  ";
        for (int i = 0; i < size; i++) {
            boardInSrting += (i + 1) + " ";
        }
        boardInSrting += "\n";

        for (int row = 0; row < size; row++) {
            boardInSrting += (row + 1) + " ";
            for (int col = 0; col < size; col++) {
                final Cell cell = board.get(row, col);
                final String cellSymbol = cell == Cell.BLACK ? "B" : cell == Cell.WHITE ? "W" : "-";
                boardInSrting += cellSymbol + " ";
            }
            boardInSrting += "\n";
        }
        return boardInSrting;
    }

    public Cell[][] getBoard() {
        return board;
    }
}






