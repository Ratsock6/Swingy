package fr.aallouv.gui;

import fr.aallouv.gui.panel.EndPanel;
import fr.aallouv.gui.panel.GamePanel;
import fr.aallouv.gui.panel.StartPanel;

import javax.swing.*;
import java.awt.*;


public class ViewManager extends JPanel {

    private final CardLayout layout = new CardLayout();

    public ViewManager() {
        setLayout(layout);

        add(new StartPanel(), ViewType.START.name());
        add(new GamePanel(), ViewType.GAME.name());
        add(new EndPanel(), ViewType.END.name());
    }

    public void show(ViewType view) {
        layout.show(this, view.name());
    }

    public enum ViewType {
        START,
        GAME,
        END
    }
}



