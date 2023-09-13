package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerPanel extends JPanel {
    private String playerName;
    private JButton inviteButton;

    public PlayerPanel(String playerName) {
        this.playerName = playerName;
        this.setLayout(new BorderLayout());
        JLabel nameLabel = new JLabel(playerName);
        inviteButton = new JButton("Invite");
        inviteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Здесь обработайте логику приглашения игрока
                // Например, показать диалоговое окно, отправить запрос и т.д.
            }
        });
        this.add(nameLabel, BorderLayout.CENTER);
        this.add(inviteButton, BorderLayout.EAST);
    }

}
