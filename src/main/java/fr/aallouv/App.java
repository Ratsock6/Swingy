package fr.aallouv;

import fr.aallouv.enums.EGameViews;
import fr.aallouv.gui.MainGUI;
import fr.aallouv.manager.GameManager;
import fr.aallouv.manager.map.MapManager;
import fr.aallouv.manager.entity.EClass;
import fr.aallouv.manager.entity.Hero;
import fr.aallouv.manager.windows.WindowsManager;
import fr.aallouv.utils.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;


public class App {

    private static App app;
    private final Logger logger;
    private final GameManager gameManager;
    private final WindowsManager windowsManager;

    public App(EGameViews gameViews, int numberOfRoom) throws IOException {
        app = this;
        logger = new Logger();

        if (numberOfRoom < 7) {
            numberOfRoom = 7;
            logger.log("[INFO] Incorrect number of room. Minimum 7.");
        }

        gameManager = new GameManager(gameViews, numberOfRoom);
        MapManager mapManager = new MapManager();
        gameManager.setMap(mapManager);
        mapManager.initMap();
        Hero hero = new Hero("Antoine", EClass.WARRIOR);
        gameManager.setHero(hero);
        windowsManager = new WindowsManager();
        gameManager.startGame(hero, mapManager);

        logger.log("End of application.");

    }


    public static void main(String[] args) throws IOException {
        System.out.println("Hello from My Java Application!");
        System.out.println("This JAR was built with Maven using 'mvn clean package'");
        if (args.length == 0) {
            System.out.println("Error: No arguments provided. (expected: 'gui' or 'console')");
            return;
        }
        if (!args[0].equals("gui") && !args[0].equals("console")) {
            System.out.println("Error: Invalid argument '" + args[0] + "'. (expected: 'gui' or 'console')");
            return;
        }

        int numberOfRoom = 7;
        if (args.length == 2)
            numberOfRoom =  Integer.parseInt(args[1]);

        new App(args[0].equals("gui") ? EGameViews.GUI : EGameViews.CONSOLE, numberOfRoom);
    }

    public static App getApp() {
        return app;
    }

    public Logger getLogger() {
        return logger;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public WindowsManager getWindowsManager() {
        return windowsManager;
    }
}
