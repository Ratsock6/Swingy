package fr.aallouv.enums;

public enum EGameViews {

    GUI("gui"),
    CONSOLE("console");

    private final String string;;

    EGameViews(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
