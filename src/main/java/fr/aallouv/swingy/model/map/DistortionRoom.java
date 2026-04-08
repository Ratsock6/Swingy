package fr.aallouv.swingy.model.map;

import fr.aallouv.swingy.controller.GameController;

public class DistortionRoom extends Room {

    private boolean triggered;

    public DistortionRoom(int x, int y) {
        super(x, y);
        this.triggered = false;
    }

    @Override
    public void onEnter(GameController controller) {
        if (!triggered) {
            triggered = true;
            controller.onEnterDistortion(this);
        }
    }

    @Override
    public String getRoomType() {
        return "DISTORTION";
    }
}