package fr.aallouv.swingy;

import fr.aallouv.swingy.controller.GameController;
import fr.aallouv.swingy.repository.FileHeroRepository;
import fr.aallouv.swingy.repository.RepositoryFactory;
import fr.aallouv.swingy.view.console.ConsoleRunner;
import fr.aallouv.swingy.view.console.ConsoleView;
import fr.aallouv.swingy.view.gui.MainWindow;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate.validator.internal.util.Version")
                .setLevel(java.util.logging.Level.OFF);
        if (args.length != 1 || (!args[0].equals("console") && !args[0].equals("gui"))) {
            System.err.println("Usage: java -jar swingy.jar [console|gui]");
            System.exit(1);
        }

        GameController controller = new GameController(RepositoryFactory.create());
        ConsoleView consoleView = new ConsoleView();
        ConsoleRunner consoleRunner = new ConsoleRunner(controller, consoleView);
        consoleView.setRunner(consoleRunner);

        boolean guiMode = args[0].equals("gui");

        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow(controller);

            ViewSwitcher switcher = new ViewSwitcher(controller, consoleView, consoleRunner, window);
            consoleRunner.setSwitcher(switcher);
            window.getGamePanel().setSwitcher(switcher);
            window.getMenuPanel().setSwitcher(switcher);

            if (guiMode) {
                controller.setView(window.getGamePanel());
                window.setVisible(true);
            } else {
                controller.setView(consoleView);
                window.setVisible(false);
            }

            Thread consoleThread = new Thread(consoleRunner::run);
            consoleThread.start();
        });
    }
}
