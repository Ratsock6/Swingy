package fr.aallouv.manager.map;

public enum EMapRoom {

    START("Starting room", "start_room", "You stand in a silent chamber at the heart of the labyrinth. The walls seem to watch you, waiting for your first move.", "s"),
    COMBAT("Combat room", "combat_room", "A hostile presence fills the room. A creature of the labyrinth steps forward, blocking your path.", "c"),
    ELITE("Elite room", "elite_room", "The air is heavy and oppressive. A powerful guardian awakens, far stronger than the others.", "e"),
    TREASURE("Treasure room", "treasure_room", "Glittering treasures lie before you, promising great rewards for those who dare to claim them.", "t"),
    TRAP("Trap room", "trap_room", "The room is filled with hidden dangers. One wrong step could trigger a deadly mechanism.", "p"),
    REST("Rest room", "rest_room", "A peaceful sanctuary amidst the chaos. Here, you can catch your breath and prepare for the challenges ahead.", "r"),
    CHOICE("Choice room", "choice_room", "Multiple paths lie before you, each leading to a different fate. Choose wisely.", "h"),
    DISTORTION("Distortion room", "distortion_room", "Reality seems to warp and twist around you. The laws of the labyrinth no longer apply here.", "d"),
    BOSS("Boss room", "boss_room", "The labyrinth trembles as you enter. Its master stands before you, ready to defend its core.", "b"),
    EXIT("Exit room", "exit_room", "A glowing portal stands before you, promising escape from the labyrinth's grasp.", "o");

    private final String name;
    private final String description;
    private final String symbol;
    private final String folderName;

    EMapRoom(String name, String folderName, String description, String symbol) {
        this.name = name;
        this.description = description;
        this.symbol = symbol;
        this.folderName = folderName;
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

    public String getFolderName() {
        return folderName;
    }
}
