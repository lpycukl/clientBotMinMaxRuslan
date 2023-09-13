package logic;

import java.util.*;

/**
 * Абстрактный класс Player представляет игрока в игре.
 * У каждого игрока есть свой тип фишки (клетки) - Cell,
 * который представляет цвет игрока (BLACK или WHITE).
 *
 * Каждый игрок получает уникальный идентификатор, который может быть использован
 * для статистики или для других целей, требующих идентификации игрока.
 */
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Player {
    private static final AtomicInteger playerCounter = new AtomicInteger(0);
    public final int playerId;
    public final Cell playerCell;

    public abstract String getPlayerID();

    /**
     * Конструктор создает объект игрока с указанным типом фишки (клетки) и присваивает
     * ему уникальный идентификатор.
     *
     * @param playerCell тип фишки (клетки) игрока (BLACK или WHITE).
     */
    public Player(Cell playerCell) {
        this.playerId = playerCounter.incrementAndGet();
        this.playerCell = playerCell;
    }

    /**
     * Абстрактный метод makeMove, который должен быть реализован в подклассах.
     * Определяет ход игрока в зависимости от типа игрока (HumanPlayer или BotPlayer).
     *
     * @param board доска, на которой происходит игра.
     * @return возвращает объект Move, представляющий сделанный игроком ход.
     */
    public abstract Move makeMove(Board board);

    /**
     * Подкласс HumanPlayer представляет человеческого игрока, который делает ходы с помощью ввода с клавиатуры.
     */
    public static class HumanPlayer extends Player {
        public static final Scanner scanner = new Scanner(System.in);

        public HumanPlayer(Cell playerCell) {
            super(playerCell);
        }

        @Override
        public Move makeMove(Board board) {
            final List<Move> availableMoves = board.getAllAvailableMoves(playerCell);
            System.out.println("Доступные ходы: ");
            for (Move m : availableMoves) {
                System.out.println(m.row + 1 + " " + (m.col + 1));
            }
            boolean invalidInput = false;
            while (true) {
                Date dateStart = new Date();
                System.out.print("Введите строку и столбец: ");
                String input = scanner.nextLine();
                String[] inputArray = input.trim().split("\\s+");
                if (inputArray.length == 2) {
                    try {
                        int row = Integer.parseInt(inputArray[0]) - 1;
                        int col = Integer.parseInt(inputArray[1]) - 1;
                        final Move move = new Move(row, col);

                        if (availableMoves.contains(move)) {
                            board.placePiece(row, col, playerCell);
                            Date dateEnd = new Date();
                            long finalTime = dateEnd.getTime() - dateStart.getTime();
                            move.setTimeOnMove(finalTime);
                            return move;
                        } else {
                            System.out.println("Недопустимый ход! Пожалуйста, выберите из доступных ходов.");
                            invalidInput = true; // Устанавливаем флаг в true при недопустимом ходе
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Недопустимый ввод! Пожалуйста, введите два числа через пробел.");
                        invalidInput = true; // Устанавливаем флаг в true при недопустимом вводе
                    }
                } else {
                    if (invalidInput) {
                        System.out.println("Недопустимый ввод! Пожалуйста, введите два числа через пробел.");
                    }
                }
            }
        }

        @Override
        public String getPlayerID() {
            // Возвращаем идентификатор игрока (например, имя)
            return "1"; // Замените на нужное имя игрока
        }
    }


    /**
     * Подкласс BotPlayer представляет игрока-бота, который делает случайные ходы.
     */
    public static class BotPlayer extends Player {
        private final Random random;

        public BotPlayer(Cell playerCell) {
            super(playerCell);
            this.random = new Random();
        }

        @Override
        public Move makeMove(Board board) {
            List<Move> availableMoves = board.getAllAvailableMoves(playerCell);
            Move move = availableMoves.get(random.nextInt(availableMoves.size()));
            board.placePiece(move.row, move.col, playerCell);
            return move;
        }

        public String getPlayerID() {
            // Возвращаем идентификатор бота (например, его уникальный номер)
            return "Bot1"; // Замените на нужный уникальный номер
        }
    }

    public static class BotPlayerMinMaxRuslan extends Player {
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

            private int bestValueTopLevel;

            private List<Integer> sosedi = new ArrayList<>();

            private Move goldMove;

            private Tree(Move move, Cell whoMadeMove, Board board, int deep, int bestValueTopLevel, Cell fatherCell, boolean isFatherCornersEmpty, int countOfEmptyMotherBoard) {
                Cell whoWillMakeMove = whoMadeMove.reverse();
                this.move = move;
                this.sosedi = sosedi;
                int maxDeepInThisSituation = MAX_DEEP;
                long timeStart = System.nanoTime();

                if (countOfEmptyMotherBoard <= EMPTY_LIMIT) {
                    maxDeepInThisSituation = MAX_DEEP_EMPTY_LIMIT;
                }

                List<Tree> nodes = new ArrayList<>();
                if (deep < maxDeepInThisSituation) {
                    createNodes(whoMadeMove, board, deep, fatherCell, bestValueTopLevel, isFatherCornersEmpty, countOfEmptyMotherBoard, timeStart, nodes);
                }

//пробегаться не по всем территориям, а по территориям незанятым у отца


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


}



