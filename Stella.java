/**
 * @file Stella.java
 *
 * @author Mohammad Shayan Attari Bin Mohammad Zeshan Attari
 * @version 1.0
 *
 * @brief Gestisce una stella di sfondo del gioco
 *
 * Questo file contiene la classe Stella che rappresenta
 * un punto bianco che si muove verso il basso nello schermo.
 */
public class Stella extends Thread {

    /**
     * @brief coordinata x della stella
     */
    public int x;

    /**
     * @brief coordinata y della stella
     */
    public int y;

    /**
     * @brief velocità di movimento della stella
     */
    public int velocita;

    /**
     * @brief riferimento al pannello di gioco
     *
     * Serve per controllare i limiti dello schermo.
     */
    public MyPanel m;

    /**
     * @brief costruttore della classe Stella
     *
     * Inizializza posizione, velocità e pannello di gioco.
     *
     * @param x coordinata iniziale x
     * @param y coordinata iniziale y
     * @param velocita velocità di movimento
     * @param m pannello principale del gioco
     */
    public Stella(int x, int y, int velocita, MyPanel m) {
        this.x = x;
        this.y = y;
        this.velocita = velocita;
        this.m = m;
    }

    /**
     * @brief sposta la stella verso il basso
     *
     * Aumenta la coordinata y usando la velocità.
     */
    public void sposta() {
        y += velocita;
    }

    /**
     * @brief controlla se la stella è uscita dallo schermo
     *
     * Verifica se la coordinata y supera l'altezza del pannello.
     *
     * @return true se la stella è fuori dallo schermo
     */
    public boolean isFinita() {
        if (y > m.getHeight() + 10)
            return true;
        return false;
    }
}
