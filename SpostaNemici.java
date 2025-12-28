/**
 * @author Statella Giuseppe Salvatore, statella.giuseppe01@gmail.com
 * @version 0.1
 * @file SpostaNemici.java
 * 
 * @brief Thread per il movimento dei nemici nel gioco Space Invaders.
 *
 *        Gestisce il movimento orizzontale dei nemici e la loro discesa quando
 *        raggiungono
 *        i bordi dello schermo. Controlla inoltre se un nemico raggiunge il
 *        fondo dello
 *        schermo, impostando la variabile gameOver a true.
 *        Il movimento avviene in un thread separato per non bloccare il
 *        rendering del gioco.
 */

public class SpostaNemici extends Thread {

    /** Riferimento al pannello principale contenente nemici e variabili di gioco */
    MyPanel m;

    /**
     * Costruisce il thread per muovere i nemici.
     *
     * Inizializza il riferimento al pannello principale, necessario per accedere
     * all'array di nemici e alle variabili di stato del gioco.
     *
     * @param m Riferimento al pannello MyPanel che contiene i nemici.
     */
    public SpostaNemici(MyPanel m) {
        this.m = m;
    }

    /**
     * Ciclo principale del thread.
     *
     * Controlla costantemente la posizione dei nemici e li muove:
     * - Se un nemico raggiunge il bordo dello schermo, cambia direzione e li fa
     * scendere.
     * - Muove i nemici nella direzione corrente (destra o sinistra).
     * - Controlla se un nemico raggiunge il fondo dello schermo e imposta gameOver
     * a true.
     *
     * Il thread dorme per 1000 millisecondi dopo ogni passo di movimento.
     */
    @Override
    public void run() {
        while (!m.gameOver) {
            synchronized (m.nemici) {
                boolean cambioDirezione = false;

                // Controlla se qualche nemico ha raggiunto il bordo
                for (Nemico n : m.nemici) {
                    if ((n.x + n.larghezza >= m.getWidth() && m.dirNemici == 1) ||
                            (n.x <= 0 && m.dirNemici == -1)) {
                        cambioDirezione = true;
                        break;
                    }
                }

                // Se serve cambia direzione e fai scendere i nemici
                if (cambioDirezione) {
                    m.dirNemici *= -1;
                    for (Nemico n : m.nemici) {
                        n.y += m.passoDiscesaNemici;
                        // Se un nemico arriva in fondo → game over
                        if (n.y + n.altezza >= m.getHeight()) {
                            m.gameOver = true;
                        }
                    }
                } else {
                    // Muovi i nemici nella direzione corrente
                    for (Nemico n : m.nemici) {
                        n.x += m.velocitaNemici * m.dirNemici;
                    }
                }
            }

            try {
                sleep(1000); // un passo ogni secondo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
