/**
 * @file MyKey.java
 *
 * @author Mohammad Shayan Attari Bin Mohammad Zeshan Attari
 * @version 1.0
 *
 * @brief Gestione della tastiera
 *
 * Questa classe serve per gestire i tasti della tastiera.
 * Permette di muovere la nave e di iniziare la partita.
 */
import java.awt.event.*;

public class MyKey implements KeyListener {

    /**
     * riferimento al pannello di gioco
     */
    MyPanel m;

    /**
     * controlla se il tasto A o D è premuto
     */
    boolean isA, isD;

    /**
     * @brief costruttore della classe MyKey
     *
     * inizializza il listener della tastiera
     * e collega il pannello di gioco
     *
     * @param m pannello principale del gioco
     */
    public MyKey(MyPanel m) {
        this.m = m;
        isA = false;
        isD = false;
    }

    /**
     * @brief gestione pressione tasti
     *
     * spazio avvia il gioco
     * A e D muovono la nave a sinistra e destra
     *
     * @param e evento della tastiera
     */
    @Override
    public void keyPressed(KeyEvent e) {

        // movimento verso destra
        if (e.getKeyCode() == KeyEvent.VK_D) {
            m.isPressed = true;
            m.movimento =((m.getWidth() + 16) / 400 * 6);
            isD = true;
        }

        // movimento verso sinistra
        if (e.getKeyCode() == KeyEvent.VK_A) {
            m.isPressed =true;
            m.movimento =-((m.getWidth() + 16)/ 400 * 6);
            isA = true;
        }
    }

    /**
     * @brief gestione rilascio tasti
     *
     * quando A e D non sono premuti
     * la nave si ferma
     *
     * @param e evento della tastiera
     */
    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_D)
            isD = false;

        if (e.getKeyCode() == KeyEvent.VK_A)
            isA = false;

        if (!isA &&!isD)
            m.isPressed =false;
    }

    /**
     * @brief metodo non utilizzato
     *
     * richiesto dall'interfaccia KeyListener
     *
     * @param e evento della tastiera
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // non usato
    }
}
