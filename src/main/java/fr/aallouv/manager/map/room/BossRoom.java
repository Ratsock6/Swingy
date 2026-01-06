package fr.aallouv.manager.map.room;

import fr.aallouv.manager.entity.Monster;
import fr.aallouv.manager.map.EMapRoom;

public class BossRoom extends Room {

    Monster boss;

    public BossRoom(int x, int y) {
        super(x, y, EMapRoom.BOSS.getSymbol(), EMapRoom.BOSS.getName(), EMapRoom.BOSS.getDescription());
        boss = Monster.createMonster(EMapRoom.BOSS);
    }

    @Override
    public void onEnter() {
    }

}
