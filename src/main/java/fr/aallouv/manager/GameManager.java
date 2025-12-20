package fr.aallouv.manager;

import fr.aallouv.enums.EGameViews;
import fr.aallouv.manager.map.MapManager;

public class GameManager {

    private EGameViews gameViews;
    private final MapManager map;

    public GameManager(EGameViews gameViews, int numberOfRoom) {
        this.gameViews = gameViews;
        this.map = new MapManager(numberOfRoom);
    }

    public  EGameViews getGameViews() {
        return gameViews;
    }

    public void setGameViews(EGameViews gameViews) {
        this.gameViews = gameViews;
    }

    public void toggleGameView() {
        if (gameViews == EGameViews.GUI) {
            gameViews = EGameViews.CONSOLE;
            return;
        }
        gameViews = EGameViews.GUI;
    }


    public MapManager getMapManager() {
        return map;
    }
}
