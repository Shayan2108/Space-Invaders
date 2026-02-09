import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

public class PauseMenuDialog extends JDialog {

    private MyPanel gamePanel;

    public PauseMenuDialog(Window owner, MyPanel gamePanel) {
        super(owner, ModalityType.APPLICATION_MODAL);
        this.gamePanel = gamePanel;

        // 🎨 Configurazione dialog
        setUndecorated(true);
        setSize(320, 260);
        setLocationRelativeTo(owner);
        setLayout(null);

        // Sfondo nero semi-trasparente
        Color sfondoPausa = new Color(0, 0, 0, 230);
        getContentPane().setBackground(sfondoPausa);

        // Titolo PAUSA
        JLabel title = new JLabel("PAUSA", SwingConstants.CENTER);
        title.setFont(new Font("Monospaced", Font.BOLD, 28));
        title.setForeground(new Color(255, 180, 255));
        title.setBounds(60, 20, 200, 40);
        add(title);

        // 🔹 Label Volume
        JLabel volumeLabel = new JLabel("Volume:");
        volumeLabel.setForeground(new Color(255, 180, 255));
        volumeLabel.setBounds(50, 80, 70, 20);
        add(volumeLabel);

        // 🔹 Slider
        JSlider volumeSlider = new JSlider(0, 100, SchermataIniziale.getVolume());
        volumeSlider.setBounds(50, 105, 250, 30);
        volumeSlider.setOpaque(false);
        volumeSlider.setFocusable(false);
        volumeSlider.setPaintTicks(false);
        volumeSlider.setPaintLabels(false);

        // Variabile per il volume corrente da disegnare
        int[] currentVolume = { SchermataIniziale.getVolume() };

        // Custom UI per slider
        volumeSlider.setUI(new javax.swing.plaf.basic.BasicSliderUI(volumeSlider) {
            @Override
            public void paintThumb(Graphics g) {
                g.setColor(new Color(255, 180, 255));
                g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
            }

            @Override
            public void paintTrack(Graphics g) {
                g.setColor(new Color(80, 0, 120));
                g.fillRect(trackRect.x, trackRect.y + trackRect.height / 3, trackRect.width, trackRect.height / 3);
            }

            @Override
            public void paintFocus(Graphics g) {
            }
        });

        // Aggiorna volume e percentuale
        volumeSlider.addChangeListener(e -> {
            int val = volumeSlider.getValue();
            SchermataIniziale.setVolume(val);
            currentVolume[0] = val;
            repaint(); // ridisegna percentuale
        });

        add(volumeSlider);

        // 🔹 Componente per disegnare la percentuale
        JComponent percentDisplay = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(255, 180, 255));
                g.setFont(new Font("Monospaced", Font.BOLD, 16));
                g.drawString(currentVolume[0] + "%", 250, 95);
            }
        };
        percentDisplay.setBounds(0, 0, getWidth(), getHeight());
        percentDisplay.setOpaque(false);
        add(percentDisplay);

        // 🔹 Bottone Torna al gioco
        JButton resumeBtn = new JButton("Torna al gioco");
        resumeBtn.setBounds(50, 160, 250, 35);
        resumeBtn.setFocusPainted(false);
        resumeBtn.setForeground(Color.WHITE);
        resumeBtn.setBackground(new Color(80, 0, 120));
        resumeBtn.addActionListener(e -> {
            gamePanel.isPaused = false;
            dispose();
        });
        add(resumeBtn);

        // 🔹 Bottone Esci dalla partita
        JButton quitBtn = new JButton("Esci dalla partita");
        quitBtn.setBounds(50, 205, 250, 35);
        quitBtn.setFocusPainted(false);
        quitBtn.setForeground(Color.WHITE);
        quitBtn.setBackground(new Color(80, 0, 120));
        quitBtn.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(gamePanel);
            frame.dispose();
            System.exit(0);
        });
        add(quitBtn);
    }
}
