package fr.aallouv;

import fr.aallouv.enums.GameViews;
import fr.aallouv.manager.GameManager;
import fr.aallouv.utils.GenerateRandom;
import fr.aallouv.utils.Logger;

import java.io.IOException;


public class App {

    private static App app;
    private final Logger logger;
    private final GameManager gameManager;

    public App(GameViews gameViews) throws IOException {
        app = this;
        logger = new Logger();
        gameManager = new GameManager(gameViews);
    }


    public static void main(String[] args) throws IOException {
        System.out.println("Hello from My Java Application!");
        System.out.println("This JAR was built with Maven using 'mvn clean package'");
        if (args.length <= 0) {
            System.out.println("Error: No arguments provided. (expected: 'gui' or 'console')");
            return;
        }
        if (!args[0].equals("gui") && !args[0].equals("console")) {
            System.out.println("Error: Invalid argument '" + args[0] + "'. (expected: 'gui' or 'console')");
            return;
        }

        new App(args[0].equals("gui") ? GameViews.GUI : GameViews.CONSOLE);
    }

    public static App getApp() {
        return app;
    }

    public Logger getLogger() {
        return logger;
    }
}
