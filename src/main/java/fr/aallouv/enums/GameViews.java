package fr.aallouv.enums;

public enum GameViews {

    GUI("gui"),
    CONSOLE("console");

    private final String string;;

    GameViews(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
