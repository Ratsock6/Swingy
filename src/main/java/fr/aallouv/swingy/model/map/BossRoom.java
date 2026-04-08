package fr.aallouv.swingy.model.map;

import fr.aallouv.swingy.controller.GameController;

public class BossRoom extends Room {

    private boolean bossDefeated;

    public BossRoom(int x, int y) {
        super(x, y);
        this.bossDefeated = false;
    }

    @Override
    public void onEnter(GameController controller) {
        if (!bossDefeated) controller.onEnterBoss(this);
    }

    @Override
    public String getRoomType() {
        return "BOSS";
    }

    public boolean isBossDefeated() { return bossDefeated; }
    public void setBossDefeated(boolean bossDefeated) { this.bossDefeated = bossDefeated; }
}
