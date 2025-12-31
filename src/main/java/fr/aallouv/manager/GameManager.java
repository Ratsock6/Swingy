package fr.aallouv.manager;

import fr.aallouv.App;
import fr.aallouv.enums.EGameViews;
import fr.aallouv.manager.map.EMapRoom;
import fr.aallouv.manager.map.MapManager;
import fr.aallouv.manager.map.SlotMap;
import fr.aallouv.manager.entity.Hero;

import java.util.Scanner;

public class GameManager {

    private EGameViews gameViews;
    private MapManager map;
    private int numberOfRooms;
    private Hero hero;

    public GameManager(EGameViews gameViews, int numberOfRooms) {
        this.gameViews = gameViews;
        this.numberOfRooms = numberOfRooms;
        this.map = null;
        this.hero = null;
        App.getApp().getLogger().log("GameManager initialized with view: " + gameViews.name());
    }

    public  EGameViews getGameViews() {
        return gameViews;
    }

    public void setGameViews(EGameViews gameViews) {
        this.gameViews = gameViews;
    }

    public void toggleGameView() {
        if (gameViews == EGameViews.GUI) {
            gameViews = EGameViews.CONSOLE;
            gameLoop();
            return;
        }
        gameViews = EGameViews.GUI;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setMap(MapManager map) {
        this.map = map;
    }

    public MapManager getMapManager() {
        return map;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void printMessage(String message) {
        App.getApp().getLogger().log("[GamePlay] " + message);
        System.out.println(message);
    }

    public void takeInput(String input) {
        App.getApp().getLogger().log("[Input] " + input);
        System.out.println("Input received: " + input);

        if (input.equalsIgnoreCase("toggleview")) {
            toggleGameView();
            App.getApp().getLogger().log("Game view toggled to: " + gameViews.getString());
        }
    }

    public void enterRoom(SlotMap slotMap) {
        printMessage("You have entered a " + slotMap.geteMapRoom().getName());
        slotMap.setVisited(true);
        if (slotMap.geteMapRoom().isCombatRoom()) {
            if (!slotMap.getMonster().isAlive())
                printMessage("A wild monster appears!");
            else
                printMessage("The room is empty, the monster has already been defeated.");
        }
        if (slotMap.geteMapRoom() == EMapRoom.START) {
            printMessage("This is the starting room. Prepare for your adventure!");
            hero.setHealth(hero.getMaxHealth());
            printMessage("Your health has been restored to " + hero.getHealth() + " HP.");
        }
    }

    public void gameLoop() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            System.out.println("You entered: " + input);

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            if (input.equalsIgnoreCase("toggleview")) {
                toggleGameView();
                break;
            }

            if (input.equalsIgnoreCase("viewStat")) {
                printMessage(hero.toStringStats());
            } else {
                takeInput(input);
            }

        }
        scanner.close();
    }

    public void startGame(Hero hero, MapManager map) {
        this.hero = hero;
        this.map = map;
        App.getApp().getLogger().log("Game started with Hero: " + hero.getName() + " and Map with " + map.getMaps().size() + " rooms.");
        printMessage("Game started! Welcome " + hero.getName() + "!");
        SlotMap actualSlotMap = getMapManager().getSlotMapByCoordinates(hero.getX(), hero.getY());
        enterRoom(actualSlotMap);
        gameLoop();
    }
}
