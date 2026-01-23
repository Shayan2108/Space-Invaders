/**
 * @file Pianeti.java
 *
 * @author Mohammad Shayan Attari Bin Mohammad Zeshan Attari
 * @version 1.0
 *
 * @brief Gestione dei pianeti nello sfondo
 *
 * Questa classe rappresenta un pianeta che scende dall'alto verso il basso
 * con una animazione a frame. Serve solo per la grafica dello sfondo.
 */
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Pianeti extends OggettiScendenti {

    /** lista di immagini per l'animazione del pianeta */
    ArrayList<BufferedImage> image;

    /** frame attualmente mostrato */
    int frameAttuale;

    /** dimensione del pianeta */
    int grandezzaPianeta;

    /** timer per il cambio frame */
    long timer;

    /** tempo tra un frame e l'altro */
    int frequenzaAggiornamento;

    /** numero massimo di frame */
    int maxFrame;
    /**velocita massima e minima per i pianeti */
    int velocitaMinima;
    int velocitaMassima;
    /**valore massimi in pixel della grandezza massima del pianeta e minima */
    int grandezzaMinima;
    int grandezzaMassima;

    /**
     * @brief costruttore della classe Pianeti
     *
     * Inizializza il pianeta con velocità, posizione casuale,
     * dimensione e immagini per l'animazione.
     * Avvia anche il thread del pianeta.
     *
     * @param x posizione iniziale x
     * @param y posizione iniziale y
     * @param velocita velocità di discesa
     * @param m pannello principale del gioco
     * @param image lista di immagini del pianeta
     */
    public Pianeti(int x, int y, int velocita, MyPanel m, ArrayList<BufferedImage> image) {
        super(x, y, velocita, m);
        velocitaMassima = 5;
        velocitaMinima = 1;
        super.velocita = m.r.nextInt(velocitaMinima, velocitaMassima);
        grandezzaMassima = 200;
        grandezzaMinima = 80;
        grandezzaPianeta = m.r.nextInt(grandezzaMinima, grandezzaMassima);
        super.x = m.r.nextInt(0, 400 - grandezzaPianeta);
        super.y = 0 - grandezzaPianeta;
        this.image = image;
        frameAttuale = 1;
        frequenzaAggiornamento = 66;
        maxFrame = 59;
        timer = System.currentTimeMillis() + frequenzaAggiornamento;
        this.start();
    }

    /**
     * @brief movimento del pianeta
     *
     * Fa scendere il pianeta verso il basso finché non esce
     * dallo schermo. Quando esce viene rimosso dalla lista.
     *
     * Attributi utilizzati:
     * - y
     * - velocita
     */
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
        synchronized (super.m.pianeti) {
            m.pianeti.remove(this);
        }
    }

    /**
     * @brief disegna il pianeta
     *
     * Disegna il pianeta sullo schermo e aggiorna il frame
     * dell'animazione in base al timer.
     *
     * @param g oggetto Graphics per il disegno
     */
    public void stampaOggettiClasse(Graphics g) {
        g.drawImage(image.get(frameAttuale), x, y, grandezzaPianeta, grandezzaPianeta, null);
        if (System.currentTimeMillis() >= timer) {
            frameAttuale++;
            timer += frequenzaAggiornamento;
            if (frameAttuale > maxFrame)
                frameAttuale = 0;
        }
    }
}
