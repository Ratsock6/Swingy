package fr.aallouv.swingy.view.console;

import fr.aallouv.swingy.model.artifact.Artifact;
import fr.aallouv.swingy.model.entity.Hero;
import fr.aallouv.swingy.model.entity.Villain;
import fr.aallouv.swingy.model.map.Direction;
import fr.aallouv.swingy.model.map.Room;
import fr.aallouv.swingy.view.View;

import java.util.List;

public class ConsoleView implements View {

    private static final String SEPARATOR = "─────────────────────────────";
    private ConsoleRunner runner;

    public void setRunner(ConsoleRunner runner) {
        this.runner = runner;
    }

    @Override
    public void showMainMenu(List<Hero> heroes) {
        System.out.println(SEPARATOR);
        System.out.println("       SWINGY - RPG");
        System.out.println(SEPARATOR);
        System.out.println("1. Créer un héros");
        if (!heroes.isEmpty()) {
            System.out.println("2. Sélectionner un héros existant");
            System.out.println(SEPARATOR);
            for (int i = 0; i < heroes.size(); i++) {
                Hero h = heroes.get(i);
                System.out.println("  [" + (i + 1) + "] " + h.getName() + " - " + h.getHeroClass().displayName + " - Niv." + h.getLevel());
            }
        }
        System.out.println(SEPARATOR);
        System.out.print("> ");
    }

    @Override
    public void showHeroStats(Hero hero) {
        System.out.println(SEPARATOR);
        System.out.println("Héros    : " + hero.getName());
        System.out.println("Classe   : " + hero.getHeroClass().displayName);
        System.out.println("Niveau   : " + hero.getLevel());
        System.out.println("XP       : " + hero.getExperience() + " / " + Hero.xpRequiredForLevel(hero.getLevel()));
        System.out.println("HP       : " + hero.getHitPoints() + " / " + hero.getMaxHitPoints());
        System.out.println("Attaque  : " + hero.getAttack());
        System.out.println("Défense  : " + hero.getDefense());
        System.out.println("Or       : " + hero.getGold());
        System.out.println(SEPARATOR);
    }

    @Override
    public void showRoom(Room room, List<Direction> availableDirections) {
        System.out.println(SEPARATOR);
        System.out.println("Salle : " + room.getRoomType() + " (" + room.getX() + "," + room.getY() + ")");
        System.out.print("Sorties : ");
        for (Direction d : availableDirections) {
            System.out.print(d.name() + " ");
        }
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("N. Nord  S. Sud  E. Est  W. Ouest  Q. Stats  X. Quitter");
        System.out.print("> ");
    }

    @Override
    public void showVillainEncounter(Villain villain) {
        System.out.println(SEPARATOR);
        System.out.println("Un ennemi apparaît : " + villain.getName());
        System.out.println("HP : " + villain.getHitPoints() + " | ATK : " + villain.getAttack() + " | DEF : " + villain.getDefense());
        System.out.println("1. Combattre  2. Fuir");
        System.out.print("> ");
        if (runner != null) runner.onVillainEncounter();
    }


    @Override
    public void showArtifactDrop(Artifact artifact) {
        System.out.println("Un artefact a été trouvé : " + artifact);
        System.out.println("1. Équiper  2. Ignorer");
        System.out.print("> ");
        if (runner != null) runner.onArtifactDrop();
    }

    @Override
    public void showCombatResult(boolean heroWon, int xpGained) {
        if (heroWon) {
            System.out.println("Victoire ! +" + xpGained + " XP");
        } else {
            System.out.println("Tu as été vaincu...");
        }
    }

    @Override
    public void showLevelUp(Hero hero) {
        System.out.println("LEVEL UP ! Vous êtes maintenant niveau " + hero.getLevel());
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showError(String message) {
        System.err.println("[Erreur] " + message);
    }

    @Override
    public void showGameOver(boolean victory) {
        System.out.println(SEPARATOR);
        if (victory) {
            System.out.println("VICTOIRE ! Vous avez terminé le niveau !");
        } else {
            System.out.println("GAME OVER. Votre héros est mort.");
        }
        System.out.println(SEPARATOR);
    }
}
