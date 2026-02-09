
/**
 * @file PowerUp.java
 *
 * @author Mohammad Shayan Attari Bin Mohammad Zeshan Attari
 * @version 1.0
 *
 * Classe PowerUp
 *
 * Questa classe rappresenta un power up del gioco.
 * Il power up scende dall’alto verso il basso.
 * Quando il giocatore lo prende, attiva un effetto
 * che dura per un certo tempo.
 *
 * Il power up viene gestito con un thread.
 * Quando il tempo finisce, l’effetto viene tolto
 * e il power up viene eliminato dalla lista.
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PowerUp extends Pianeti {

    // tipo del power up
    int tipo;

    // area di collisione
    Rectangle hitbox;

    // indica se l’effetto è finito
    volatile boolean effettoFinito;

    // indica se l’effetto è iniziato
    volatile boolean effettoIniziato;

    // serve per evitare che l’effetto parta più volte
    boolean iniziatoUnaVolta;

    // indica se il thread deve finire
    volatile boolean finireThread;

    // indica se il power up deve essere disegnato
    volatile boolean isDisegnare;

    // indica se il timer è finito
    volatile boolean isTimerFinito;

    // timer del power up
    Long timerPowerUp;

    /**
     * Costruttore della classe PowerUp
     *
     * @param x        posizione x iniziale
     * @param y        posizione y iniziale
     * @param velocita velocità di discesa
     * @param m        riferimento al pannello di gioco
     * @param image    lista delle immagini
     * @param tipo     tipo del power up
     */
    public PowerUp(int x, int y, int velocita, MyPanel m,
            ArrayList<BufferedImage> image, int tipo) {

        super(x, y, velocita, m, image);

        super.x = x;
        super.y = y;
        super.velocita = 5;

        maxFrame = 60;
        this.tipo = tipo;

        hitbox = new Rectangle(x, y, 25, 25);

        effettoFinito = false;
        effettoIniziato = false;
        finireThread = false;
        iniziatoUnaVolta = false;
        isDisegnare = true;
        isTimerFinito = false;
    }

    /**
     * Metodo run del thread.
     *
     * Fa scendere il power up.
     * Controlla la pausa del gioco.
     * Attiva l’effetto quando viene preso.
     * Controlla quando il timer finisce.
     * Alla fine rimuove il power up dalla lista.
     */
    @Override
    public void run() {

        while ((y <= m.getHeight() || iniziatoUnaVolta)
                && !finireThread && !m.gameOver) {

            // se il gioco è in pausa
            if (m.isPaused) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
                continue;
            }

            // movimento verso il basso
            y += velocita;
            hitbox.translate(0, velocita);

            try {
                sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // attivazione effetto
            if (effettoIniziato) {

                if (tipo == 1) {
                    m.bulletMassime += 16;
                    timerPowerUp = System.currentTimeMillis() + 10000;
                }

                if (tipo == 0) {
                    Nemico.accesiTipo0++;
                    Nemico.isScudoOn = true;
                    timerPowerUp = System.currentTimeMillis() + 10000;
                    Nemico.tempoScudo = System.currentTimeMillis() + 10000;
                }
                if (tipo == 2) {
                    if (MyPanel.cuoreRimanenti + 3 >= 9) {
                        MyPanel.cuoreRimanenti = 9;
                    } else {
                        MyPanel.cuoreRimanenti += 3;
                    }
                }

                effettoIniziato = false;
                iniziatoUnaVolta = true;
            }

            // controllo fine timer
            if (timerPowerUp != null &&
                    timerPowerUp < System.currentTimeMillis()) {

                if (tipo == 1 && !isTimerFinito) {
                    m.bulletMassime -= 16;
                }

                isTimerFinito = true;

                if (tipo == 0) {
                    Nemico.accesiTipo0--;
                    boolean finitoPerTutti = true;
                    finireThread = true;

                    synchronized (m.powerUps) {
                        for (int i = 0; i < m.powerUps.size(); i++) {
                            if (!m.powerUps.get(i).isTimerFinito
                                    && m.powerUps.get(i).tipo == 0
                                    && m.powerUps.get(i).iniziatoUnaVolta) {
                                finitoPerTutti = false;
                            }
                        }
                    }

                    if (Nemico.accesiTipo0 == 0 && finitoPerTutti) {
                        Nemico.isScudoOn = false;
                        Nemico.tempoScudo = null;
                    }
                }
            }
        }

        // rimozione dalla lista dei power up
        synchronized (m.powerUps) {
            m.powerUps.remove(this);
        }
    }

    /**
     * Disegna il power up a schermo
     *
     * @param g oggetto Graphics
     */
    public void stampaOggettiClasse(Graphics g) {

        g.drawImage(
                image.get(frameAttuale),
                x, y, 25, 25,
                null);

        if (System.currentTimeMillis() >= timer) {
            frameAttuale++;
            timer += frequenzaAggiornamento;

            if (frameAttuale >= maxFrame)
                frameAttuale = 0;
        }
    }
}
