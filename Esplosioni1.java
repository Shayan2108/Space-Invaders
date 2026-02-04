/**
 * @file Esplosioni1.java
 *
 * @author Mohammad Shayan Attari Bin Mohammad Zeshan Attari
 * @version 1.0
 *
 * @brief Classe per rappresentare un'esplosione grande.
 *
 * La classe estende Esplosioni e ridefinisce
 * il numero massimo di frame dell'animazione.
 */

public class Esplosioni1 extends Esplosioni {

    /**
     * Costruttore della classe Esplosioni1.
     *
     * @param x posizione x dell'esplosione
     * @param y posizione y dell'esplosione
     * @param avanzamento velocità di avanzamento dei frame
     */
    public Esplosioni1(int x, int y, int avanzamento) {
        super(x, y, avanzamento);
        this.maxFrame = 16;
    }

}
