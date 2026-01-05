package fr.aallouv.manager.map.room;

import fr.aallouv.manager.map.EMapRoom;

public class DistortionRoom extends Room {

    public DistortionRoom(int x, int y) {
        super(x, y, EMapRoom.DISTORTION.getSymbol(), EMapRoom.DISTORTION.getName(), EMapRoom.DISTORTION.getDescription());
    }

    @Override
    public void onEnter() {
    }

}
