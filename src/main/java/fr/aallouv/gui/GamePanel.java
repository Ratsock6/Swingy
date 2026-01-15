package fr.aallouv.gui;

import javax.swing.*;

public class GamePanel extends JFrame {

    public GamePanel(MainGUI mainGUI) {
        setTitle("Game Panel");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void updateGameView() {
        // Update the game view logic here
    }

}
