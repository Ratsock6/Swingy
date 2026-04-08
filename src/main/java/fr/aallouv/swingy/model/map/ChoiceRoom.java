package fr.aallouv.swingy.model.map;

import fr.aallouv.swingy.controller.GameController;

public class ChoiceRoom extends Room {

    private boolean resolved;

    public ChoiceRoom(int x, int y) {
        super(x, y);
        this.resolved = false;
    }

    @Override
    public void onEnter(GameController controller) {
        if (!resolved) controller.onEnterChoice(this);
    }

    @Override
    public String getRoomType() {
        return "CHOICE";
    }

    public boolean isResolved() { return resolved; }
    public void setResolved(boolean resolved) { this.resolved = resolved; }
}
