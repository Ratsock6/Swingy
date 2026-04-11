package fr.aallouv.swingy.model.map;

import fr.aallouv.swingy.controller.GameController;

public class CoffreRoom extends Room {

    private boolean opened;

    public CoffreRoom(int x, int y) {
        super(x, y);
        this.opened = false;
    }

    @Override
    public void onEnter(GameController controller) {
        if (!opened) {
            controller.onEnterCoffre(this);
        }
    }

    @Override
    public String getRoomType() { return "COFFRE"; }

    @Override
    public boolean isActivated() { return opened; }

    public boolean isOpened() { return opened; }
    public void setOpened(boolean opened) { this.opened = opened; }
}
