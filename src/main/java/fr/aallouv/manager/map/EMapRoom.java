package fr.aallouv.enums;

public enum EMapRoom {

    START("Starting room", "You stand in a silent chamber at the heart of the labyrinth. The walls seem to watch you, waiting for your first move."),
    COMBAT("Combat room", "A hostile presence fills the room. A creature of the labyrinth steps forward, blocking your path."),
    ELITE("Elite room", "The air is heavy and oppressive. A powerful guardian awakens, far stronger than the others."),
    BOSS(),
    TREASURE(),
    TRAP(),
    REST(),
    CHOICE(),
    DISTORTION(),
    EXIT();

    private final String name;
    private final String description;

    EMapRoom(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
