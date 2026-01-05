package fr.aallouv.manager.map.room;

import fr.aallouv.manager.map.EMapRoom;

public class ExitRoom extends Room {

    public ExitRoom(int x, int y) {
        super(x, y, EMapRoom.EXIT.getSymbol(), EMapRoom.EXIT.getName(), EMapRoom.EXIT.getDescription());
    }

    @Override
    public void onEnter() {
    }

}
