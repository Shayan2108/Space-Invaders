import javax.swing.*;
import java.awt.*;

public class PauseMenuDialog extends JDialog {

    private JSlider volumeSlider;
    private JLabel volumeLabel;
    private MyPanel gamePanel;

    public PauseMenuDialog(Window owner, MyPanel gamePanel) {
        super(owner, "Menu Pausa", ModalityType.APPLICATION_MODAL);
        this.gamePanel = gamePanel;

        setSize(400, 300);
        setLocationRelativeTo(owner);
        setLayout(null);
        getContentPane().setBackground(new Color(0, 0, 0, 200));

        // 🔹 Torna al gioco
        JButton resumeBtn = new JButton("Torna al gioco");
        resumeBtn.setBounds(120, 60, 160, 40);
        resumeBtn.addActionListener(e -> {
            gamePanel.isPaused = false;
            dispose();
        });
        add(resumeBtn);

        // 🔹 Esci dal gioco
        JButton quitBtn = new JButton("Esci dalla partita");
        quitBtn.setBounds(120, 120, 160, 40);
        quitBtn.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(gamePanel);
            frame.dispose();
            System.exit(0);
        });
        add(quitBtn);

        // 🔹 Volume
        volumeLabel = new JLabel("Volume: " + gamePanel.getVolume() + "%");
        volumeLabel.setForeground(Color.WHITE);
        volumeLabel.setBounds(150, 180, 200, 20);
        add(volumeLabel);

        volumeSlider = new JSlider(0, 100, gamePanel.getVolume());
        volumeSlider.setBounds(100, 210, 200, 40);
        volumeSlider.addChangeListener(e -> {
            int vol = volumeSlider.getValue();
            gamePanel.setVolume(vol);
            volumeLabel.setText("Volume: " + vol + "%");
        });
        add(volumeSlider);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}
