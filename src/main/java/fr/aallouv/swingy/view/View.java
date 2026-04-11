package fr.aallouv.swingy.view;

import fr.aallouv.swingy.model.artifact.Artifact;
import fr.aallouv.swingy.model.entity.Hero;
import fr.aallouv.swingy.model.entity.Villain;
import fr.aallouv.swingy.model.map.ChoiceRoom;
import fr.aallouv.swingy.model.map.Direction;
import fr.aallouv.swingy.model.map.Room;

import java.util.List;

public interface View {
    void showMainMenu(List<Hero> heroes);
    void showHeroStats(Hero hero);
    void showRoom(Room room, List<Direction> availableDirections);
    void showVillainEncounter(Villain villain);
    void showCombatResult(boolean heroWon, int xpGained);
    void showArtifactDrop(Artifact artifact);
    void showLevelUp(Hero hero);
    void showMessage(String message);
    void showError(String message);
    void showGameOver(boolean victory);
    void showRestChoice(boolean alreadyUsed);
    void showCoffreChoice(boolean alreadyOpened);
    void showChoiceRoom(ChoiceRoom room);
}
