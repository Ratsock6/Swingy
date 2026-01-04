package fr.aallouv.manager.map.slotMap;

import fr.aallouv.manager.map.EMapRoom;

public class BossRoom extends Room {

    public BossRoom(int x, int y) {
        super(x, y, EMapRoom.BOSS.getSymbol(), EMapRoom.BOSS.getName(), EMapRoom.BOSS.getDescription());
    }

    @Override
    public void onEnter() {
    }

}
