package ui;

import logic.*;

/**
 * Класс UI отвечает за вывод в консоль текущего состояния доски и положения фишек после каждого хода игрока.
 */
public final class UI {
    /**
     * Метод displayBoard выводит в консоль текущее состояние доски.
     *
     * @param board доска.
     */
    public static void displayBoard(final Board board) {
        final int size = board.getSize();

        System.out.print("  ");
        for (int i = 0; i < size; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();

        for (int row = 0; row < size; row++) {
            System.out.print(row + 1 + " ");
            for (int col = 0; col < size; col++) {
                final Cell cell = board.get(row, col);
                final String cellSymbol = cell == Cell.BLACK ? "B" : cell == Cell.WHITE ? "W" : "-";
                System.out.print(cellSymbol + " ");
            }
            System.out.println();
        }
    }

    /**
     * Метод displayMove выводит в консоль состояние доски и положение фишек после сделанного хода игрока.
     *
     * @param moveNumber номер текущего хода.
     * @param board      доска.
     * @param player     игрок, который сделал ход.
     * @param move       сделанный ход.
     */
    public static void displayMove(final int moveNumber, final Board board, final Player player, final Move move) {
        System.out.println("Ход №" + moveNumber + " игрока " + player.playerId + "с цветом " + player.playerCell + ": " + (move.row + 1) + " " + (move.col + 1) + "\n");
        displayBoard(board);
        System.out.println("W "+ board.getQuantityOfWhite() +"B "+ board.getQuantityOfBlack());

        System.out.println();
    }
}
