package gui;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class intro {
    public static void info() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Information");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(630, 900);

            JPanel mainPanel = new JPanel(new BorderLayout());

            String pdfFilePath = "C:\\job\\deeplay\\groupprogect\\game\\reversi-deeplay\\UI\\src\\main\\java\\gui\\Reversi.pdf";

            try {
                PDDocument document = PDDocument.load(new File(pdfFilePath));
                PDFRenderer pdfRenderer = new PDFRenderer(document);

                JPanel pdfPanel = new JPanel();
                pdfPanel.setLayout(new BoxLayout(pdfPanel, BoxLayout.Y_AXIS)); // зменили на BoxLayout

                for (int page = 0; page < document.getNumberOfPages(); page++) {
                    BufferedImage image = pdfRenderer.renderImage(page);
                    JLabel label = new JLabel(new ImageIcon(image));
                    pdfPanel.add(label);
                }

                document.close();

                JScrollPane scrollPane = new JScrollPane(pdfPanel);
                mainPanel.add(scrollPane, BorderLayout.CENTER);

                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose(); // Закрыть окно
                    }
                });
                closeButton.setFocusPainted(false);
                closeButton.setContentAreaFilled(false);
                closeButton.setOpaque(true);
                closeButton.setFont(new Font("Arial", Font.BOLD, 14));
                closeButton.setForeground(Color.WHITE);
                closeButton.setBackground(Color.BLACK);
                closeButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
                closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        closeButton.setBackground(Color.DARK_GRAY);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        closeButton.setBackground(Color.BLACK);
                    }
                });




                mainPanel.add(closeButton, BorderLayout.SOUTH);

                frame.add(mainPanel);
                frame.setLocationRelativeTo(null); // Вывод окна по центру экрана
                frame.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}