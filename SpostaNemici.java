/**
* @author  Statella Giuseppe Salvatore
* @version 0.2
* @file SpostaNemici.java
*
* @brief Thread per il movimento dei nemici nel gioco Space Invaders.
*
*        Muove i nemici orizzontalmente, li fa scendere ai bordi dello schermo
*        e aumenta la velocità man mano che i nemici diminuiscono.
*        Controlla il game over se un nemico arriva alla nave o se il giocatore
*        distrugge tutti i nemici.
*/

/**
* @class SpostaNemici
*
* @brief Thread per gestire il movimento dei nemici.
*
*        La classe muove i nemici orizzontalmente, li fa scendere quando
*        raggiungono i bordi dello schermo e calcola la velocità proporzionale
*        in base al numero di nemici rimasti. Controlla anche la vittoria
*        o sconfitta del giocatore.
*/
public class SpostaNemici extends Thread {

    /** 
     * @brief Riferimento al pannello principale contenente nemici e variabili di gioco 
     */
    MyPanel m;

    /** 
     * @brief Numero totale iniziale di nemici
     *
     *        Serve per calcolare la velocità proporzionale in base ai nemici rimasti.
     */
    int nemiciIniziali;

    /** @brief Velocità minima dei nemici */
    int velocitaMin = 2;

    /** @brief Velocità massima dei nemici */
    int velocitaMax = 10;

    /**
     * @brief Costruttore del thread SpostaNemici
     *
     * @param m Pannello principale del gioco contenente i nemici
     *
     *        Inizializza il riferimento al pannello principale e calcola
     *        il numero iniziale di nemici per la velocità proporzionale.
     */
    public SpostaNemici(MyPanel m) {
        this.m = m;
        this.nemiciIniziali = m.nemici.size();
    }

    /**
     * @brief Metodo principale del thread
     *
     *        Ciclo infinito che muove i nemici orizzontalmente e li fa scendere
     *        quando raggiungono il bordo dello schermo.
     *        La velocità aumenta proporzionalmente al numero di nemici rimasti.
     *        Controlla la condizione di game over:
     *        - se un nemico arriva alla nave
     *        - se non ci sono più nemici (vittoria del giocatore)
     *
     *        Il metodo chiama repaint() ad ogni iterazione per aggiornare la grafica.
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

                // Calcola velocità proporzionale in base ai nemici rimasti
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

                        // Game over se un nemico arriva alla nave
                        if (n.y + n.altezza >= m.yNave) {
                            m.gameOver = true;
                        }
                    }
                } else {
                    // Muove i nemici con velocità proporzionale
                    for (Nemico n : m.nemici) {
                        n.x += velocitaAttuale * m.dirNemici;
                    }
                }

                // Controlla se il giocatore ha vinto
                if (m.nemici.isEmpty()) {
                    m.gameOver = true; // segna gameOver anche se il giocatore vince
                }
            }

            try {
                sleep(50); ///< pausa di 50ms per movimento fluido
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            m.repaint(); ///< ridisegna il pannello
        }
    }
}
