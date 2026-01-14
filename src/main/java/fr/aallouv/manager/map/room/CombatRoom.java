package fr.aallouv.manager.map.room;

import fr.aallouv.App;
import fr.aallouv.manager.ECombatStatus;
import fr.aallouv.manager.combat.CombatManager;
import fr.aallouv.manager.entity.Monster;
import fr.aallouv.manager.map.EMapRoom;

public class CombatRoom extends Room {

    Monster monster;

    public CombatRoom(int x, int y) {
        super(x, y, EMapRoom.COMBAT.getSymbol(), EMapRoom.COMBAT.getName(), EMapRoom.COMBAT.getDescription());
        monster = Monster.createMonster(EMapRoom.COMBAT);
    }

    @Override
    public void onEnter() {
        if (!monster.isAlive()) {
            System.out.println("The boss has already been defeated. Now you can find the exit!");
        } else {
            CombatManager combatManager = new CombatManager(monster);
            ECombatStatus combatStatus = combatManager.startCombat();
            if (combatStatus == ECombatStatus.VICTORY) {
                System.out.println("Congratulations! You have defeated the boss!");
            } else if (combatStatus == ECombatStatus.DEFEAT) {
                System.out.println("You have been defeated by the boss. Try again!");
            } else {
                System.out.println("You fled from the boss fight. Prepare yourself for another encounter!");
                App.getApp().getGameManager().getHero().setX(App.getApp().getGameManager().getHero().getLastX());
                App.getApp().getGameManager().getHero().setY(App.getApp().getGameManager().getHero().getLastY());
            }
        }
    }

}
