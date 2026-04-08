package fr.aallouv.swingy.model.artifact;

public class Artifact {

    private final String name;
    private final ArtifactType type;
    private final int bonus;

    public Artifact(String name, ArtifactType type, int bonus) {
        this.name  = name;
        this.type  = type;
        this.bonus = bonus;
    }

    public String getName()  { return name; }
    public ArtifactType getType()  { return type; }
    public int getBonus() { return bonus; }

    @Override
    public String toString() {
        return name + " (" + type + " +" + bonus + ")";
    }
}
