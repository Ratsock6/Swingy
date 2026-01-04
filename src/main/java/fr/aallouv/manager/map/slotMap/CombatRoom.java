package fr.aallouv.manager.map.slotMap;

import fr.aallouv.manager.map.EMapRoom;

public class CombatRoom extends Room {

    public CombatRoom(int x, int y) {
        super(x, y, EMapRoom.COMBAT.getSymbol(), EMapRoom.COMBAT.getName(), EMapRoom.COMBAT.getDescription());
    }

    @Override
    public void onEnter() {
    }

}
