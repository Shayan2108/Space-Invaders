
 /**
* @author  mohammad Shayan Attari Bin Mohammad Zeshan Attari
* @version 1.0
* @file Nemico.java  
 *
* @brief Classe per rappresentare un nemico nel gioco star destroyer.
 *
* la classe estende pianeti e cambia le variabili
e riscrive la stampaOgetti e il run della classe thread
**/

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
    int dardiMaxUccisione;
     public BufferedImage image;
    volatile boolean isVivo;

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
        dardiMaxUccisione = dardiNecessariPerMorte;
        this.image = images.get(m.r.nextInt(0, images.size()));
        isVivo = true;
    }

    @Override
    public void stampaOggettiClasse(Graphics g) {
        g.drawImage(image, x, y, grandezzaPianeta, grandezzaPianeta, null);
    }

    @Override
    public void run() {
        while (y <= super.m.getHeight() - super.grandezzaPianeta && isVivo) {
            y += velocita;

            // Se è un nemico intelligente, segue la nave
            // if (intelligente) {
            //     int centroNave = m.xNave + m.larghezzaNave / 2;
            //     int centroNemico = x + grandezzaPianeta / 2;

            //     // Movimento proporzionale alla distanza (sempre fluido)
            //     int delta = centroNave - centroNemico;

            //     // Aggiorna x con una piccola frazione di delta per un movimento lento e
            //     // costante
            //     x += delta / 10;

            //     // Limiti dello schermo
            //     if (x < 0)
            //         x = 0;
            //     if (x + grandezzaPianeta > m.getWidth())
            //         x = m.getWidth() - grandezzaPianeta;
            // }

            try {
                sleep(33); // quasi 30 fps
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Rimuove il nemico dalla lista
        synchronized (m.nemici) {
            m.nemici.remove(this);
        }

        // Se il nemico arriva in fondo vivo, game over
        if (isVivo) {
            m.cl.show(m.contenitore, "GAMEOVER");
            m.gameOver = true;
            GUI.scriviPunteggio();
        }
        }

}
