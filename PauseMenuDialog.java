import javax.swing.*;
import java.awt.*;

public class PauseMenuDialog extends JDialog {

    private MyPanel gamePanel;

    public PauseMenuDialog(Window owner, MyPanel gamePanel) {
        super(owner, ModalityType.APPLICATION_MODAL);
        this.gamePanel = gamePanel;

        setUndecorated(true); // niente barra, niente X
        setSize(300, 200); // più piccola del gioco
        setLocationRelativeTo(owner);
        setLayout(null);

        // Sfondo nero semi-trasparente
        getContentPane().setBackground(new Color(0, 0, 0, 230));

        // Titolo PAUSA allineato ai bottoni
        JLabel title = new JLabel("PAUSA");
        title.setFont(new Font("Monospaced", Font.BOLD, 28));
        title.setForeground(new Color(255, 180, 255));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(50, 20, 200, 40); // stesso X/larghezza dei bottoni
        add(title);

        // Bottone Torna al gioco
        JButton resumeBtn = new JButton("Torna al gioco");
        resumeBtn.setBounds(50, 80, 200, 35); // stesso allineamento del titolo
        resumeBtn.setFocusPainted(false);
        resumeBtn.setForeground(Color.WHITE);
        resumeBtn.setBackground(new Color(80, 0, 120));
        resumeBtn.addActionListener(e -> {
            gamePanel.isPaused = false;
            dispose();
        });
        add(resumeBtn);

        // Bottone Esci dalla partita
        JButton quitBtn = new JButton("Esci dalla partita");
        quitBtn.setBounds(50, 125, 200, 35);
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
