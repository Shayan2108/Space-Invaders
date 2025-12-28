
/**
* @author  Statella Giuseppe Salvatore, statella.giuseppe01@gmail.com
* @version 0.1
* @file Nemico.java 
*
* @brief Classe per rappresentare un nemico nel gioco Space Invaders.
*
* Contiene le informazioni necessarie per gestire un nemico, come posizione, dimensioni
* e immagine. La logica di movimento e discesa viene gestita dal pannello MyPanel o dal
* thread dedicato SpostaNemici.
*/

import java.awt.image.BufferedImage;

/**
 * @class Nemico
 *
 * @brief Rappresenta un nemico singolo nel gioco.
 *
 *        Ogni nemico ha una posizione, dimensione e immagine. Viene gestito
 *        insieme agli altri
 *        nemici dal pannello di gioco per disegnarlo e spostarlo.
 */
public class Nemico {

    /** Coordinata X del nemico sullo schermo */
    public int x;

    /** Coordinata Y del nemico sullo schermo */
    public int y;

    /** Larghezza del nemico in pixel */
    public int larghezza = 40;

    /** Altezza del nemico in pixel */
    public int altezza = 30;

    /** Immagine associata al nemico per il rendering */
    public BufferedImage image;

    /**
     * @brief Costruisce un nuovo nemico con posizione e immagine specificata.
     *
     *        Inizializza le coordinate e l'immagine del nemico. Questa istanza può
     *        essere poi
     *        gestita dal pannello di gioco o dal thread SpostaNemici per il
     *        movimento e il disegno.
     *
     * @param x     Coordinata X iniziale del nemico.
     * @param y     Coordinata Y iniziale del nemico.
     * @param image Immagine da usare per disegnare il nemico.
     */
    public Nemico(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }
}
