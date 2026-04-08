package fr.aallouv.swingy.model.map;

import fr.aallouv.swingy.controller.GameController;

public class ExitRoom extends Room {

    public ExitRoom(int x, int y) {
        super(x, y);
    }

    @Override
    public void onEnter(GameController controller) {
        controller.onEnterExit(this);
    }

    @Override
    public String getRoomType() {
        return "EXIT";
    }
}
