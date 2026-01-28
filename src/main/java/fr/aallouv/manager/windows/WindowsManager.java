package fr.aallouv.manager.windows;

import fr.aallouv.App;
import fr.aallouv.enums.EGameViews;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;

public class WindowsManager extends JFrame {

    public WindowsManager() {

        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GraphicsDevice gd = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();

        gd.setFullScreenWindow(this);

        // ---- CONSOLE ----
        JTextArea console = new JTextArea();
        console.setEditable(false);
        console.setFont(new Font("SansSerif", Font.PLAIN, 18));
        console.setBackground(Color.BLACK);
        console.setForeground(Color.GREEN);
        console.setCaretColor(Color.GREEN);

        JScrollPane consoleScroll = new JScrollPane(console);
        consoleScroll.setPreferredSize(new Dimension(450, 200)); // ✅ 50px -> 450px
        add(consoleScroll, BorderLayout.SOUTH);

        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;

        TextAreaOutputStream taStream = new TextAreaOutputStream(console);
        System.setOut(new PrintStream(new TeeOutputStream(originalOut, taStream), true));
        System.setErr(new PrintStream(new TeeOutputStream(originalErr, taStream), true));

        // ---- INPUT + BUTTON ----
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));

        JTextField input = new JTextField(20);
        input.setFont(new Font("SansSerif", Font.BOLD, 16));
        input.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        JButton send = new JButton("Envoyer");
        send.setFont(new Font("SansSerif", Font.BOLD, 16));
        send.setFocusPainted(false);
        send.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));

        send.addActionListener(e -> {
            System.out.println("User typed: " + input.getText());
            input.setText("");
            input.requestFocusInWindow();
        });

        input.addActionListener(e -> send.doClick()); // Entrée = envoyer

        panel.add(input);
        panel.add(send);
        add(panel, BorderLayout.NORTH);

        // ✅ IMPORTANT : affichage en dernier
        setVisible(App.getApp().getGameManager().getGameViews() == EGameViews.GUI);

        // et si jamais tu modifies l'UI après coup :
        revalidate();
        repaint();

        System.out.println("Console initialized in GUI mode.");
    }


    private void styleInput(JTextField input) {
        input.setFont(new Font("SansSerif", Font.BOLD, 16));
        input.setBackground(new Color(245, 245, 245));
        input.setForeground(Color.BLACK);
        input.setCaretColor(Color.BLACK);
        input.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBackground(new Color(60, 130, 220));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Color normal = button.getBackground();
        Color hover = new Color(40, 110, 200);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) { button.setBackground(hover); }
            @Override public void mouseExited(java.awt.event.MouseEvent e) { button.setBackground(normal); }
        });
    }

    public void setNimbus() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }
        } catch (Exception ignored) {}
    }
}
