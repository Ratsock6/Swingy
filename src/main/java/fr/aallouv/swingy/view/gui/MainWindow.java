package fr.aallouv.swingy.view.gui;

import fr.aallouv.swingy.controller.GameController;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel contentPane;
    private final MenuPanel menuPanel;
    private final GamePanel gamePanel;

    public MainWindow(GameController controller) {
        super("Swingy");

        this.cardLayout = new CardLayout();
        this.contentPane = new JPanel(cardLayout);

        this.menuPanel = new MenuPanel(controller, this);
        this.gamePanel = new GamePanel(controller, this);

        contentPane.add(menuPanel, "menu");
        contentPane.add(gamePanel, "game");

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        showMenu();
    }

    public void showMenu() {
        menuPanel.refresh();
        cardLayout.show(contentPane, "menu");
    }

    public void showGame() {
        gamePanel.refresh();
        cardLayout.show(contentPane, "game");
        gamePanel.requestFocusInWindow();
    }

    public MenuPanel getMenuPanel() { return menuPanel; }
    public GamePanel getGamePanel() { return gamePanel; }
}
