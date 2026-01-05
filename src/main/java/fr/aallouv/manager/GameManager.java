package fr.aallouv.manager;

import fr.aallouv.App;
import fr.aallouv.enums.EGameViews;
import fr.aallouv.manager.map.CardinalPoint;
import fr.aallouv.manager.map.MapManager;
import fr.aallouv.manager.entity.Hero;
import fr.aallouv.manager.map.room.Room;

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

    public void enterRoom(Room room) {
        // TODO: Implement room entering logic
        if (room == null) {
            printMessage("Error: The room you are trying to enter does not exist.");
            hero.setX(0);
            hero.setY(0);
            enterRoom(map.getRoomByCoord(hero.getX(), hero.getY()));
            return;
        }
        printMessage("You have entered a " + room.getName());
        room.onEnter();
        room.markVisited();
        printMessage("Press Enter to continue...");
        new Scanner(System.in).nextLine();
        map.viewVisitedRoom();
        printPossibleCommands();
//        if (slotMap == null) {
//            printMessage("Error: The room you are trying to enter does not exist.");
//            hero.setX(0);
//            hero.setY(0);
//            enterRoom(map.getSlotMapByCoordinates(hero.getX(), hero.getY()));
//            return;
//        }
//        printMessage("You have entered a " + slotMap.geteMapRoom().getName());
//        if (slotMap.isVisited()) {
//            printMessage("You have been here before.");
//        }
//        if (!slotMap.isVisited()) {
//            if (slotMap.geteMapRoom().isCombatRoom()) {
//                if (slotMap.getMonster() != null && slotMap.getMonster().isAlive()) {
//                    printMessage("A wild " + slotMap.getMonster().getName() + "[id:" + slotMap.getMonster().getId() + "] appears!");
//                } else {
//                    printMessage("The room is eerily quiet. It seems the monster here has already been defeated.");
//                }
//            }
//            if (slotMap.geteMapRoom() == EMapRoom.START) {
//                printMessage("This is the starting room. Prepare for your adventure!");
//            }
//            if (slotMap.geteMapRoom() == EMapRoom.REST) {
//                printMessage("You found a rest room. Your health is fully restored!");
//                hero.setHealth(hero.getMaxHealth());
//            }
//            if (slotMap.geteMapRoom() == EMapRoom.TREASURE) {
//                hero.setGold(hero.getGold() + new Random().nextInt(100) + 50);
//                printMessage("You found a treasure room! You gained some gold. Current gold: " + hero.getGold());
//            }
//            if (slotMap.geteMapRoom() == EMapRoom.TRAP) {
//                int esquiveChance = new Random().nextInt(100);
//                App.getApp().getLogger().log("Trap triggered! Esquive chance: " + esquiveChance + ", Hero speed: " + hero.getSpeed() + ", Active: " + (esquiveChance < hero.getSpeed()));
//                if (esquiveChance < hero.getSpeed()) {
//                    printMessage("You triggered a trap but managed to evade it thanks to your speed! No damage taken.");
//                } else {
//                    int damage = new Random().nextInt(20) + 10;
//                    hero.takePhysicalDamage(damage);
//                    printMessage("You triggered a trap! You took " + damage + " damage. Current health: " + hero.getHealth());
//                }
//            }
//
//            if (slotMap.geteMapRoom() == EMapRoom.CHOICE) {
//                printMessage("You have entered a choice room. Choose your path wisely!");
//                EChoice firstChoice = EChoice.getRandomChoice();
//                EChoice secondChoice = EChoice.getRandomChoiceExclude(firstChoice);
//                printMessage("You have two choices:\n1) " + firstChoice.getDescription() + " (Cost: " + firstChoice.getCost() + ")" + "\n2) " + secondChoice.getDescription() + " (Cost: " + secondChoice.getCost() + ")");
//                printMessage("Type 1 or 2 to make your choice:");
//                Scanner scanner = new Scanner(System.in);
//                String choiceInput = scanner.nextLine();
//                EChoice chosen;
//                if (choiceInput.equals("1")) {
//                    chosen = firstChoice;
//                } else if (choiceInput.equals("2")) {
//                    chosen = secondChoice;
//                } else {
//                    printMessage("Invalid choice. Defaulting to choice 1.");
//                    chosen = firstChoice;
//                }
//
//                if (chosen.getCost() > hero.getGold()) {
//                    printMessage("You do not have enough gold for this choice. No effects applied.");
//                } else {
//                    hero.setGold(hero.getGold() - chosen.getCost());
//                    printMessage("You chose: " + chosen.getDescription() + ". Gold remaining: " + hero.getGold());
//                }
//                hero.setMaxHealth(hero.getMaxHealth() + chosen.getHealthValue());
//                hero.setHealth(hero.getHealth() + chosen.getHealthValue());
//                hero.setAttack(hero.getAttack() + chosen.getAttackValue());
//                hero.setDefense(hero.getDefense() + chosen.getDefenseValue());
//                hero.setPsychicAttack(hero.getPsychicAttack() + chosen.getPsychicAttackValue());
//                hero.setPsychicDefense(hero.getPsychicDefense() + chosen.getPsychicDefenseValue());
//                hero.setSpeed(hero.getSpeed() + chosen.getSpeedValue());
//                hero.addXp(chosen.getXpValue());
//            }
//
//            if (slotMap.geteMapRoom() == EMapRoom.DISTORTION) {
//                slotMap.setVisited(true);
//                printMessage("You have entered a distortion room. Your position will be randomized!");
//                ArrayList<SlotMap> slotVisited = map.getVisitedRooms();
//                if (slotVisited.size() > 1) {
//                    SlotMap randomRoom;
//                    do {
//                        randomRoom = slotVisited.get(new Random().nextInt(slotVisited.size()));
//                    } while (randomRoom == slotMap);
//                    hero.setX(randomRoom.getCoordX());
//                    hero.setY(randomRoom.getCoordY());
//                    printMessage("You have been teleported to a new location: (" + hero.getX() + ", " + hero.getY() + ")");
//                    enterRoom(randomRoom);
//                } else {
//                    printMessage("No other visited rooms to teleport to. Staying in the current room.");
//                }
//            }
//        }
//        slotMap.setVisited(true);
//        printMessage("Press Enter to continue...");
//        new Scanner(System.in).nextLine();
//        map.viewVisitedRoom();
//        printPossibleCommands();
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
//        SlotMap actualSlotMap = getMapManager().getSlotMapByCoordinates(hero.getX(), hero.getY());
//        enterRoom(actualSlotMap);
        gameLoop();
    }
}
