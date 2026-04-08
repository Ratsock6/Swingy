package fr.aallouv.swingy.view.console;

import fr.aallouv.swingy.ViewSwitcher;
import fr.aallouv.swingy.controller.GameController;
import fr.aallouv.swingy.model.entity.Hero;
import fr.aallouv.swingy.model.entity.HeroClass;
import fr.aallouv.swingy.model.map.Direction;
import fr.aallouv.swingy.repository.FileHeroRepository;

import java.util.List;
import java.util.Scanner;

public class ConsoleRunner {

    private final GameController controller;
    private final ConsoleView view;
    private final Scanner scanner;
    private volatile boolean active;
    private ViewSwitcher switcher;

    private boolean waitingForCombatChoice;
    private boolean waitingForArtifactChoice;

    public ConsoleRunner(GameController controller, ConsoleView view) {
        this.controller = controller;
        this.view = view;
        this.scanner = new Scanner(System.in);
        this.waitingForCombatChoice = false;
        this.waitingForArtifactChoice = false;
        this.active = true;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setSwitcher(ViewSwitcher switcher) {
        this.switcher = switcher;
    }

    public void run() {
        showMainMenu();

        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();

            if (!active) continue;

            if (input.equals("gui")) {
                if (switcher != null) switcher.switchToGui();
                continue;
            }

            if (controller.isGameOver()) {
                showMainMenu();
                continue;
            }

            if (waitingForCombatChoice) {
                handleCombatChoice(input);
                continue;
            }

            if (waitingForArtifactChoice) {
                handleArtifactChoice(input);
                continue;
            }

            if (controller.getState() == null) {
                handleMenuInput(input);
            } else {
                handleGameInput(input);
            }
        }
    }

    // --- Menu ---

    private void showMainMenu() {
        waitingForCombatChoice = false;
        waitingForArtifactChoice = false;
        List<Hero> heroes = new FileHeroRepository().findAll();
        view.showMainMenu(heroes);
    }

    private void handleMenuInput(String input) {
        List<Hero> heroes = new FileHeroRepository().findAll();

        if (input.equals("1")) {
            handleCreateHero();
            return;
        }

        if (input.equals("2") && !heroes.isEmpty()) {
            handleSelectHero(heroes);
            return;
        }

        view.showError("Choix invalide.");
        showMainMenu();
    }

    private void handleCreateHero() {
        System.out.print("Nom du héros : ");
        String name = scanner.nextLine().trim();

        System.out.println("Choisissez une classe :");
        HeroClass[] classes = HeroClass.values();
        for (int i = 0; i < classes.length; i++) {
            System.out.println((i + 1) + ". " + classes[i].displayName);
        }
        System.out.print("> ");

        String classInput = scanner.nextLine().trim();
        int classIndex;
        try {
            classIndex = Integer.parseInt(classInput) - 1;
        } catch (NumberFormatException e) {
            view.showError("Classe invalide.");
            showMainMenu();
            return;
        }

        if (classIndex < 0 || classIndex >= classes.length) {
            view.showError("Classe invalide.");
            showMainMenu();
            return;
        }

        controller.createHero(name, classes[classIndex].name());
    }

    private void handleSelectHero(List<Hero> heroes) {
        System.out.print("Numéro du héros : ");
        String input = scanner.nextLine().trim();
        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            view.showError("Sélection invalide.");
            showMainMenu();
            return;
        }
        controller.selectHero(index);
    }

    // --- Jeu ---

    private void handleGameInput(String input) {
        switch (input) {
            case "n" -> controller.move(Direction.NORTH);
            case "s" -> controller.move(Direction.SOUTH);
            case "e" -> controller.move(Direction.EAST);
            case "w" -> controller.move(Direction.WEST);
            case "q" -> view.showHeroStats(controller.getState().getHero());
            case "x" -> {
                System.out.println("Retour au menu.");
                controller.getState().setGameOver(true);
                showMainMenu();
            }
            default -> view.showError("Commande invalide. (n/s/e/w/q/x/gui)");
        }
    }

    // --- Combat ---

    public void onVillainEncounter() {
        waitingForCombatChoice = true;
    }

    private void handleCombatChoice(String input) {
        switch (input) {
            case "1" -> {
                waitingForCombatChoice = false;
                controller.fight();
            }
            case "2" -> {
                waitingForCombatChoice = false;
                controller.run();
            }
            default -> view.showError("1 pour combattre, 2 pour fuir.");
        }
    }

    // --- Artefact ---

    public void onArtifactDrop() {
        waitingForArtifactChoice = true;
    }

    private void handleArtifactChoice(String input) {
        switch (input) {
            case "1" -> {
                waitingForArtifactChoice = false;
                controller.equipArtifact();
            }
            case "2" -> {
                waitingForArtifactChoice = false;
                controller.ignoreArtifact();
            }
            default -> view.showError("1 pour équiper, 2 pour ignorer.");
        }
    }
}
