
/**
 * @file Bullets.java
 *
 * @author Mohammad Shayan Attari Bin Mohammad Zeshan Attari
 * @version 1.0
 *
 * @brief Classe per i proiettili nel gioco
 *
 * La classe rappresenta un singolo proiettile sparato dalla nave.
 * Contiene posizione, movimento e l'immagine del proiettile.
 */
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Bullets {

    /** pannello principale del gioco, usato per accedere alle immagini e liste */
    MyPanel m;

    /** immagine del proiettile */
    BufferedImage image;

    /** posizione orizzontale del proiettile */
    int x;

    /** posizione verticale del proiettile */
    int y;

    /** velocità di movimento verticale */
    int movimento;

    Rectangle hitBox;

    /**
     * @brief costruttore della classe Bullets
     *
     *        Inizializza il proiettile con posizione, velocità e immagine casuale.
     *        Attributi modificati: m, x, y, movimento, image
     *
     * @param m         pannello principale del gioco
     * @param x         posizione iniziale x
     * @param y         posizione iniziale y
     * @param movimento velocità di movimento verticale del proiettile
     */
    public Bullets(MyPanel m, int x, int y, int movimento) {
        this.m = m;
        this.x = x;
        this.y = y;
        this.movimento = movimento;
        this.image = m.immaginiBullet.get(m.r.nextInt(1, 66));
        hitBox = new Rectangle(x, y - 60, 20, 60);
    }

    /**
     * @brief aggiorna la posizione del proiettile
     *
     *        Muove il proiettile verso l'alto. Se esce dallo schermo
     *        viene rimosso dalla lista dei proiettili.
     */
    public void sposta() {
        synchronized (m.bullets) {
            if (y - movimento > 0) {
                y -= movimento;
                hitBox.y -= movimento;
            } else {
                m.bullets.remove(this);
            }
        }
    }
}
