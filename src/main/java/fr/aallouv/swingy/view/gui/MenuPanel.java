package fr.aallouv.swingy.view.gui;

import fr.aallouv.swingy.ViewSwitcher;
import fr.aallouv.swingy.controller.GameController;
import fr.aallouv.swingy.model.entity.Hero;
import fr.aallouv.swingy.model.entity.HeroClass;
import fr.aallouv.swingy.repository.FileHeroRepository;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuPanel extends JPanel {

    private final GameController controller;
    private final MainWindow window;

    private final JPanel heroListPanel;
    private final JTextField nameField;
    private final JComboBox<HeroClass> classCombo;
    private ViewSwitcher switcher;

    public MenuPanel(GameController controller, MainWindow window) {
        this.controller = controller;
        this.window = window;

        setLayout(new BorderLayout(10, 10));

        // Titre
        JLabel title = new JLabel("SWINGY", SwingConstants.CENTER);
        title.setFont(new Font("Monospaced", Font.BOLD, 32));
        add(title, BorderLayout.NORTH);

        // Panel central divisé en deux : création à gauche, sélection à droite
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // --- Création ---
        JPanel createPanel = new JPanel();
        createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.Y_AXIS));
        createPanel.setBorder(BorderFactory.createTitledBorder("Créer un héros"));

        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        classCombo = new JComboBox<>(HeroClass.values());
        classCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JButton createBtn = new JButton("Créer");
        createBtn.addActionListener(e -> onCreateHero());

        createPanel.add(new JLabel("Nom du héros :"));
        createPanel.add(Box.createVerticalStrut(5));
        createPanel.add(nameField);
        createPanel.add(Box.createVerticalStrut(10));
        createPanel.add(new JLabel("Classe :"));
        createPanel.add(Box.createVerticalStrut(5));
        createPanel.add(classCombo);
        createPanel.add(Box.createVerticalStrut(15));
        createPanel.add(createBtn);

        // --- Sélection ---
        JPanel selectPanel = new JPanel(new BorderLayout(5, 5));
        selectPanel.setBorder(BorderFactory.createTitledBorder("Héros existants"));

        heroListPanel = new JPanel();
        heroListPanel.setLayout(new BoxLayout(heroListPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(heroListPanel);
        selectPanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(createPanel);
        centerPanel.add(selectPanel);
        add(centerPanel, BorderLayout.CENTER);

        JButton consoleBtn = new JButton("Passer en mode Console");
        consoleBtn.addActionListener(e -> { if (switcher != null) switcher.switchToConsole(); });
        add(consoleBtn, BorderLayout.SOUTH);
    }

    public void setSwitcher(ViewSwitcher switcher) {
        this.switcher = switcher;
    }

    public void refresh() {
        heroListPanel.removeAll();

        List<Hero> heroes = controller.getHeroes();

        if (heroes.isEmpty()) {
            heroListPanel.add(new JLabel("Aucun héros sauvegardé."));
        } else {
            for (int i = 0; i < heroes.size(); i++) {
                Hero hero = heroes.get(i);
                int index = i;

                JPanel row = new JPanel(new BorderLayout(5, 0));
                row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
                row.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

                String info = "<html><b>" + hero.getName() + "</b> — "
                        + hero.getHeroClass().displayName
                        + " — Niv." + hero.getLevel()
                        + " — HP: " + hero.getHitPoints() + "/" + hero.getMaxHitPoints()
                        + "</html>";

                JButton selectBtn = new JButton("Jouer");
                selectBtn.addActionListener(e -> onSelectHero(index));

                row.add(new JLabel(info), BorderLayout.CENTER);
                row.add(selectBtn, BorderLayout.EAST);
                heroListPanel.add(row);
            }
        }

        heroListPanel.revalidate();
        heroListPanel.repaint();
    }

    private void onCreateHero() {
        String name = nameField.getText().trim();
        HeroClass heroClass = (HeroClass) classCombo.getSelectedItem();
        controller.createHero(name, heroClass.name());
    }

    private void onSelectHero(int index) {
        controller.setView(window.getGamePanel());
        controller.selectHero(index);
        window.showGame();
    }
}
