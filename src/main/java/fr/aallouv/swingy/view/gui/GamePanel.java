package fr.aallouv.swingy.view.gui;

import fr.aallouv.swingy.controller.GameController;
import fr.aallouv.swingy.model.artifact.Artifact;
import fr.aallouv.swingy.model.entity.Hero;
import fr.aallouv.swingy.model.entity.Villain;
import fr.aallouv.swingy.model.map.Direction;
import fr.aallouv.swingy.model.map.Room;
import fr.aallouv.swingy.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class GamePanel extends JPanel implements View, KeyListener {

    private final GameController controller;
    private final MainWindow window;

    private final RoomPanel roomPanel;
    private final JTextArea logArea;
    private final JLabel statsLabel;
    private final JLabel roomLabel;

    private boolean waitingForCombatChoice;
    private boolean waitingForArtifactChoice;

    public GamePanel(GameController controller, MainWindow window) {
        this.controller = controller;
        this.window = window;

        setLayout(new BorderLayout(5, 5));
        setFocusable(true);
        addKeyListener(this);

        // --- Zone salle (centre) ---
        roomPanel = new RoomPanel();
        roomPanel.setPreferredSize(new Dimension(400, 400));
        add(roomPanel, BorderLayout.CENTER);

        // --- Zone droite : stats + logs ---
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setPreferredSize(new Dimension(360, 0));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));

        // Stats
        statsLabel = new JLabel("<html>Stats...</html>");
        statsLabel.setVerticalAlignment(SwingConstants.TOP);
        statsLabel.setBorder(BorderFactory.createTitledBorder("Héros"));
        statsLabel.setPreferredSize(new Dimension(340, 150));

        // Type de salle
        roomLabel = new JLabel("Salle : -");
        roomLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Logs
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Journal"));

        // Instructions clavier
        JLabel keysLabel = new JLabel(
                "<html><center>↑ Nord &nbsp; ↓ Sud &nbsp; ← Ouest &nbsp; → Est<br>" +
                        "F : Combattre &nbsp; R : Fuir &nbsp; E : Équiper &nbsp; I : Ignorer<br>" +
                        "S : Stats &nbsp; M : Menu</center></html>",
                SwingConstants.CENTER
        );
        keysLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        rightPanel.add(statsLabel, BorderLayout.NORTH);
        rightPanel.add(roomLabel, BorderLayout.CENTER);

        JPanel bottomRight = new JPanel(new BorderLayout());
        bottomRight.add(scrollPane, BorderLayout.CENTER);
        bottomRight.add(keysLabel, BorderLayout.SOUTH);
        rightPanel.add(bottomRight, BorderLayout.SOUTH);

        add(rightPanel, BorderLayout.EAST);
    }

    public void refresh() {
        waitingForCombatChoice = false;
        waitingForArtifactChoice = false;
        logArea.setText("");
        requestFocusInWindow();
    }

    // --- View ---

    @Override
    public void showMainMenu(List<Hero> heroes) {}

    @Override
    public void showHeroStats(Hero hero) {
        String html = "<html>"
                + "<b>" + hero.getName() + "</b> — " + hero.getHeroClass().displayName + "<br>"
                + "Niveau : " + hero.getLevel() + "<br>"
                + "XP : " + hero.getExperience() + " / " + Hero.xpRequiredForLevel(hero.getLevel()) + "<br>"
                + "HP : " + hero.getHitPoints() + " / " + hero.getMaxHitPoints() + "<br>"
                + "ATK : " + hero.getAttack() + " &nbsp; DEF : " + hero.getDefense() + "<br>"
                + "Or : " + hero.getGold()
                + "</html>";
        statsLabel.setText(html);
    }

    @Override
    public void showRoom(Room room, List<Direction> availableDirections) {
        roomLabel.setText("Salle : " + room.getRoomType() + " (" + room.getX() + ", " + room.getY() + ")");
        roomPanel.setRoom(room, availableDirections);
        waitingForCombatChoice = false;
        waitingForArtifactChoice = false;

        if (controller.getState() != null) {
            showHeroStats(controller.getState().getHero());
        }
    }

    @Override
    public void showVillainEncounter(Villain villain) {
        log("⚔ Ennemi : " + villain.getName()
                + " | HP:" + villain.getHitPoints()
                + " ATK:" + villain.getAttack()
                + " DEF:" + villain.getDefense());
        log("→ F pour combattre, R pour fuir.");
        waitingForCombatChoice = true;
    }

    @Override
    public void showCombatResult(boolean heroWon, int xpGained) {
        if (heroWon) {
            log("✔ Victoire ! +" + xpGained + " XP");
        } else {
            log("✘ Vous avez été vaincu...");
        }
    }

    @Override
    public void showArtifactDrop(Artifact artifact) {
        log("★ Artefact trouvé : " + artifact);
        log("→ E pour équiper, I pour ignorer.");
        waitingForArtifactChoice = true;
    }

    @Override
    public void showLevelUp(Hero hero) {
        log("▲ LEVEL UP ! Vous êtes niveau " + hero.getLevel());
    }

    @Override
    public void showMessage(String message) {
        log(message);
    }

    @Override
    public void showError(String message) {
        log("[Erreur] " + message);
    }

    @Override
    public void showGameOver(boolean victory) {
        String msg = victory ? "VICTOIRE ! Vous avez terminé le niveau !" : "GAME OVER. Votre héros est mort.";
        log(msg);
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, msg, "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
            window.showMenu();
        });
    }

    // --- KeyListener ---

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (waitingForCombatChoice) {
            if (key == KeyEvent.VK_F) controller.fight();
            if (key == KeyEvent.VK_R) controller.run();
            return;
        }

        if (waitingForArtifactChoice) {
            if (key == KeyEvent.VK_E) controller.equipArtifact();
            if (key == KeyEvent.VK_I) controller.ignoreArtifact();
            return;
        }

        switch (key) {
            case KeyEvent.VK_UP    -> controller.move(Direction.NORTH);
            case KeyEvent.VK_DOWN  -> controller.move(Direction.SOUTH);
            case KeyEvent.VK_RIGHT -> controller.move(Direction.EAST);
            case KeyEvent.VK_LEFT  -> controller.move(Direction.WEST);
            case KeyEvent.VK_S     -> { if (controller.getState() != null) showHeroStats(controller.getState().getHero()); }
            case KeyEvent.VK_M     -> window.showMenu();
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    // --- Utilitaire ---

    private void log(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
}