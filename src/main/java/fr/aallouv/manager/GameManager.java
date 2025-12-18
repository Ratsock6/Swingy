package fr.aallouv.manager;

import fr.aallouv.enums.GameViews;
import java.time.LocalDateTime;

public class GameManager {

    private GameViews gameViews;

    public GameManager(GameViews gameViews) {
        this.gameViews = gameViews;
    }

    public GameViews getGameViews() {
        return gameViews;
    }

    public void setGameViews(GameViews gameViews) {
        this.gameViews = gameViews;
    }

    public void toggleGameView() {
        if (gameViews == GameViews.GUI) {
            gameViews = GameViews.CONSOLE;
            return;
        }
        gameViews = GameViews.GUI;
    }
}
