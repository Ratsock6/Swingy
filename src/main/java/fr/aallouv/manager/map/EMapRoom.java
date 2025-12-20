package fr.aallouv.manager.map;

public enum EMapRoom {

    START("Starting room", "You stand in a silent chamber at the heart of the labyrinth. The walls seem to watch you, waiting for your first move."),
    COMBAT("Combat room", "A hostile presence fills the room. A creature of the labyrinth steps forward, blocking your path."),
    ELITE("Elite room", "The air is heavy and oppressive. A powerful guardian awakens, far stronger than the others."),
    TREASURE("Treasure room", "Glittering treasures lie before you, promising great rewards for those who dare to claim them."),
    TRAP("Trap room", "The room is filled with hidden dangers. One wrong step could trigger a deadly mechanism."),
    REST("Rest room", "A peaceful sanctuary amidst the chaos. Here, you can catch your breath and prepare for the challenges ahead."),
    CHOICE("Choice room", "Multiple paths lie before you, each leading to a different fate. Choose wisely."),
    DISTORTION("Distortion room", "Reality seems to warp and twist around you. The laws of the labyrinth no longer apply here."),
    BOSS("Boss room", "The labyrinth trembles as you enter. Its master stands before you, ready to defend its core."),
    EXIT("Exit room", "A glowing portal stands before you, promising escape from the labyrinth's grasp.");

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
