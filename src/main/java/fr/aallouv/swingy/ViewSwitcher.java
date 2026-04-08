package fr.aallouv.swingy;

import fr.aallouv.swingy.controller.GameController;
import fr.aallouv.swingy.view.console.ConsoleRunner;
import fr.aallouv.swingy.view.console.ConsoleView;
import fr.aallouv.swingy.view.gui.MainWindow;

import javax.swing.*;

public class ViewSwitcher {

    private final GameController controller;
    private final ConsoleView consoleView;
    private final ConsoleRunner consoleRunner;
    private final MainWindow window;

    public ViewSwitcher(GameController controller, ConsoleView consoleView,
                        ConsoleRunner consoleRunner, MainWindow window) {
        this.controller = controller;
        this.consoleView = consoleView;
        this.consoleRunner = consoleRunner;
        this.window = window;
    }

    public void switchToGui() {
        consoleRunner.setActive(false);
        controller.setView(window.getGamePanel());
        SwingUtilities.invokeLater(() -> {
            window.setVisible(true);
            if (controller.getState() != null) {
                window.getGamePanel().refresh();
                window.showGame();
                if (controller.getState().getHero() != null) {
                    window.getGamePanel().showHeroStats(controller.getState().getHero());
                }
            } else {
                window.showMenu();
            }
        });
        System.out.println("[Basculement vers GUI]");
    }

    public void switchToConsole() {
        SwingUtilities.invokeLater(() -> window.setVisible(false));
        controller.setView(consoleView);
        consoleRunner.setActive(true);
        System.out.println("[Basculement vers Console]");
        if (controller.getState() != null) {
            consoleView.showHeroStats(controller.getState().getHero());
        }
    }
}
