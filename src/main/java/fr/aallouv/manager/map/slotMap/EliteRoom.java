package fr.aallouv.manager.map.slotMap;

import fr.aallouv.manager.map.EMapRoom;

public class EliteRoom extends Room {

    public EliteRoom(int x, int y) {
        super(x, y, EMapRoom.ELITE.getSymbol(), EMapRoom.ELITE.getName(), EMapRoom.ELITE.getDescription());
    }

    @Override
    public void onEnter() {
    }

}
