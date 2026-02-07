
/**
 * @file PowerUp.java
 *
 * @author Mohammad Shayan Attari Bin Mohammad Zeshan Attari
 * @version 1.0
 *
 * @brief Classe PowerUp
 *
 * Rappresenta un power-up che scende dall’alto quando si distrugge una nave nemica.
 * Quando viene preso, attiva un effetto temporaneo.
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PowerUp extends Pianeti {

    int tipo;
    Rectangle hitbox;

    volatile boolean effettoFinito;
    volatile boolean effettoIniziato;
    boolean iniziatoUnaVolta;
    volatile boolean finireThread;
    volatile boolean isDisegnare;
    volatile boolean isTimerFinito;

    Long timerPowerUp;

    public volatile boolean running = true;

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

    @Override
    public void run() {

        while ((y <= m.getHeight() || iniziatoUnaVolta)
                && !finireThread && running == true && !m.gameOver) {

            if (m.isPaused) {
                try {
                    Thread.sleep(33); // blocca loop ma non CPU
                } catch (InterruptedException e) {
                }
                continue;
            }
            y += velocita;
            hitbox.translate(0, velocita);

            try {
                sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (effettoIniziato) {

                if (tipo == 1) {
                    m.bulletMassime += 16;
                    timerPowerUp = System.currentTimeMillis() + 10000;
                }
                if (tipo == 0) {
                    Nemico.isScudoOn = true;
                    timerPowerUp = System.currentTimeMillis() + 10000;
                }

                effettoIniziato = false;
                iniziatoUnaVolta = true;
            }
            if (timerPowerUp != null &&
                    timerPowerUp < System.currentTimeMillis()) {
                if (tipo == 1 && !isTimerFinito) {
                    m.bulletMassime -= 16;
                }
                isTimerFinito = true;
                if (tipo == 0) {
                    int tuttiFiniti = 0;
                    boolean finitoPerTutti = true;
                    finireThread = true;
                    synchronized (m.powerUps) {
                        for (int i = 0; i < m.powerUps.size(); i++) {
                            if (m.powerUps.get(i).tipo == 0 && m.powerUps.get(i).iniziatoUnaVolta) {
                                tuttiFiniti++;
                                System.out.println("spengo tutti falsei");
                            }
                            if (!m.powerUps.get(i).isTimerFinito && m.powerUps.get(i).tipo == 0
                                    && m.powerUps.get(i).iniziatoUnaVolta) {
                                finitoPerTutti = false;
                                System.out.println("uno manca ancora");
                            }
                        }
                    }
                    if (tuttiFiniti == 1 && finitoPerTutti) {
                        Nemico.isScudoOn = false;
                    }
                    tuttiFiniti = 0;
                }
            }
        }

        synchronized (m.powerUps) {
            m.powerUps.remove(this);
        }
    }

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
