package fr.aallouv.swingy.model.map;

import fr.aallouv.swingy.controller.GameController;

public class EliteRoom extends Room {

    private boolean cleared;

    public EliteRoom(int x, int y) {
        super(x, y);
        this.cleared = false;
    }

    @Override
    public void onEnter(GameController controller) {
        if (!cleared) controller.onEnterElite(this);
    }

    @Override
    public String getRoomType() {
        return "ELITE";
    }

    public void setCleared(boolean cleared) { this.cleared = cleared; }

    @Override
    public boolean isActivated() { return cleared; }
}
