package fr.aallouv.manager.map.room;

import fr.aallouv.manager.entity.Monster;
import fr.aallouv.manager.map.EMapRoom;

public class EliteRoom extends Room {

    Monster elite;

    public EliteRoom(int x, int y) {
        super(x, y, EMapRoom.ELITE.getSymbol(), EMapRoom.ELITE.getName(), EMapRoom.ELITE.getDescription());
        elite = Monster.createMonster(EMapRoom.ELITE);
    }

    @Override
    public void onEnter() {
    }

}
