package fr.aallouv.gui;

import javax.swing.*;

public class GameWindow extends JFrame {

    private final ViewManager viewManager;

    public GameWindow() {
        super("Swingy");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        viewManager = new ViewManager();
        setContentPane(viewManager);

        setVisible(true);
    }

    public void showView(ViewManager.ViewType view) {
        viewManager.show(view);
    }
}

