package fr.aallouv.manager.map;

import fr.aallouv.App;
import fr.aallouv.manager.entity.Monster;

public class SlotMap {

    private static int idCounter = 0;
    private EMapRoom eMapRoom;
    private final int id = idCounter++;
    private final int coordX, coordY;
    private boolean visited;
    private Monster monster = null;

    public SlotMap(EMapRoom mapRoom, int x, int y) {

        this.eMapRoom = mapRoom;
        this.coordX = x;
        this.coordY = y;
        this.visited = false;
        if (eMapRoom.isCombatRoom()) {
            monster = Monster.createMonster(eMapRoom);
        }

        App.getApp().getGameManager().getMapManager().getMaps().add(this);
        App.getApp().getLogger().log("Created SlotMap ID[" + this.id + "] of type " + this.eMapRoom.name() + " at (" + this.coordX + "," + this.coordY + ")");
    }

    public int getId() {
        return id;
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

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    public Monster getMonster() {
        return monster;
    }
}


