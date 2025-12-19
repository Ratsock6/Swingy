package fr.aallouv.manager.map;

public class SlotMap {

    private EMapRoom eMapRoom;
    public int coordX, coordY;

    SlotMap(EMapRoom mapRoom, int x, int y) {
        this.eMapRoom = mapRoom;
        this.coordX = x;
        this.coordY = y;
    }

}
