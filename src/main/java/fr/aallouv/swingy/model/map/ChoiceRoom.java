package fr.aallouv.swingy.model.map;

import fr.aallouv.swingy.controller.GameController;

import java.util.Random;

public class ChoiceRoom extends Room {

    public enum StatType { ATTACK, DEFENSE, HP }

    public static class GoldOffer {
        public final StatType stat;
        public final int bonus;
        public final int cost;

        public GoldOffer(StatType stat, int bonus, int cost) {
            this.stat = stat;
            this.bonus = bonus;
            this.cost = cost;
        }
    }

    public static class SacrificeOffer {
        public final StatType gainStat;
        public final int gainAmount;
        public final StatType loseStat;
        public final int loseAmount;

        public SacrificeOffer(StatType gainStat, int gainAmount, StatType loseStat, int loseAmount) {
            this.gainStat = gainStat;
            this.gainAmount = gainAmount;
            this.loseStat = loseStat;
            this.loseAmount = loseAmount;
        }
    }

    private final GoldOffer goldOffer;
    private final SacrificeOffer sacrificeOffer;
    private boolean resolved;

    public ChoiceRoom(int x, int y) {
        super(x, y);
        this.resolved = false;

        Random random = new Random();
        StatType[] stats = StatType.values();

        // Offre or : bonus modéré pour un prix cohérent avec les coffres
        StatType goldStat = stats[random.nextInt(stats.length)];
        int goldBonus = 3 + random.nextInt(8);
        int goldCost = 15 + goldBonus * 3 + random.nextInt(15);
        this.goldOffer = new GoldOffer(goldStat, goldBonus, goldCost);

        // Offre sacrifice : gain plus élevé, perte sur une stat différente
        StatType gainStat = stats[random.nextInt(stats.length)];
        StatType loseStat;
        do {
            loseStat = stats[random.nextInt(stats.length)];
        } while (loseStat == gainStat);

        int gainAmount = 8 + random.nextInt(10);
        int loseAmount = 3 + random.nextInt(6);
        this.sacrificeOffer = new SacrificeOffer(gainStat, gainAmount, loseStat, loseAmount);
    }

    @Override
    public void onEnter(GameController controller) {
        controller.onEnterChoice(this);
    }

    @Override
    public String getRoomType() { return "CHOICE"; }

    @Override
    public boolean isActivated() { return resolved; }

    public GoldOffer getGoldOffer() { return goldOffer; }
    public SacrificeOffer getSacrificeOffer() { return sacrificeOffer; }
    public boolean isResolved() { return resolved; }
    public void setResolved(boolean resolved) { this.resolved = resolved; }
}
