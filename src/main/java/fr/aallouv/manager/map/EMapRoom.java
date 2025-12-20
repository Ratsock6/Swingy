package fr.aallouv.manager.map;

public enum EMapRoom {

    START("Starting room", "You stand in a silent chamber at the heart of the labyrinth. The walls seem to watch you, waiting for your first move.", "s"),
    COMBAT("Combat room", "A hostile presence fills the room. A creature of the labyrinth steps forward, blocking your path.", "c"),
    ELITE("Elite room", "The air is heavy and oppressive. A powerful guardian awakens, far stronger than the others.", "e"),
    TREASURE("Treasure room", "Glittering treasures lie before you, promising great rewards for those who dare to claim them.", "t"),
    TRAP("Trap room", "The room is filled with hidden dangers. One wrong step could trigger a deadly mechanism.", "p"),
    REST("Rest room", "A peaceful sanctuary amidst the chaos. Here, you can catch your breath and prepare for the challenges ahead.", "r"),
    CHOICE("Choice room", "Multiple paths lie before you, each leading to a different fate. Choose wisely.", "h"),
    DISTORTION("Distortion room", "Reality seems to warp and twist around you. The laws of the labyrinth no longer apply here.", "d"),
    BOSS("Boss room", "The labyrinth trembles as you enter. Its master stands before you, ready to defend its core.", "b"),
    EXIT("Exit room", "A glowing portal stands before you, promising escape from the labyrinth's grasp.", "o");

    private final String name;
    private final String description;
    private final String symbol;

    EMapRoom(String name, String description, String symbol) {
        this.name = name;
        this.description = description;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSymbol() {
        return symbol;
    }
}
