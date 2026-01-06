package fr.aallouv.manager.map.room;

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
    }

}
