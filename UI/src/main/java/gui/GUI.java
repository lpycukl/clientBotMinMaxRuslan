package gui;

import logic.Board;
import logic.Cell;
import logic.Move;
import logic.Player;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GUI extends JFrame {
    private final int BOARD_SIZE = 8;
    private final JButton[][] buttons;
    Color brightOrange = new Color(255, 120, 10);
    private Board board;
    private Player currentPlayer;
    private Player player1;
    private Player player2;
    private JLabel playerLabel;
    private JLabel scoreLabel;
    private JLabel player1InfoLabel;
    private JLabel player2InfoLabel;
    private int blackScore;
    private int whiteScore;
    private ImageIcon blackIcon;
    private ImageIcon whiteIcon;
    private int player1Wins = 0;
    private int player2Wins = 0;

    private JTextArea gameInfoTextArea;
    private JScrollPane gameInfoScrollPane;
    private ArrayList<PlayerPanel> playerList;


    public GUI() {
        super();
        playerList = new ArrayList<>();
        Font customFont = new Font("Arial", Font.BOLD, 25);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        add(titlePanel, BorderLayout.NORTH);

        JTextPane titleTextPane = new JTextPane();
        titleTextPane.setEditable(false);
        titleTextPane.setFont(customFont);
        titleTextPane.setBorder(BorderFactory.createEmptyBorder());
        titleTextPane.setOpaque(false);

        StyledDocument doc = titleTextPane.getStyledDocument();
        Style style = titleTextPane.addStyle("TitleStyle", null);
        StyleConstants.setForeground(style, Color.red);
        StyleConstants.setFontSize(style, 20);

        try {
            doc.insertString(doc.getLength(), "O", style);
            StyleConstants.setForeground(style, new Color(255, 120, 10));
            doc.insertString(doc.getLength(), "T", style);
            StyleConstants.setForeground(style, new Color(255, 120, 10));
            doc.insertString(doc.getLength(), "H", style);
            StyleConstants.setForeground(style, Color.black);
            doc.insertString(doc.getLength(), "E", style);
            StyleConstants.setForeground(style, Color.black);
            doc.insertString(doc.getLength(), "L", style);
            StyleConstants.setForeground(style, Color.black);
            doc.insertString(doc.getLength(), "L", style);
            StyleConstants.setForeground(style, Color.gray);
            doc.insertString(doc.getLength(), "O", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        titlePanel.add(titleTextPane);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        blackIcon = new ImageIcon("D:\\Java\\reversi-deeplay\\UI\\src\\main\\java\\gui\\Black.png");
        whiteIcon = new ImageIcon("D:\\Java\\reversi-deeplay\\UI\\src\\main\\java\\gui\\White.png");

        ImageIcon icon = new ImageIcon("D:\\Java\\reversi-deeplay\\UI\\src\\main\\java\\gui\\icon.png");
        setIconImage(icon.getImage());

        board = new Board();
        blackScore = 0;
        whiteScore = 0;
        currentPlayer = new Player.HumanPlayer(Cell.BLACK);

        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        JPanel boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setPreferredSize(new Dimension(60, 60));
                buttons[row][col].addActionListener(new ButtonListener(row, col));
                boardPanel.add(buttons[row][col]);
            }
        }

        playerLabel = new JLabel("Current player: " + (currentPlayer.playerCell == Cell.BLACK ? "Black" : "White"));
        scoreLabel = new JLabel("Score: Black " + blackScore + " - " + whiteScore + " White");

        Font customFont2 = new Font("Arial", Font.BOLD, 20);
        Font italicCustomFont = customFont2.deriveFont(Font.ITALIC);

        Font customFont3 = new Font("Arial", Font.BOLD, 16);
        Font shotCustomFont = customFont3.deriveFont(Font.ITALIC);

        TitledBorder orangeTitledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.ORANGE, 4),
                "PLAYER INFORMATION",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                italicCustomFont
        );

        orangeTitledBorder.setTitleColor(brightOrange);

        JPanel playerInfoPanel = new JPanel(new GridLayout(3, 2));
        playerInfoPanel.setBorder(orangeTitledBorder);
        playerInfoPanel.setPreferredSize(new Dimension(300, 100));
        player1InfoLabel = new JLabel();
        player2InfoLabel = new JLabel();
        updatePlayerInfo(true);

        JLabel player1Label = new JLabel("                         Player 1: ");
        //player1Label.setFont(shotCustomFont);
        playerInfoPanel.add(player1Label);
        playerInfoPanel.add(player1InfoLabel);

        JLabel player2Label = new JLabel("                         Player 2: ");
        //player2Label.setFont(shotCustomFont);
        playerInfoPanel.add(player2Label);
        playerInfoPanel.add(player2InfoLabel);

        JPanel controlButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton surrenderButton = new JButton("Surrender");
        surrenderButton.setPreferredSize(new Dimension(120, 30));

        surrenderButton.setFocusPainted(false);
        surrenderButton.setContentAreaFilled(false);
        surrenderButton.setOpaque(true);
        surrenderButton.setFont(new Font("Arial", Font.BOLD, 14));
        surrenderButton.setForeground(Color.WHITE);
        surrenderButton.setBackground(Color.DARK_GRAY);
        surrenderButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        surrenderButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                surrenderButton.setBackground(new Color(100, 100, 100));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                surrenderButton.setBackground(Color.DARK_GRAY);
            }
        });
        surrenderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        JButton quitButton = new JButton("Quit Game");
        quitButton.setPreferredSize(new Dimension(120, 30));

        quitButton.setFocusPainted(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setOpaque(true);
        quitButton.setFont(new Font("Arial", Font.BOLD, 14));
        quitButton.setForeground(Color.WHITE);
        quitButton.setBackground(Color.RED);
        quitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        quitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                quitButton.setBackground(new Color(170, 40, 40));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                quitButton.setBackground(Color.RED);
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        controlButtonPanel.add(surrenderButton);
        controlButtonPanel.add(quitButton);


        JButton humanVsBotButton = new JButton("Human vs Bot");
        humanVsBotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player1 = new Player.HumanPlayer(Cell.BLACK);
                player2 = new Player.BotPlayer(Cell.WHITE);
                resetGame();
                gameInfoTextArea.setText("");
                updatePlayerInfo(true);
                currentMoveNumber = 1;
                startHumanVsBotGame();
            }
        });
        humanVsBotButton.setFocusPainted(false);
        humanVsBotButton.setContentAreaFilled(false);
        humanVsBotButton.setOpaque(true);
        humanVsBotButton.setFont(new Font("Arial", Font.BOLD, 14));
        humanVsBotButton.setForeground(Color.WHITE);
        humanVsBotButton.setBackground(new Color(255, 120, 10));
        humanVsBotButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        humanVsBotButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                humanVsBotButton.setBackground(new Color(255, 140, 30));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                humanVsBotButton.setBackground(new Color(255, 120, 10));
            }
        });

        JButton botVsBotButton = new JButton("Bot vs Bot");
        botVsBotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player1 = new Player.BotPlayer(Cell.BLACK);
                player2 = new Player.BotPlayer(Cell.WHITE);
                resetGame();
                gameInfoTextArea.setText("");
                updatePlayerInfo(false);
                currentMoveNumber = 1;
                startBotVsBotGame();
            }
        });
        botVsBotButton.setFocusPainted(false);
        botVsBotButton.setContentAreaFilled(false);
        botVsBotButton.setOpaque(true);
        botVsBotButton.setFont(new Font("Arial", Font.BOLD, 14));
        botVsBotButton.setForeground(Color.WHITE);
        botVsBotButton.setBackground(Color.BLACK);
        botVsBotButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        botVsBotButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botVsBotButton.setBackground(Color.DARK_GRAY);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                botVsBotButton.setBackground(Color.BLACK);
            }
        });

        setLayout(new BorderLayout());
        add(boardPanel, BorderLayout.CENTER);

        JPanel modePanel = new JPanel(new FlowLayout());
        modePanel.add(humanVsBotButton);
        modePanel.add(botVsBotButton);
        add(modePanel, BorderLayout.NORTH);

        JPanel statusPanel = new JPanel(new GridLayout(2, 1));
        statusPanel.add(playerLabel);
        statusPanel.add(scoreLabel);
        add(statusPanel, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(playerInfoPanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.EAST);

        gameInfoTextArea = new JTextArea(10, 20);
        gameInfoTextArea.setEditable(false);
        gameInfoScrollPane = new JScrollPane(gameInfoTextArea);
        gameInfoTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
        rightPanel.add(gameInfoScrollPane, BorderLayout.CENTER);
        rightPanel.add(controlButtonPanel, BorderLayout.SOUTH);

        JPanel additionalInfoPanel = new JPanel(new BorderLayout());
        JLabel additionalInfoLabel = new JLabel("PLAYERS");

        Font newFont = new Font("Arial", Font.BOLD | Font.ITALIC, 18);
        additionalInfoLabel.setFont(newFont);

        Color newTextColor = Color.GRAY;
        additionalInfoLabel.setForeground(newTextColor);
        additionalInfoLabel.add(Box.createVerticalStrut(50), BorderLayout.NORTH);
        additionalInfoLabel.setHorizontalAlignment(JLabel.CENTER);
        additionalInfoPanel.add(additionalInfoLabel, BorderLayout.NORTH);

        JPanel playerListPanel = new JPanel(new GridLayout(0, 1));
        JScrollPane  playersListScrollPane = new JScrollPane(playerListPanel);
        playersListScrollPane.setPreferredSize(new Dimension(200, 50));
        additionalInfoPanel.add(playersListScrollPane, BorderLayout.CENTER);


        JButton refreshButton = new JButton("Update players list");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Здесь добавьте логику для обновления информации о доступных игроках
                // Например, запрос к серверу и получение списка игроков в response
                String[] updatedPlayerNames = new String[]{"Player1", "Player2", "Player3"}; // полученные имена игроков из response
                updatePlayerList(updatedPlayerNames); // Обновляем список игроков в GUI
            }
        });

        refreshButton.setFocusPainted(false);
        refreshButton.setContentAreaFilled(false);
        refreshButton.setOpaque(true);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBackground(Color.GRAY);
        refreshButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(new Color(70, 70, 70));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(Color.GRAY);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(refreshButton);
        additionalInfoPanel.add(buttonPanel, BorderLayout.SOUTH);

        JButton InfoButton = new JButton("GAME INFO");
        InfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                intro.info(); // Вызов метода для открытия PDF
            }
        });

        InfoButton.setFocusPainted(false);
        InfoButton.setContentAreaFilled(false);
        InfoButton.setOpaque(true);
        InfoButton.setFont(new Font("Arial", Font.BOLD, 14));
        InfoButton.setForeground(Color.WHITE);
        InfoButton.setBackground(Color.DARK_GRAY);
        InfoButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        InfoButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                InfoButton.setBackground(new Color(100, 100, 100));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                InfoButton.setBackground(Color.DARK_GRAY);
            }
        });

        JPanel buttonPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel1.add(InfoButton);


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(additionalInfoPanel, BorderLayout.WEST);
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(modePanel, BorderLayout.NORTH);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(buttonPanel1, BorderLayout.SOUTH);

        add(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        updateBoardGUI();

    }

    private void updatePlayerList(String[] playerNames) {
        playerList.clear(); // Очищаем текущий список игроков

        for (String playerName : playerNames) {
            PlayerPanel playerPanel = new PlayerPanel(playerName);
            playerList.add(playerPanel);
        }

        // Обновляем отображение панели с игроками
        JPanel playerListPanel = new JPanel(new GridLayout(0, 1));
        for (PlayerPanel playerPanel : playerList) {
            playerListPanel.add(playerPanel);
        }

        JScrollPane playersListScrollPane = new JScrollPane(playerListPanel);
        playersListScrollPane.setPreferredSize(new Dimension(200, 50));
        playerListPanel.add(playersListScrollPane, BorderLayout.CENTER);

        revalidate(); // Перерисовываем компоненты
        repaint();
    }


    private void updatePlayerInfo(boolean isHumanVsBotMode) {
        if (isHumanVsBotMode) {
            player1InfoLabel.setText("Human");
            player2InfoLabel.setText("Bot");
        } else {
            player1InfoLabel.setText("Bot 1");
            player2InfoLabel.setText("Bot 2");
        }
    }

    private void appendGameInfo(String info) {
        gameInfoTextArea.append(info + "\n");
        JScrollBar verticalScrollBar = gameInfoScrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
    }

    private void updateBoardGUI() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                buttons[row][col].setBackground(board.get(row, col) == Cell.EMPTY ? brightOrange : (board.get(row, col) == Cell.BLACK ? Color.BLACK : Color.WHITE));
            }
        }

        List<Move> availableMoves = board.getAllAvailableMoves(currentPlayer.playerCell);
        for (Move move : availableMoves) {
            buttons[move.row][move.col].setBackground(Color.GRAY);
        }

        playerLabel.setText("Current player: " + (currentPlayer.playerCell == Cell.BLACK ? "Black" : "White"));
        if (currentPlayer.playerCell == Cell.BLACK) {
            playerLabel.setIcon(blackIcon);
        } else {
            playerLabel.setIcon(whiteIcon);
        }
        int blackCount = 0;
        int whiteCount = 0;
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board.get(row, col) == Cell.BLACK) {
                    blackCount++;
                } else if (board.get(row, col) == Cell.WHITE) {
                    whiteCount++;
                }
            }
        }
        scoreLabel.setText("Black pieces: " + blackCount + " - White pieces: " + whiteCount);

    }

    private int currentMoveNumber = 1;

    private void makeHumanMove(int row, int col) {
        if (currentPlayer instanceof Player.HumanPlayer) {
            Move move = new Move(row, col);
            List<Move> availableMoves = board.getAllAvailableMoves(currentPlayer.playerCell);

            if (availableMoves.contains(move)) {
                board.placePiece(row, col, currentPlayer.playerCell);
                updateBoardGUI();

                if (!board.isGameOver()) {
                    currentPlayer = player2;
                    appendGameInfo(currentMoveNumber + " move Human player moved to: " + (row + 1) + ", " + (col + 1));
                    currentMoveNumber++;
                    synchronized (board) {
                        board.notify();
                    }
                }
            }
        }
    }

    private void startHumanVsBotGame() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int moveNumber = 1;
                while (!board.isGameOver()) {
                    if (currentPlayer instanceof Player.HumanPlayer) {
                        synchronized (board) {
                            try {
                                board.wait();
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    } else if (currentPlayer instanceof Player.BotPlayer) {
                        makeBotMove();
                        currentPlayer = player1;
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                String winner;
                if (board.getWinner() == Cell.BLACK) {
                    winner = "Black";
                    blackScore++;
                    player1Wins++;
                } else if (board.getWinner() == Cell.WHITE) {
                    winner = "White";
                    whiteScore++;
                } else {
                    winner = "Draw";
                    player2Wins++;
                }
                updatePlayerWins(winner);
                JOptionPane.showMessageDialog(GUI.this, "Game Over! The winner is " + winner, "Game Over", JOptionPane.INFORMATION_MESSAGE);

                resetGame();
            }
        }).start();
    }

    private void makeBotMove() {
        if (currentPlayer instanceof Player.BotPlayer) {
            Move move = currentPlayer.makeMove(board);
            board.placePiece(move.row, move.col, currentPlayer.playerCell);
            updateBoardGUI();

            if (!board.isGameOver()) {
                currentPlayer = player1;
                appendGameInfo(currentMoveNumber + " move Bot player moved to: " + (move.row + 1) + ", " + (move.col + 1));
                currentMoveNumber++;
                updateBoardGUI();
            }
        }
    }

    private void startBotVsBotGame() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int moveNumber = 1;
                while (!board.isGameOver()) {
                    if (currentPlayer instanceof Player.BotPlayer) {
                        Move move = currentPlayer.makeMove(board);
                        board.placePiece(move.row, move.col, currentPlayer.playerCell);

                        String botName = currentPlayer == player1 ? "Bot 1 (Black)" : "Bot 2 (White)";
                        appendGameInfo(moveNumber + " move " + botName + " moved to: " + (move.row + 1) + ", " + (move.col + 1));

                        updateBoardGUI();

                        if (currentPlayer.playerCell.equals(Cell.BLACK)) {
                            if (!board.getAllAvailableMoves(Cell.WHITE).isEmpty()) {
                                currentPlayer = currentPlayer.playerCell == Cell.BLACK ? player2 : player1;
                            }

                        } else if (currentPlayer.playerCell.equals(Cell.WHITE)) {
                            if (!board.getAllAvailableMoves(Cell.BLACK).isEmpty()) {
                                currentPlayer = currentPlayer.playerCell == Cell.BLACK ? player2 : player1;
                            }

                        }

                        moveNumber++;

                    }
                    int delayBetweenMoves = 10;
                    try {
                        Thread.sleep(delayBetweenMoves);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

                String winner;
                if (board.getWinner() == Cell.BLACK) {
                    winner = "Black";
                    blackScore++;
                } else if (board.getWinner() == Cell.WHITE) {
                    winner = "White";
                    whiteScore++;
                } else {
                    winner = "Draw";
                }
                JOptionPane.showMessageDialog(GUI.this, "Game Over! The winner is " + winner, "Game Over", JOptionPane.INFORMATION_MESSAGE);

                resetGame();
            }
        }).start();
    }


    private class ButtonListener implements ActionListener {
        private final int row;
        private final int col;

        public ButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            makeHumanMove(row, col);
        }

    }

    private void updatePlayerWinsLabels() {
        player1InfoLabel.setText("Wins: " + player1Wins);
        player2InfoLabel.setText("Wins: " + player2Wins);
    }

    private void updatePlayerWins(String winner) {
        if ("Black".equals(winner)) {
            player1Wins++;
        } else if ("White".equals(winner)) {
            player2Wins++;
        }
        updatePlayerWinsLabels();
    }

    private void resetGame() {
        board = new Board();
        currentPlayer = new Player.HumanPlayer(Cell.BLACK);
        blackScore = 0;
        whiteScore = 0;
        updateBoardGUI();
        updatePlayerInfo(true);

    }
}