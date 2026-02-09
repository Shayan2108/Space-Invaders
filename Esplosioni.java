/**
 * @file Esplosioni.java
 *
 * @author Mohammad Shayan Attari Bin Mohammad Zeshan Attari
 * @version 1.0
 *
 * @brief Classe che rappresenta un'esplosione nel gioco.
 *
 *        La classe gestisce la posizione dell'esplosione e
 *        l'avanzamento dei frame dell'animazione.
 */

/**
 * @class Esplosioni
 * 
 * @brief Esplosione animata nel gioco.
 * 
 *        Gestisce la posizione x e y dell'esplosione, l'avanzamento
 *        dei frame e determina quando l'animazione è terminata.
 */
public class Esplosioni {

    /** coordinata x dell'esplosione */
    public int x;

    /** coordinata y dell'esplosione */
    public int y;

    /** frame attualmente in esecuzione nell'animazione */
    public int frameInEseguzione;

    /** numero massimo di frame dell'animazione */
    public int maxFrame;

    /** velocità di avanzamento dei frame */
    public int avanzamento;

    /**
     * @brief Costruttore della classe Esplosioni.
     *
     *        Inizializza la posizione e la velocità di avanzamento dei frame.
     *        Imposta il numero massimo di frame a 70 e il frame iniziale a 0.
     *
     * @param x           posizione x dell'esplosione
     * @param y           posizione y dell'esplosione
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
     * @brief Avanza l'animazione dell'esplosione.
     *
     *        Incrementa il frame in esecuzione usando la velocità di avanzamento.
     *        L'animazione progredisce fino a raggiungere il numero massimo di
     *        frame.
     */
    public void aggiorna() {
        frameInEseguzione += avanzamento;
    }

    /**
     * @brief Controlla se l'animazione è terminata.
     *
     * @return true se il frame in esecuzione ha raggiunto o superato il massimo
     */
    public boolean isFinita() {
        return frameInEseguzione >= maxFrame;
    }
}
