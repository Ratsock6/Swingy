package fr.aallouv.swingy.model.map;

import fr.aallouv.swingy.controller.GameController;

public class StartRoom extends Room {

    public StartRoom(int x, int y) {
        super(x, y);
    }

    @Override
    public void onEnter(GameController controller) {
        controller.onEnterStart();
    }

    @Override
    public String getRoomType() {
        return "START";
    }
}
