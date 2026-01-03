package fr.aallouv.manager;

import fr.aallouv.App;
import fr.aallouv.enums.EGameViews;
import fr.aallouv.manager.map.CardinalPoint;
import fr.aallouv.manager.map.EMapRoom;
import fr.aallouv.manager.map.MapManager;
import fr.aallouv.manager.map.SlotMap;
import fr.aallouv.manager.entity.Hero;

import java.util.ArrayList;
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

    public boolean takeInput(String input) {

        if (input.equalsIgnoreCase("exit")) {
            System.out.println("Goodbye!");
            return false;
        }


        if (input.equalsIgnoreCase("stats")) {
            printMessage(hero.toStringStats());
            return true;
        }

        if (input.equalsIgnoreCase("map")) {
            if (gameViews == EGameViews.CONSOLE) {
                map.viewVisitedRoom();
            }
            return true;
        }

        if (input.equalsIgnoreCase("toggleview")) {
            toggleGameView();
            App.getApp().getLogger().log("Game view toggled to: " + gameViews.getString());
            return false;
        }

        if (input.equalsIgnoreCase("help")) {
            showHelp();
            return true;
        }

        if (input.equalsIgnoreCase("possibility")) {
            printPossibleCommands();
            return true;
        }

        if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("s") ||
            input.equalsIgnoreCase("e") || input.equalsIgnoreCase("w")) {
            CardinalPoint direction = CardinalPoint.fromString(input);
            if (direction == null) {
                printMessage("Invalid direction command: " + input);
                return true;
            }
            ArrayList<CardinalPoint> availableDirections = map.getCardinalPointsHeroCanMove(hero.getX(), hero.getY());
            System.out.println(availableDirections);
            if (!availableDirections.contains(direction)) {
                printMessage("You can't move " + direction.toString() + " from here.");
                return true;
            }
            hero.move(direction);
            return true;
        }


        App.getApp().getGameManager().printMessage("Unknown command: " + input);
        return true;
    }

    public void enterRoom(SlotMap slotMap) {
        if (slotMap == null) {
            printMessage("Error: The room you are trying to enter does not exist.");
            hero.setX(0);
            hero.setY(0);
            enterRoom(map.getSlotMapByCoordinates(hero.getX(), hero.getY()));
            return;
        }
        printMessage("You have entered a " + slotMap.geteMapRoom().getName());
        slotMap.setVisited(true);
        if (slotMap.geteMapRoom().isCombatRoom()) {
            if (slotMap.getMonster().isAlive())
                printMessage("A wild monster appears!");
            else
                printMessage("The room is empty, the monster has already been defeated.");
        }
        if (slotMap.geteMapRoom() == EMapRoom.START) {
            printMessage("This is the starting room. Prepare for your adventure!");
            hero.setHealth(hero.getMaxHealth());
            printMessage("Your health has been restored to " + hero.getHealth() + " HP.");
        }
        printMessage("Press Enter to continue...");
        new Scanner(System.in).nextLine();
        map.viewVisitedRoom();
        printPossibleCommands();
    }

    public void showHelp() {
        printMessage("Available commands:");
        printMessage(" - stats : Show hero statistics");
        printMessage(" - map : Show visited rooms on the map");
        printMessage(" - toggleview : Toggle between GUI and Console views");
        printMessage(" - exit : Exit the game");
        printMessage(" - possility : Show possible movement directions from current position");
    }

    public void printPossibleCommands() {
        ArrayList<CardinalPoint> availableDirections = map.getCardinalPointsHeroCanMove(hero.getX(), hero.getY());
        printMessage("You can move in the following directions: " + availableDirections.toString());
    }

    public void gameLoop() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("> ");
            String input = scanner.nextLine();
            if (!takeInput(input)) {
                break;
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
