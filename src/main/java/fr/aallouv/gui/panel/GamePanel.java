package fr.aallouv.gui.panel;

import fr.aallouv.App;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public GamePanel() {
        setLayout(new BorderLayout());

        add(createTopBar(), BorderLayout.NORTH);
        //add(createGameArea(), BorderLayout.CENTER);
        add(createConsole(), BorderLayout.SOUTH);
    }

    private JComponent createTopBar() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Hero stats"));
        panel.add(new JLabel("HP: " + App.getApp().getGameManager().getHero().getHealth() + "/" + App.getApp().getGameManager().getHero().getMaxHealth()));
        return panel;
    }

    private JComponent createGameArea() {
        return null;
    }

    private JComponent createConsole() {
        JTextArea console = new JTextArea(5, 20);
        console.setEditable(false);
        return new JScrollPane(console);
    }

}
