package fr.aallouv.swingy.model.game;

import fr.aallouv.swingy.model.entity.Hero;
import fr.aallouv.swingy.model.map.Direction;
import fr.aallouv.swingy.model.map.GameMap;
import fr.aallouv.swingy.model.map.Room;

public class GameState {

    private final Hero hero;
    private final GameMap map;
    private Room currentRoom;
    private int heroX;
    private int heroY;
    private boolean bossDefeated;
    private boolean gameOver;
    private boolean victory;
    private Direction lastMoveDirection = Direction.SOUTH;

    public GameState(Hero hero, GameMap map) {
        this.hero = hero;
        this.map = map;
        this.bossDefeated = false;
        this.gameOver = false;
        this.victory = false;

        int center = map.getSize() / 2;
        this.heroX = center;
        this.heroY = center;
        this.currentRoom = map.getStartRoom();
    }

    public Hero getHero() {
        return hero;
    }

    public GameMap getMap() {
        return map;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public int getHeroX() {
        return heroX;
    }

    public int getHeroY() {
        return heroY;
    }

    public boolean isBossDefeated() {
        return bossDefeated;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isVictory() {
        return victory;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public void setHeroX(int x) {
        this.heroX = x;
    }

    public void setHeroY(int y) {
        this.heroY = y;
    }

    public void setBossDefeated(boolean bossDefeated) {
        this.bossDefeated = bossDefeated;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }

    public Direction getLastMoveDirection() { return lastMoveDirection; }

    public void setLastMoveDirection(Direction direction) { this.lastMoveDirection = direction; }
}
