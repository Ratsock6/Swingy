package fr.aallouv.swingy.model.map;

import fr.aallouv.swingy.controller.GameController;

public class DistortionRoom extends Room {

    public DistortionRoom(int x, int y) {
        super(x, y);
    }

    @Override
    public void onEnter(GameController controller) {
        controller.onEnterDistortion(this);
    }

    @Override
    public String getRoomType() {
        return "DISTORTION";
    }
}
