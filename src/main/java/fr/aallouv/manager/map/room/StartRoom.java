package fr.aallouv.manager.map.room;

import fr.aallouv.manager.map.EMapRoom;

public class StartRoom extends Room {

    public StartRoom(int x, int y) {
        super(x, y, EMapRoom.START.getSymbol(), EMapRoom.START.getName(), EMapRoom.START.getDescription());
    }

    @Override
    public void onEnter() {
    }
}
