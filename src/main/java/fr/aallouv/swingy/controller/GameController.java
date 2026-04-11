package fr.aallouv.swingy.controller;

import fr.aallouv.swingy.model.artifact.Artifact;
import fr.aallouv.swingy.model.entity.Hero;
import fr.aallouv.swingy.model.entity.HeroClass;
import fr.aallouv.swingy.model.entity.Villain;
import fr.aallouv.swingy.model.game.CombatResult;
import fr.aallouv.swingy.model.game.GameEngine;
import fr.aallouv.swingy.model.game.GameState;
import fr.aallouv.swingy.model.map.*;
import fr.aallouv.swingy.repository.HeroRepository;
import fr.aallouv.swingy.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private final HeroRepository repository;
    private final GameEngine engine;
    private View view;
    private GameState state;

    public GameController(HeroRepository repository) {
        this.repository = repository;
        this.engine = new GameEngine();
    }

    public void setView(View view) {
        this.view = view;
    }

    // --- Démarrage ---

    public void start() {
        List<Hero> heroes = repository.findAll();
        view.showMainMenu(heroes);
    }

    public void createHero(String name, String heroClassName) {
        try {
            HeroClass heroClass =
                    HeroClass.valueOf(heroClassName.toUpperCase());
            Hero hero = new Hero.Builder(name, heroClass).build();
            repository.save(hero);
            startGame(hero);
        } catch (IllegalArgumentException e) {
            view.showError("Classe invalide : " + heroClassName);
        }
    }

    public void selectHero(int index) {
        List<Hero> heroes = repository.findAll();
        if (index < 0 || index >= heroes.size()) {
            view.showError("Sélection invalide.");
            return;
        }
        startGame(heroes.get(index));
    }

    private void startGame(Hero hero) {
        GameMap map = new GameMap(hero);
        state = new GameState(hero, map);
        state.getCurrentRoom().setVisited(true);
        view.showHeroStats(hero);
        showCurrentRoom();
    }

    // --- Déplacement ---

    public void move(Direction direction) {
        Room current = state.getCurrentRoom();
        Room next = current.getNeighbor(direction);

        if (next == null) {
            view.showError("Impossible d'aller dans cette direction.");
            return;
        }

        if (next instanceof ExitRoom && !state.isBossDefeated()) {
            view.showMessage("La sortie est bloquée. Vous devez d'abord vaincre le boss.");
            showCurrentRoom();
            return;
        }

        state.setHeroX(next.getX());
        state.setHeroY(next.getY());
        state.setCurrentRoom(next);
        next.setVisited(true);
        next.onEnter(this);
        state.setLastMoveDirection(direction);
    }

    private void showCurrentRoom() {
        Room current = state.getCurrentRoom();
        List<Direction> available = new ArrayList<>();
        for (Direction d : Direction.values()) {
            if (current.hasExit(d)) available.add(d);
        }
        view.showRoom(current, available);
    }

    // --- Callbacks des salles ---

    public void onEnterStart() {
        view.showMessage("Vous essayez de rentrer dans la salle : Start");
        view.showMessage("Vous êtes à la salle de départ.");
        showCurrentRoom();
    }

    public void onEnterRest(RestRoom room) {
        showCurrentRoom();
        view.showRestChoice(room.isActivated());
        pendingRoom = room;
    }

    public void onEnterCoffre(CoffreRoom room) {
        showCurrentRoom();
        view.showCoffreChoice(room.isOpened());
        pendingRoom = room;
    }

    public void confirmRest() {
        if (!(pendingRoom instanceof RestRoom room)) return;
        if (room.isActivated()) {
            view.showMessage("Vous vous êtes déjà reposé ici.");
            pendingRoom = null;
            return;
        }
        int healed = engine.computeHeal(state.getHero());
        room.setUsed(true);
        repository.save(state.getHero());
        pendingRoom = null;
        view.showMessage("Vous récupérez " + healed + " HP.");
        showCurrentRoom();
    }

    public void declineRest() {
        view.showMessage("Vous décidez de ne pas vous reposer pour l'instant.");
        pendingRoom = null;
        showCurrentRoom();
    }

    public void confirmCoffre() {
        if (!(pendingRoom instanceof CoffreRoom room)) return;
        if (room.isOpened()) {
            view.showMessage("Le coffre est déjà ouvert.");
            pendingRoom = null;
            return;
        }
        int gold = 10 + state.getHero().getLevel() * 5 + new java.util.Random().nextInt(20);
        state.getHero().addGold(gold);
        room.setOpened(true);
        repository.save(state.getHero());
        pendingRoom = null;
        view.showMessage("Vous ouvrez le coffre ! +" + gold + " or.");
        showCurrentRoom();
    }

    public void declineCoffre() {
        view.showMessage("Vous décidez d'ouvrir le coffre plus tard.");
        pendingRoom = null;
        showCurrentRoom();
    }

    public void onEnterCombat(CombatRoom room) {
        showCurrentRoom();
        view.showMessage("Vous essayez de rentrer dans la salle : Combat");
        Villain villain = engine.generateVillain(state.getHero().getLevel());
        view.showVillainEncounter(villain);
        pendingVillain = villain;
        pendingRoom = room;
    }

    public void onEnterElite(EliteRoom room) {
        showCurrentRoom();
        view.showMessage("Vous essayez de rentrer dans la salle : Elite");
        Villain villain = engine.generateElite(state.getHero().getLevel());
        view.showVillainEncounter(villain);
        pendingVillain = villain;
        pendingRoom = room;
    }

    public void onEnterBoss(BossRoom room) {
        showCurrentRoom();
        view.showMessage("Vous essayez de rentrer dans la salle : Boss");
        Villain villain = engine.generateBoss(state.getHero().getLevel());
        view.showVillainEncounter(villain);
        pendingVillain = villain;
        pendingRoom = room;
    }

    public void onEnterExit(ExitRoom room) {
        showCurrentRoom();
        view.showMessage("Vous essayez de rentrer dans la salle : Exit");
        if (!state.isBossDefeated()) {
            view.showMessage("La sortie est bloquée. Vous devez d'abord vaincre le boss.");
            showCurrentRoom();
            return;
        }
        state.setVictory(true);
        state.setGameOver(true);
        repository.save(state.getHero());
        view.showGameOver(true);
    }

    public void onEnterTrap(TrapRoom room) {
        showCurrentRoom();
        view.showMessage("Vous essayez de rentrer dans la salle : Trap");
        int damage = engine.computeTrapDamage(state.getHero());
        state.getHero().takeDamage(damage);
        view.showMessage("Un piège ! Vous perdez " + damage + " HP.");
        if (!state.getHero().isAlive()) {
            state.setGameOver(true);
            view.showGameOver(false);
        }
    }

    public void onEnterChoice(ChoiceRoom room) {
        showCurrentRoom();
        view.showChoiceRoom(room);
        pendingRoom = room;
    }

    public void declineChoice() {
        view.showMessage("Vous décidez de revenir plus tard.");
        pendingRoom = null;
        showCurrentRoom();
    }

    public void acceptGoldOffer() {
        if (!(pendingRoom instanceof ChoiceRoom room)) return;
        ChoiceRoom.GoldOffer offer = room.getGoldOffer();
        if (state.getHero().getGold() < offer.cost) {
            view.showMessage("Vous n'avez pas assez d'or.");
            return;
        }
        state.getHero().addGold(-offer.cost);
        applyStatBonus(offer.stat, offer.bonus);
        room.setResolved(true);
        repository.save(state.getHero());
        view.showMessage("Marché conclu ! +" + offer.bonus + " " + offer.stat.name() + " pour " + offer.cost + " or.");
        pendingRoom = null;
        showCurrentRoom();
    }

    public void acceptSacrificeOffer() {
        if (!(pendingRoom instanceof ChoiceRoom room)) return;
        ChoiceRoom.SacrificeOffer offer = room.getSacrificeOffer();
        applyStatBonus(offer.gainStat, offer.gainAmount);
        applyStatBonus(offer.loseStat, -offer.loseAmount);
        room.setResolved(true);
        repository.save(state.getHero());
        view.showMessage("Sacrifice accepté ! +" + offer.gainAmount + " " + offer.gainStat.name()
                + " / -" + offer.loseAmount + " " + offer.loseStat.name());
        pendingRoom = null;
        showCurrentRoom();
    }

    private void applyStatBonus(ChoiceRoom.StatType stat, int amount) {
        switch (stat) {
            case ATTACK  -> state.getHero().setAttack(Math.max(1, state.getHero().getAttack() + amount));
            case DEFENSE -> state.getHero().setDefense(Math.max(0, state.getHero().getDefense() + amount));
            case HP      -> {
                int newMax = Math.max(1, state.getHero().getMaxHitPoints() + amount);
                state.getHero().setMaxHitPoints(newMax);
                state.getHero().setHitPoints(Math.min(state.getHero().getHitPoints(), newMax));
            }
        }
    }

    public void onEnterDistortion(DistortionRoom room) {
        showCurrentRoom();
        view.showMessage("Vous essayez de rentrer dans la salle : Distortion");
        List<Room> visited = new ArrayList<>();
        GameMap map = state.getMap();
        for (int x = 0; x < map.getSize(); x++) {
            for (int y = 0; y < map.getSize(); y++) {
                Room r = map.getRoom(x, y);
                if (r != null && r.isVisited() && r != room) visited.add(r);
            }
        }
        if (visited.isEmpty()) {
            view.showMessage("La distortion n'a aucun effet.");
            showCurrentRoom();
            return;
        }
        Room target = visited.get(new java.util.Random().nextInt(visited.size()));
        state.setHeroX(target.getX());
        state.setHeroY(target.getY());
        state.setCurrentRoom(target);
        view.showMessage("Vous êtes téléporté vers une salle visitée !");
        showCurrentRoom();
    }

    // --- Combat ---

    private Villain pendingVillain;
    private Room pendingRoom;

    public void fight() {
        if (pendingVillain == null) return;

        Hero hero = state.getHero();
        CombatResult result = engine.simulateCombat(hero, pendingVillain);
        view.showCombatResult(result.isHeroWon(), result.getXpGained());

        if (!result.isHeroWon()) {
            state.setGameOver(true);
            view.showGameOver(false);
            return;
        }

        if (pendingRoom instanceof BossRoom bossRoom) {
            bossRoom.setBossDefeated(true);
            state.setBossDefeated(true);
            view.showMessage("Le boss est vaincu ! La sortie est maintenant accessible.");
        }
        if (pendingRoom instanceof CombatRoom combatRoom) combatRoom.setCleared(true);
        if (pendingRoom instanceof EliteRoom eliteRoom) eliteRoom.setCleared(true);

        if (result.hasArtifactDrop()) {
            view.showArtifactDrop(result.getDroppedArtifact());
            pendingArtifact = result.getDroppedArtifact();
        } else {
            pendingVillain = null;
            pendingRoom = null;
            repository.save(hero);
            showCurrentRoom();
        }
    }

    public void run() {
        if (pendingVillain == null) return;

        if (engine.tryRun()) {
            view.showMessage("Vous réussissez à fuir !");
            pendingVillain = null;
            pendingRoom = null;
            showCurrentRoom();
        } else {
            view.showMessage("Échec de la fuite ! Vous devez combattre.");
            fight();
        }
    }

    // --- Artefact ---

    private Artifact pendingArtifact;

    public void equipArtifact() {
        if (pendingArtifact == null) return;
        state.getHero().equipArtifact(pendingArtifact);
        view.showMessage("Artefact équipé : " + pendingArtifact);
        clearPending();
    }

    public void ignoreArtifact() {
        view.showMessage("Vous ignorez l'artefact.");
        clearPending();
    }

    private void clearPending() {
        pendingArtifact = null;
        pendingVillain = null;
        pendingRoom = null;
        repository.save(state.getHero());
        showCurrentRoom();
    }

    // --- Getters utiles ---

    public GameState getState() { return state; }
    public boolean isGameOver() { return state != null && state.isGameOver(); }
}
