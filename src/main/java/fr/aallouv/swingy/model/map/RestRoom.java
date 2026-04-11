package fr.aallouv.swingy.model.map;

import fr.aallouv.swingy.controller.GameController;

public class RestRoom extends Room {

    private boolean used;

    public RestRoom(int x, int y) {
        super(x, y);
        this.used = false;
    }

    @Override
    public void onEnter(GameController controller) {
        if (!used) {
            controller.onEnterRest(this);
            used = true;
        }
    }

    @Override
    public String getRoomType() {
        return "REST";
    }

    @Override
    public boolean isActivated() { return used; }
}
