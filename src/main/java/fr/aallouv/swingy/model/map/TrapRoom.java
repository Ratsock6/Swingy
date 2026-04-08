package fr.aallouv.swingy.model.map;

import fr.aallouv.swingy.controller.GameController;

public class TrapRoom extends Room {

    private boolean triggered;

    public TrapRoom(int x, int y) {
        super(x, y);
        this.triggered = false;
    }

    @Override
    public void onEnter(GameController controller) {
        if (!triggered) {
            controller.onEnterTrap(this);
            triggered = true;
        }
    }

    @Override
    public String getRoomType() {
        return "TRAP";
    }

    public boolean isTriggered() { return triggered; }
}
