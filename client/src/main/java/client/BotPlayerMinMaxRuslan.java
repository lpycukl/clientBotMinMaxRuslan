package client;

import logic.Board;
import logic.Cell;
import logic.Move;
import logic.Player;

import java.util.*;

public class BotPlayerMinMaxRuslan extends Player {
    int moveCounter = 1;

    public BotPlayerMinMaxRuslan(Cell playerCell) {
        super(playerCell);

    }

    @Override
    public Move makeMove(Board board) {

        List<Move> availableMoves = board.getAllAvailableMoves(playerCell);
        if (availableMoves.size() == 1) {
            Move move = availableMoves.get(0);
            board.placePiece(move.row, move.col, playerCell);
            return move;
        }
        Move zeroMove = new Move(-1, -1);

        long time1 = System.nanoTime();
        Tree father = new Tree(zeroMove, playerCell.reverse(), board, 1, 5000, playerCell, isCornersEmpty(board), board.getQuantityOfEmpty());
        long time2 = System.nanoTime();

        float timeLastMoves = (float) (time2 - time1) / 1000000000;
        String result = String.format("%.2f", timeLastMoves);
        if (isCornersEmpty(board) && board.getQuantityOfEmpty() > 10)
            System.out.print(moveCounter + " poisk uglov: ");
        else {
            System.out.print(moveCounter + " endspil: ");
        }
        moveCounter++;
        System.out.println(result);
        Move move = father.getGoldMove();
        board.placePiece(move.row, move.col, playerCell);
        return move;
    }

    private boolean isCornersEmpty(Board board) {
        Cell[][] cells = board.getBoard();
        int[] angleCoordinates = {0, 7};
        for (int cord1 : angleCoordinates
        ) {
            for (int cord2 : angleCoordinates
            ) {
                if (cells[cord1][cord2].equals(Cell.EMPTY)) {
                    return true;
                }

            }

        }
        return false;

    }

    public String getPlayerID() {
        // Возвращаем идентификатор бота (например, его уникальный номер)
        return "Bot1"; // Замените на нужный уникальный номер
    }

    private static class Tree {
        static final int MAX_DEEP = 7;//6 пока что условный максимум
        static final int EMPTY_LIMIT = 10;
        static final int MAX_DEEP_EMPTY_LIMIT = 10;
        static final long MAX_TIME = 50;
        private int value = 0;
        private final Move move;

        private Move goldMove;

        private Tree(Move move, Cell whoMadeMove, Board board, int deep, int bestValueTopLevel, Cell fatherCell, boolean isFatherCornersEmpty, int countOfEmptyMotherBoard) {

            this.move = move;

            int maxDeepInThisSituation = MAX_DEEP;

            Cell whoWillMakeMove = whoMadeMove.reverse();
            List<Tree> nodes = new ArrayList<>();


            long timeStart = System.nanoTime();

            if (countOfEmptyMotherBoard <= EMPTY_LIMIT) maxDeepInThisSituation = MAX_DEEP_EMPTY_LIMIT;

            if (deep < maxDeepInThisSituation) createNodes(whoMadeMove, board, deep, fatherCell, bestValueTopLevel, isFatherCornersEmpty, countOfEmptyMotherBoard, timeStart, nodes);

            setValue(board, deep, fatherCell, isFatherCornersEmpty, whoWillMakeMove, maxDeepInThisSituation, nodes);
        }

        private void setValue(Board board, int deep, Cell fatherCell, boolean isFatherCornersEmpty, Cell whoWillMakeMove, int maxDeepInThisSituation, List<Tree> nodes) {
            if (isFatherCornersEmpty && board.getQuantityOfEmpty() > EMPTY_LIMIT) {
                if (deep == maxDeepInThisSituation || Objects.requireNonNull(nodes).isEmpty()) {

                    Cell[][] cells = board.getBoard();
                    int winOrLose = winOrLose(board, fatherCell);

                    if (winOrLose != 0) value = winOrLose;
                    else {

                        //проверяем углы
                        int[][] cornersCoordinates = {{0, 7}, {7, 0}, {0, 0}, {7, 7}};
                        value = value + calculateDeltaValue(cornersCoordinates, 25, fatherCell, cells);

                        //проверяем то, что рядом с углами
                        int[][] closeToCornersCoordinates = {{0, 1}, {1, 0}, {1, 1}, {0, 6}, {1, 6}, {1, 7}, {6, 0}, {6, 1}, {7, 1}, {6, 6}, {6, 7}, {7, 6}};
                        value = value + calculateDeltaValue(closeToCornersCoordinates, 15, fatherCell.reverse(), cells);

                        //проверяем то, что далеко от углов
                        int[][] notCloseToCornersCoordinates = {{0, 2}, {1, 2}, {2, 2}, {2, 1}, {2, 0}, {0, 5}, {1, 5}, {2, 5}, {2, 6}, {2, 7}, {5, 0}, {5, 1}, {5, 2}, {6, 2}, {7, 2}, {5, 5}, {5, 6}, {5, 7}, {6, 5}, {7, 5}};
                        value = value + calculateDeltaValue(notCloseToCornersCoordinates, 7, fatherCell, cells);
                    }
                } else {

                    if (whoWillMakeMove.equals(fatherCell)) setValueNodeOurColor(deep, nodes);
                    else setValueNodeEnemyColor(nodes);

                }


            } else {
                if (deep == maxDeepInThisSituation || Objects.requireNonNull(nodes).isEmpty()) {
                    int winOrLose = winOrLose(board, fatherCell);
                    if (winOrLose != 0) value = winOrLose;
                    else {
                        if (fatherCell.equals(Cell.WHITE)) value = board.getQuantityOfWhite();
                        if (fatherCell.equals(Cell.BLACK)) value = board.getQuantityOfBlack();
                    }
                } else {
                    if (whoWillMakeMove.equals(fatherCell)) setValueNodeOurColor(deep, nodes);
                    else setValueNodeEnemyColor(nodes);

                }

            }
        }

        private static void createNodes(Cell whoMadeMove, Board board, int deep, Cell fatherCell, int bestValueTopLevel, boolean isFatherCornersEmpty, int countOfEmptyMotherBoard, long timeStart, List<Tree> nodes) {
            boolean createMaxNodes = false;
            int bestValue;
            ArrayList<Integer> sosedi = new ArrayList<>();
            List<Move> availableMoves = board.getAllAvailableMoves(whoMadeMove.reverse());
            if (whoMadeMove == fatherCell) {
                createMaxNodes = true;
                bestValue = 5000;
            } else {
                bestValue = -5000;
            }
            for (Move thisMove : availableMoves
            ) {
                long timeEnd = System.nanoTime();
                if ((timeEnd - timeStart) / 100000000 > MAX_TIME) {
                    System.out.println((timeEnd - timeStart) / 100000000);
                    break;
                }
                Board boardAfterMove = board.placePieceAndGetCopy(thisMove.row, thisMove.col, whoMadeMove.reverse());

                if (!sosedi.isEmpty()) {
                    if (createMaxNodes) bestValue = Collections.min(sosedi);
                    if (!createMaxNodes) bestValue = Collections.max(sosedi);
                }

                Tree newNode = new Tree(thisMove, whoMadeMove.reverse(), boardAfterMove, deep + 1, bestValue, fatherCell, isFatherCornersEmpty, countOfEmptyMotherBoard);
                sosedi.add(newNode.getValue());
                nodes.add(newNode);
                if (createMaxNodes && newNode.getValue() < bestValueTopLevel) break;
                if (!createMaxNodes && newNode.getValue() > bestValueTopLevel) break;

            }
        }

        private void setValueNodeEnemyColor(List<Tree> nodes) {
            value = 5000;
            for (Tree node : nodes
            ) {
                if (node.getValue() <= value) {
                    value = node.getValue();
                }

            }
        }

        private void setValueNodeOurColor(int deep, List<Tree> nodes) {
            value = -5000;
            for (Tree node : nodes
            ) {
                if (node.getValue() >= value) {
                    value = node.getValue();
                    if (deep == 1) {
                        goldMove = node.getMove();
                    }
                }

            }
        }

        private int calculateDeltaValue(int[][] coordinates, int changeValue, Cell mainCell, Cell[][] cells) {
            int deltaValue = 0;
            for (int[] coord : coordinates
            ) {
                if (cells[coord[0]][coord[1]].equals(mainCell)) {
                    deltaValue = deltaValue + changeValue;
                } else if (cells[coord[0]][coord[1]].equals(mainCell.reverse())) {
                    deltaValue = deltaValue - changeValue;
                }
            }
            return deltaValue;
        }


        public int getValue() {
            return value;
        }

        public Move getGoldMove() {
            return goldMove;
        }

        public Move getMove() {
            return move;
        }

        private int winOrLose(Board board, Cell cell) {
            if (!board.getAllAvailableMoves(Cell.BLACK).isEmpty() || !board.getAllAvailableMoves(Cell.WHITE).isEmpty()) {
                return 0;
            }
            if (board.getQuantityOfBlack() > board.getQuantityOfWhite()) {
                if (cell.equals(Cell.BLACK)) return 5000;
                if (cell.equals(Cell.WHITE)) return -5000;
            }
            if (board.getQuantityOfBlack() < board.getQuantityOfWhite()) {
                if (cell.equals(Cell.BLACK)) return -5000;
                if (cell.equals(Cell.WHITE)) return 5000;
            }
            if (board.getQuantityOfBlack() == board.getQuantityOfWhite()) {
                return -2000;
            }
            return 0;
        }

    }
}




