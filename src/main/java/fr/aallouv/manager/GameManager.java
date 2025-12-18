package fr.aallouv.manager;

import fr.aallouv.enums.GameViews;
import java.time.LocalDateTime;

public class GameManager {

    private GameViews gameViews;
    private final String logsFile;

    public GameManager(GameViews gameViews) {
        this.gameViews = gameViews;
        logsFile = LocalDateTime.now().toString();
    }

    public void newLogs(String logs) {
        String logsLine = LocalDateTime.now().toString() + " [LOGS]: " + logs;

    }

    public String getLogsFile() {
        return logsFile;
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
