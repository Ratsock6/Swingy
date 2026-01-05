package fr.aallouv.manager.map.room;

import fr.aallouv.App;
import fr.aallouv.manager.map.EMapRoom;

public abstract class Room {

    /* ======================
       Static ID generator
       ====================== */

    private static long NEXT_ID = 1;

    /* ======================
       Fields
       ====================== */

    protected final long id;
    protected final int x;
    protected final int y;
    protected final char symbol;
    protected final String name, description;
    protected boolean visited;

    /* ======================
       Constructor
       ====================== */

    protected Room(int x, int y, char symbol, String name, String description) {
        this.id = NEXT_ID++;
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.name = name;
        this.description = description;
        this.visited = false;
        App.getApp().getLogger().log("Created room ID " + id + " at (" + x + ", " + y + ")");
        App.getApp().getGameManager().getMapManager().getMaps().add(this);
    }

    /* ======================
       Getters
       ====================== */

    public long getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisited() {
        return visited;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /* ======================
       State
       ====================== */

    public void markVisited() {
        this.visited = true;
    }

    /* ======================
       Room behavior
       ====================== */

    /**
     * Called when the hero enters the room.
     */
    public abstract void onEnter();

    /**
     * Optional hook called when the hero leaves the room.
     */
    public void onExit() {
        // default: nothing
    }

    public static Room createRoom(int x, int y, EMapRoom mapRoom) {
        switch (mapRoom) {
            case BOSS:
                return new BossRoom(x, y);
            case COMBAT:
                return new CombatRoom(x, y);
            case DISTORTION:
                return new DistortionRoom(x, y);
            case TRAP:
                return new TrapRoom(x, y);
            case EXIT:
                return new ExitRoom(x, y);
            case TREASURE:
                return new TreasureRoom(x, y);
            case START:
                return new StartRoom(x, y);
            case ELITE:
                return new EliteRoom(x, y);
            case REST:
                return new RestRoom(x, y);
            case CHOICE:
                return new ChoiceRoom(x, y);
            default:
                throw new IllegalArgumentException("Unknown room type: " + mapRoom);
        }
    }
}
