package fr.aallouv.manager.map.slotMap;

import fr.aallouv.manager.map.EMapRoom;

public class TreasureRoom extends Room {

    public TreasureRoom(int x, int y) {
        super(x, y, EMapRoom.TREASURE.getSymbol(), EMapRoom.TREASURE.getName(), EMapRoom.TREASURE.getDescription());
    }

    @Override
    public void onEnter() {
    }

}
