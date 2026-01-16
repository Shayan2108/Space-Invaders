
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
 * @class Nemico
 *
 * @brief Rappresenta un nemico singolo nel gioco.
 *
 *        Ogni nemico ha una posizione, dimensione e immagine. Viene gestito
 *        insieme agli altri
 *        nemici dal pannello di gioco per disegnarlo e spostarlo.
 */
public class Nemico extends Pianeti {

    static int frequenzaAggiuntaNemicoMinima = 1000;
    static int frequenzaAggiuntaNemicoMassima = 5000;
    int dardiNecessariPerMorte;
    public BufferedImage image;
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
