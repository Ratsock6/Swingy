package fr.aallouv.gui;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel root = new JPanel(cardLayout);

    public MainGUI() {
        setTitle("Adventure Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setContentPane(root);
        root.add(new GamePanel(this), "GamePanel");
    }

    public void showScreen() {
        cardLayout.show(root, "MainMenu");
    }

}
