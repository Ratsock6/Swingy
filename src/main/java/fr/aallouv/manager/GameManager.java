package fr.aallouv.manager;

import fr.aallouv.enums.EGameViews;
import fr.aallouv.manager.map.MapManager;

public class GameManager {

    private EGameViews gameViews;
    private MapManager map;

    public GameManager(EGameViews gameViews) {
        this.gameViews = gameViews;
        this.map = null;
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


    public void setMap(MapManager map) {
        this.map = map;
    }

    public MapManager getMapManager() {
        return map;
    }

    public void startGame() {
        
    }
}
