package fr.aallouv.manager.map;

import fr.aallouv.App;

public class SlotMap {

    private EMapRoom eMapRoom;
    private final int coordX, coordY;

    public SlotMap(EMapRoom mapRoom, int x, int y) {
        this.eMapRoom = mapRoom;
        this.coordX = x;
        this.coordY = y;
        App.getApp().getGameManager().getMapManager().getMaps().add(this);
        App.getApp().getLogger().log("New room created:" + mapRoom.getName() + " coord:" + coordX + "/" + coordY);
    }

    public EMapRoom geteMapRoom() {
        return eMapRoom;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }
}


