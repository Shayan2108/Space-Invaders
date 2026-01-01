/**
 * @author Statella Giuseppe Salvatore
 * @version 0.2
 * @file SpostaNemici.java
 * 
 * @brief Thread per il movimento dei nemici nel gioco Space Invaders.
 *
 *        Muove i nemici orizzontalmente, li fa scendere ai bordi dello schermo
 *        e aumenta la velocità man mano che i nemici diminuiscono.
 *        Controlla il game over se un nemico arriva alla nave.
 */
public class SpostaNemici extends Thread {

    /** Riferimento al pannello principale */
    MyPanel m;

    /** numero totale iniziale di nemici */
    int nemiciIniziali;

    /** velocità minima e massima dei nemici */
    int velocitaMin = 2;
    int velocitaMax = 10;

    public SpostaNemici(MyPanel m) {
        this.m = m;
        this.nemiciIniziali = m.nemici.size();
    }

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

                // Calcola velocità proporzionale
                int numNemici = m.nemici.size();
                int velocitaAttuale = velocitaMin;
                if (numNemici > 0) {
                    velocitaAttuale = velocitaMin
                            + ((nemiciIniziali - numNemici) * (velocitaMax - velocitaMin)) / nemiciIniziali;
                }

                if (cambioDirezione) {
                    m.dirNemici *= -1;
                    for (Nemico n : m.nemici) {
                        n.y += m.passoDiscesaNemici;

                        // Game over se arriva alla nave
                        if (n.y + n.altezza >= m.yNave) {
                            m.gameOver = true;
                        }
                    }
                } else {
                    // Muovi i nemici con velocità proporzionale
                    for (Nemico n : m.nemici) {
                        n.x += velocitaAttuale * m.dirNemici;
                    }
                }

                // Controlla vittoria
                if (m.nemici.isEmpty()) {
                    m.gameOver = true; // segna gameOver anche se il giocatore vince
                }
            }

            try {
                sleep(50); // aggiorna ogni 50ms per movimento fluido
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            m.repaint();
        }
    }
}
