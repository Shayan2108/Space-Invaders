/**
* @author  Statella Giuseppe Salvatore
* @version 0.1
* @file Explosion.java 
* 
* @brief Classe per gestire le esplosioni nel gioco.
*
*        Rappresenta un'esplosione visiva sullo schermo, gestita tramite
*        una sequenza di immagini (frame). Ogni frame ha una durata specifica e la
*        classe si occupa di aggiornare l'animazione e disegnarla sul pannello.
*/

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
* @class Explosion
*
* @brief Gestisce l'animazione di un'esplosione.
*
*        Aggiorna i frame dell'esplosione in base al tempo e ne gestisce
*        la visualizzazione fino al termine dell'animazione.
*/
public class Explosion {

    /** Posizione orizzontale dell'esplosione */
    int x;

    /** Posizione verticale dell'esplosione */
    int y;

    /** Array di immagini che compongono l'animazione dell'esplosione */
    BufferedImage[] frames;

    /** Indice dell'immagine corrente nell'animazione */
    int frame = 0;

    /** Durata di ogni frame in millisecondi */
    int durataFrame = 50;

    /** Timestamp dell'ultimo aggiornamento del frame */
    long ultimoAggiornamento;

    /** Stato dell'esplosione, true se l'animazione è terminata */
    boolean finita = false;

    /**
     * @brief Costruttore dell'esplosione
     * 
     * Inizializza la posizione e i frame dell'animazione.
     * Imposta il tempo dell'ultimo aggiornamento all'istante corrente.
     * 
     * @param x Posizione orizzontale dell'esplosione
     * @param y Posizione verticale dell'esplosione
     * @param frames Array di immagini che compongono l'animazione
     */
    public Explosion(int x, int y, BufferedImage[] frames) {
        this.x = x;
        this.y = y;
        this.frames = frames;
        this.ultimoAggiornamento = System.currentTimeMillis();
    }

    /**
     * @brief Aggiorna lo stato dell'esplosione
     * 
     * Incrementa il frame corrente se è trascorso il tempo di durata.
     * Se l'ultimo frame è stato mostrato, imposta l'esplosione come finita.
     */
    public void aggiorna() {
        if (finita) return;
        long now = System.currentTimeMillis();
        if (now - ultimoAggiornamento > durataFrame) {
            frame++;
            ultimoAggiornamento = now;
            if (frame >= frames.length) {
                finita = true;
            }
        }
    }

    /**
     * @brief Disegna l'esplosione sul pannello
     * 
     * Mostra l'immagine corrente dell'esplosione se l'animazione non è terminata.
     * Le dimensioni dell'immagine disegnata sono adattabili.
     * 
     * @param g Oggetto Graphics usato per il rendering
     */
    public void disegna(Graphics g) {
        if (!finita && frames.length > 0) {
            g.drawImage(frames[frame], x, y, 40, 40, null);
        }
    }
}
