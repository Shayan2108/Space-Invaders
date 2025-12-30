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
        if (m.bulletMassime - m.bullets.size() > 0) {
            m.bullets.add(new Bullets(m, m.xNave + m.paddingBullet1, m.yNave, 8));
            m.bullets.add(new Bullets(m, m.xNave + m.paddingBullet2, m.yNave, 8));
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
