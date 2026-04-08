package fr.aallouv.swingy.model.map;

import fr.aallouv.swingy.controller.GameController;

public class CombatRoom extends Room {

    private boolean cleared;

    public CombatRoom(int x, int y) {
        super(x, y);
        this.cleared = false;
    }

    @Override
    public void onEnter(GameController controller) {
        if (!cleared) controller.onEnterCombat(this);
    }

    @Override
    public String getRoomType() {
        return "COMBAT";
    }

    public boolean isCleared() { return cleared; }
    public void setCleared(boolean cleared) { this.cleared = cleared; }
}
