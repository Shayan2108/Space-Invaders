/**
 * @file Esplosioni.java
 *
 * @author Mohammad Shayan Attari Bin Mohammad Zeshan Attari
 * @version 1.0
 *
 * @brief Classe che rappresenta un'esplosione nel gioco.
 *
 * La classe gestisce la posizione dell'esplosione e
 * l'avanzamento dei frame dell'animazione.
 */

public class Esplosioni {

    public int x;
    public int y;
    public int frameInEseguzione;
    public int maxFrame;
    public int avanzamento;

    /**
     * Costruttore della classe Esplosioni.
     *
     * @param x posizione x dell'esplosione
     * @param y posizione y dell'esplosione
     * @param avanzamento velocità di avanzamento dei frame
     */
    public Esplosioni(int x, int y, int avanzamento) {
        this.x = x;
        this.y = y;
        this.avanzamento = avanzamento;
        this.maxFrame = 70;
        this.frameInEseguzione = 0;
    }

    /**
     * Avanza l'animazione dell'esplosione.
     */
    public void aggiorna() {
        frameInEseguzione += avanzamento;
    }

    /**
     * Controlla se l'animazione è terminata.
     *
     * @return true se l'esplosione è finita
     */
    public boolean isFinita() {
        return frameInEseguzione >= maxFrame;
    }
}
