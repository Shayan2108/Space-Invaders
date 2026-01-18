/**
 * @file MyMouse.java
 *
 * @author Mohammad Shayan Attri Bin Mohammad Zeshan Attari
 * @version 1.0
 *
 * @brief Gestione del mouse
 *
 * Questa classe serve per gestire il click del mouse
 * per sparare i proiettili nel gioco.
 */
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MyMouse implements MouseListener {

    /**
     * riferimento al pannello di gioco
     */
    MyPanel m;

    /**
     * @brief costruttore della classe MyMouse
     *
     * Collega il listener del mouse al pannello di gioco
     *
     * @param m pannello principale del gioco
     */
    public MyMouse(MyPanel m) {
        this.m = m;
    }

    /**
     * @brief gestione click del mouse
     *
     * Quando il mouse viene cliccato, se ci sono
     * proiettili disponibili, ne vengono creati due
     * ai lati della nave.
     *
     * @param e evento del mouse
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (m.bulletMassime - m.bullets.size() > 1) {
            m.bullets.add(new Bullets(m, m.xNave + m.paddingBullet1, m.yNave, 8));
            m.bullets.add(new Bullets(m, m.xNave + m.paddingBullet2, m.yNave, 8));
            try {
                try {
                    m.audio = AudioSystem.getAudioInputStream(new File("SuonoSparo.wav"));
                } catch (UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                m.sparoClip.add(AudioSystem.getClip());
                m.sparoClip.getLast().open(m.audio);
                m.sparoClip.getLast().start();
            } catch (LineUnavailableException | IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    /**
     * @brief non utilizzato
     * 
     * @param e evento del mouse
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // non usato
    }

    /**
     * @brief non utilizzato
     *
     * @param e evento del mouse
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // non usato
    }

    /**
     * @brief non utilizzato
     *
     * @param e evento del mouse
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // non usato
    }

    /**
     * @brief non utilizzato
     *
     * @param e evento del mouse
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // non usato
    }
}
