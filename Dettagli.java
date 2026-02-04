
/**
 * @file Dettagli.java
 *
 * @author mohammad Shayan Attari Bin mohammad Zeshan Attari
 * @version 1.0
 *
 * @brief Classe che gestisce i dettagli grafici in movimento
 *
 * la classe estende stelle e è una classe che gestisce i vari oggetti che scendono
 */
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Dettagli extends OggettiScendenti {

    /** frequenza di aggiornamento del movimento */
    int frequenzaAggiornamento;

    /** grandezza del dettaglio disegnato */
    int grandezzaDettaglio;

    /** immagine del dettaglio */
    BufferedImage image;

    /**
     * @brief costruttore della classe Dettagli
     *
     *        Inizializza il dettaglio impostando posizione iniziale,
     *        velocità, dimensione e immagine.
     *        Il thread viene avviato automaticamente.
     *
     *        Attributi modificati:
     *        - x
     *        - y
     *        - velocita
     *        - image
     *
     * @param x        posizione iniziale orizzontale
     * @param y        posizione iniziale verticale che è sempre 0
     * @param velocita velocità di movimento
     * @param m        pannello principale del gioco
     * @param image    immagine del dettaglio
     */
    public Dettagli(int x, int y, int velocita, MyPanel m, BufferedImage image) {
        super(x, y, velocita, m);
        super.y = -100; // per evitare che ci sia una improvvisa apparizione
        this.image = image;
        frequenzaAggiornamento = 10;
        super.velocita = 5;
        grandezzaDettaglio = 50;
        this.start();
    }

    /**
     * @brief metodo principale del thread ovvero run( )
     *
     *        Muove il dettaglio verso il basso fino a quando
     *        esce dallo schermo. Quando termina viene rimosso
     *        dalla lista dei dettagli.
     *
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
        synchronized (super.m.dettagli) {
            m.dettagli.remove(this);
        }
    }

    /**
     * @brief disegna il dettaglio sullo schermo
     *
     *        Disegna l'immagine del dettaglio usando la posizione
     *        e la dimensione impostata.
     *        metodo chiamato dal MyPanel
     *
     * @param g oggetto Graphics usato per il disegno
     */
    public void stampaDettagli(Graphics g) {
        for (int i = 0; i < m.dettagli.size(); i++) {
            g.drawImage(image, x, y, grandezzaDettaglio, grandezzaDettaglio, null);
        }
    }
}
