import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

public class SchermataIniziale extends JPanel {

    CardLayout cl;
    JPanel contenitore;
    JButton bottone;
    BufferedImage image;

    // 🎵 Musica globale condivisa
    public static Clip clipSottofondo;
    public static int volume = 50; // da 0 a 100

    public SchermataIniziale(CardLayout cl, JPanel contenitore) {
        this.cl = cl;
        this.contenitore = contenitore;
        this.setLayout(null);

        // 🔹 Caricamento immagine di sfondo
        try {
            image = ImageIO.read(new File("alt f4.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 🔹 Bottone Start
        ImageIcon icon = new ImageIcon(getClass().getResource("Start_button.png"));
        bottone = new JButton(icon);
        bottone.setBounds(920, 62, bottone.getPreferredSize().width, bottone.getPreferredSize().height);
        bottone.setBorderPainted(false);
        bottone.setOpaque(false);
        bottone.setContentAreaFilled(false);
        bottone.setFocusPainted(false);
        this.add(bottone);

        // 🔹 Listener per ridimensionamento e partenza audio
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(SchermataIniziale.this);
                frame.setSize(1259, 718);
                startBackgroundMusic();
            }
        });

        // 🔹 Bottone Start gioco
        bottone.addActionListener(e -> {
            cl.show(contenitore, "GAME");
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(SchermataIniziale.this);
            frame.setSize(new Dimension(400, 800));
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    // 🔹 Metodo per avviare la musica
    private void startBackgroundMusic() {
        try {
            if (clipSottofondo == null) {
                AudioInputStream audio = AudioSystem.getAudioInputStream(new File("audioSchermataIniziale.wav"));
                clipSottofondo = AudioSystem.getClip();
                clipSottofondo.open(audio);
                setVolume(volume); // applica volume attuale
                clipSottofondo.loop(Clip.LOOP_CONTINUOUSLY);
                clipSottofondo.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Metodo per cambiare volume
    public static void setVolume(int vol) {
        volume = vol;
        if (clipSottofondo != null) {
            try {
                FloatControl gain = (FloatControl) clipSottofondo.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (-80 + (vol / 100.0f) * 80);
                gain.setValue(dB);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 🔹 Metodo per leggere il volume
    public static int getVolume() {
        return volume;
    }
}
