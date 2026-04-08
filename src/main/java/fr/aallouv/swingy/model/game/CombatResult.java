package fr.aallouv.swingy.model.game;

import fr.aallouv.swingy.model.artifact.Artifact;

public class CombatResult {

    private final boolean heroWon;
    private final int xpGained;
    private final Artifact droppedArtifact;

    public CombatResult(boolean heroWon, int xpGained, Artifact droppedArtifact) {
        this.heroWon = heroWon;
        this.xpGained = xpGained;
        this.droppedArtifact = droppedArtifact;
    }

    public boolean isHeroWon() { return heroWon; }
    public int getXpGained() { return xpGained; }
    public Artifact getDroppedArtifact() { return droppedArtifact; }
    public boolean hasArtifactDrop() { return droppedArtifact != null; }
}
