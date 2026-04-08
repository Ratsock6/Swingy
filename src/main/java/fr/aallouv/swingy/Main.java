package fr.aallouv.swingy;

import fr.aallouv.swingy.controller.GameController;
import fr.aallouv.swingy.repository.FileHeroRepository;
import fr.aallouv.swingy.view.console.ConsoleRunner;
import fr.aallouv.swingy.view.console.ConsoleView;
import fr.aallouv.swingy.view.gui.MainWindow;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1 || (!args[0].equals("console") && !args[0].equals("gui"))) {
            System.err.println("Usage: java -jar swingy.jar [console|gui]");
            System.exit(1);
        }

        GameController controller = new GameController(new FileHeroRepository());

        if (args[0].equals("gui")) {
            SwingUtilities.invokeLater(() -> {
                MainWindow window = new MainWindow(controller);
                window.setVisible(true);
            });
        } else {
            ConsoleView consoleView = new ConsoleView();
            controller.setView(consoleView);
            ConsoleRunner runner = new ConsoleRunner(controller, consoleView);
            consoleView.setRunner(runner);
            runner.run();
        }
    }
}
