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

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
<<<<<<< HEAD
* @class Nemico
*
* @brief Rappresenta un nemico singolo nel gioco.
*
*        Ogni nemico ha una posizione, dimensione e immagine. Viene gestito
*        insieme agli altri nemici dal pannello di gioco per disegnarlo e spostarlo.
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
     *        essere poi gestita dal pannello di gioco o dal thread SpostaNemici
     *        per il movimento e il disegno.
     *
     * @param x     Coordinata X iniziale del nemico.
     * @param y     Coordinata Y iniziale del nemico.
     * @param image Immagine da usare per disegnare il nemico.
     */
    public Nemico(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.image = image;
=======
    public Nemico(int x, int y, int velocita, MyPanel m, ArrayList<BufferedImage> images) {
        super(x, y, velocita, m, images);
        super.grandezzaMassima = 100;
        super.grandezzaMinima = 50;
        grandezzaPianeta = m.r.nextInt(grandezzaMinima, grandezzaMassima);
        super.y = 0 - grandezzaPianeta;
        velocitaMinima = 10;
        super.velocitaMassima = 50;
        super.maxFrame = 1;
        dardiNecessariPerMorte = m.r.nextInt(6, 20);
        this.image = images.get(m.r.nextInt(0,images.size()));
>>>>>>> aa294413387b792408c3e150f1d72aa90777e77e
    }
    @Override
    public void stampaOggettiClasse(Graphics g) {
        g.drawImage(image, x, y, grandezzaPianeta, grandezzaPianeta, null);
    }
    @Override
    public void run() {
                while (y <= super.m.getHeight()) {
            y += velocita;
            try {
                sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (super.m.nemici) {
            m.nemici.remove(this);
        }
    }





}
