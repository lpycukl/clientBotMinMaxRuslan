package io.deeplay;


import gui.GUI;
import logic.Board;
import logic.Cell;
import logic.Player;
import parsing.BoardParser;

import javax.swing.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import static logic.Player.HumanPlayer.scanner;

public class Application {
    public static void main(String[] args) {
       /* System.out.println("Выберите режим интерфейса:");
        System.out.println("1. Консольный интерфейс");
        System.out.println("2. Графический интерфейс");
        int choice = scanner.nextInt();*/
        int choice = 1;

        if (choice == 1) {
            startConsoleInterface();

        } else if (choice == 2) {
            SwingUtilities.invokeLater(() -> new GUI());

        } else {
            System.out.println("Неверный выбор");
        }
    }

    private static void startConsoleInterface() {
       /* Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите режим игры:");
        System.out.println("1. Human vs Human");
        System.out.println("2. Human vs Bot");
        System.out.println("3. Bot vs Bot");
        int choice = scanner.nextInt();*/
        int choice = 3;

        if (choice == 1) {
            startHumanVsHumanGame();
        } else if (choice == 2) {
            startHumanVsBotGame();
        } else if (choice == 3) {
            startBotVsBotGame();
        }else {
            System.out.println("Неверный выбор");
        }
    }

    private static void startHumanVsBotGame() {
        Board boardParse = BoardParser.parse(
                "B B B B W B B B \n" +
                        "B B B W B B B B \n" +
                        "B B B W W B B B \n" +
                        "B B B W B W B B \n" +
                        "B B B B W W B B \n" +
                        "B B B W B W B B \n" +
                        "B B W B B B B B \n" +
                        "B W - W B W - - ", 'B', 'W', '-');
        final String nonStableId = new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime());
        new Game().startGame(boardParse, new Player.HumanPlayer(Cell.BLACK), new Player.BotPlayer(Cell.WHITE),
                Integer.parseInt(nonStableId), "fileForHuman", "systemFile");
    }
    private static void startHumanVsHumanGame() {
        Board boardParse = BoardParser.parse(

                "w w w w w w w w \n" +
                        "b _ b w w w w w \n" +
                        "_ b b b w w w w \n" +
                        "_ b b w b w w w \n" +
                        "_ _ b w w b w w \n" +
                        "_ b b w b w b w \n" +
                        "_ _ b b w b b b \n" +
                        "b b b b b b b b\n", 'b', 'w', '_');

        System.out.println(boardParse.getAllAvailableMoves(Cell.WHITE));
        System.out.println(boardParse.getQuantityOfWhite());
        System.out.println(boardParse.getAllAvailableMoves(Cell.BLACK));
        System.out.println(boardParse.getQuantityOfBlack());

        final String nonStableId = new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime());
        new Game().startGame(boardParse, new Player.HumanPlayer(Cell.BLACK), new Player.HumanPlayer(Cell.WHITE),
                Integer.parseInt(nonStableId), "fileForHuman", "systemFile");
    }
    private static void startBotVsBotGame() {
        String winner;
        int winnerW=0;
        int winnerB=0;
        int winnerT=0;
        for (int i = 0; i < 100; i++) {
            final String nonStableId = new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime());
            winner = new Game().startGame(new Board(), new Player.BotPlayerMinMaxRuslan(Cell.BLACK), new Player.BotPlayer(Cell.WHITE),
                    Integer.parseInt(nonStableId), "fileForHuman", "systemFile");
            if (winner=="B"){winnerB++;}
            if (winner=="W"){winnerW++;}
            if (winner=="T"){winnerT++;}
        }
        System.out.println("B: "+ winnerB +"  W: "+winnerW+"  T: "+winnerT);
    }

    public static void startGUIInterface() {
        new GUI();
    }
}
