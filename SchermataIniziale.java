
/**
* @author  Statella Giuseppe Salvatore, statella.giuseppe01@gmail.com
* @version 1.0
* @file SchermataIniziale.java 
* 
* @brief Schermata iniziale del gioco Space Invaders.
*
* Contiene lo sfondo, il bottone Start e il bottone Help.
* Gestisce anche la musica di sottofondo con volume regolabile.
*/

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 * @class SchermataIniziale
 * 
 * @brief Pannello della schermata iniziale del gioco.
 * 
 *        Gestisce l'interfaccia iniziale: sfondo, bottoni Start e Help,
 *        e la musica di sottofondo. Consente di iniziare il gioco e
 *        aprire la finestra di aiuto.
 */
public class SchermataIniziale extends JPanel {

    /** Layout del contenitore principale */
    CardLayout cl;

    /** Pannello contenitore principale */
    JPanel contenitore;

    /** Bottone Start */
    JButton bottone;

    /** Sfondo della schermata iniziale */
    BufferedImage image;

    /** Clip musicale di sottofondo condivisa tra tutte le schermate */
    public static Clip clipSottofondo;

    /** Volume corrente della musica, da 0 a 100 */
    public static int volume = 100;

    /**
     * @brief Costruttore della schermata iniziale.
     *
     *        Inizializza il layout, lo sfondo, il bottone Start,
     *        il bottone Help e il listener per la musica di sottofondo.
     * @param cl          layout del contenitore principale
     * @param contenitore pannello principale che contiene le varie schermate
     */
    public SchermataIniziale(CardLayout cl, JPanel contenitore) {
        this.cl = cl;
        this.contenitore = contenitore;
        this.setLayout(null);

        // Caricamento immagine di sfondo
        try {
            image = ImageIO.read(new File("alt f4.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Bottone Start
        ImageIcon startIcon = new ImageIcon(getClass().getResource("Start_button.png"));
        bottone = new JButton(startIcon);
        bottone.setBounds(920, 62, 250, 63); // dimensioni standard
        bottone.setBorderPainted(false);
        bottone.setOpaque(false);
        bottone.setContentAreaFilled(false);
        bottone.setFocusPainted(false);
        this.add(bottone);

        // Bottone Help leggermente più alto
        try {
            ImageIcon helpIconOriginal = new ImageIcon(getClass().getResource("help_buttom.png"));

            // Dimensioni target: stessa larghezza dello Start, altezza leggermente maggiore
            int targetWidth = 250;
            int targetHeight = 80;

            // Ridimensionamento proporzionale all'altezza desiderata
            Image scaledHelp = helpIconOriginal.getImage().getScaledInstance(targetWidth, targetHeight,
                    Image.SCALE_SMOOTH);
            ImageIcon helpIcon = new ImageIcon(scaledHelp);

            JButton helpBtn = new JButton(helpIcon);
            helpBtn.setBounds(bottone.getX(), bottone.getY() + bottone.getHeight() + 20, targetWidth, targetHeight);

            // Trasparenza come Start
            helpBtn.setBorderPainted(false);
            helpBtn.setOpaque(false);
            helpBtn.setContentAreaFilled(false);
            helpBtn.setFocusPainted(false);

            // Apre la finestra HelpDialog
            helpBtn.addActionListener(e -> {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(SchermataIniziale.this);
                new HelpDialog(frame).setVisible(true);
            });

            this.add(helpBtn);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Listener per ridimensionamento e partenza audio
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(SchermataIniziale.this);
                frame.setSize(1259, 718);
                startBackgroundMusic();
            }
        });

        // Bottone Start gioco
        bottone.addActionListener(e -> {
            cl.show(contenitore, "GAME");
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(SchermataIniziale.this);
            frame.setSize(new Dimension(400, 800));
        });
    }

    /**
     * @brief Ridisegna lo sfondo della schermata.
     *
     * @param g contesto grafico
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    /**
     * @brief Avvia la musica di sottofondo della schermata iniziale.
     *
     *        Se la clip non è già caricata, la apre e la fa partire in loop
     *        continuo.
     */
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

    /**
     * @brief Imposta il volume della musica.
     *
     *        Usa scala logaritmica per una percezione più naturale.
     *
     * @param vol volume desiderato, da 0 a 100
     */
    public static void setVolume(int vol) {
        volume = vol;
        if (clipSottofondo != null) {
            try {
                FloatControl gain = (FloatControl) clipSottofondo.getControl(FloatControl.Type.MASTER_GAIN);
                float min = -80f;
                float max = 0f;
                float dB = (vol == 0) ? min : (float) (20 * Math.log10(vol / 100.0));
                if (dB < min)
                    dB = min;
                if (dB > max)
                    dB = max;
                gain.setValue(dB);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @brief Restituisce il volume corrente della musica.
     *
     * @return volume corrente, da 0 a 100
     */
    public static int getVolume() {
        return volume;
    }
}
