package fr.aallouv.manager.map.slotMap;

import fr.aallouv.manager.map.EMapRoom;

public class RestRoom extends Room {

    public RestRoom(int x, int y) {
        super(x, y, EMapRoom.REST.getSymbol(), EMapRoom.REST.getName(), EMapRoom.REST.getDescription());
    }

    @Override
    public void onEnter() {
    }
}
