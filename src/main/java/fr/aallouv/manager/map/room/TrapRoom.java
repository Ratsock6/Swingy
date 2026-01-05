package fr.aallouv.manager.map.room;

import fr.aallouv.manager.map.EMapRoom;

public class TrapRoom extends Room {

    public TrapRoom(int x, int y) {
        super(x, y, EMapRoom.TRAP.getSymbol(), EMapRoom.TRAP.getName(), EMapRoom.TRAP.getDescription());
    }

    @Override
    public void onEnter() {
    }

}
