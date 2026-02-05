
/**
 * @file SpostaBullet.java
 *
 * @author Mohammad Shayan Attari Bin Mohammad Zeshan Attari
 * @version 1.0
 *
 * @brief Thread che gestisce il movimento dei proiettili
 *
 * La classe si occupa di aggiornare la posizione dei proiettili,
 * controllare le collisioni con i nemici e gestire le esplosioni.
 *
 * La classe collabora con MyPanel, Bullets e Nemico.
 */
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

class SpostaBullet extends Thread {

    /**
     * @brief pannello principale del gioco
     *
     *        Serve per accedere alle liste di proiettili, nemici,
     *        esplosioni e punteggio.
     */
    MyPanel m;

    /**
     * @brief costruttore del thread SpostaBullet
     *
     *        Inizializza il riferimento al pannello di gioco.
     *
     * @param m pannello principale del gioco
     */
    SpostaBullet(MyPanel m) {
        this.m = m;
    }

    /**
     * @brief metodo principale del thread
     *
     *        Il metodo run viene eseguito in ciclo continuo.
     *        Sposta i proiettili, controlla le collisioni con i nemici
     *        e aggiorna punteggio, esplosioni e suoni.
     *
     *        Attributi utilizzati/modificati:
     *        - m.bullets
     *        - m.nemici
     *        - MyPanel.score
     */
    @Override
    public void run() {
        while (true && !m.gameOver) {

            // spostamento dei proiettili
            for (int i = 0; i < m.bullets.size(); i++) {
                m.bullets.get(i).sposta();
            }

            synchronized (m.bullets) {
                synchronized (m.nemici) {

                    cicloBullet: for (int i = 0; i < m.bullets.size(); i++) {
                        for (int j = 0; j < m.nemici.size(); j++) {

                            // controllo collisione bullet - nemico
                            if (m.bullets.get(i).x > m.nemici.get(j).x
                                    && m.bullets.get(i).x < (m.nemici.get(j).x + m.nemici.get(j).grandezzaPianeta)
                                    && m.bullets.get(i).y > m.nemici.get(j).y
                                    && m.bullets.get(i).y < (m.nemici.get(j).y + m.nemici.get(j).grandezzaPianeta)) {

                                // nemico distrutto
                                if (m.nemici.get(j).dardiNecessariPerMorte - 1 == 0) {

                                    m.esplosioni1.add(
                                            new Esplosioni1(
                                                    m.bullets.get(i).x - 100,
                                                    m.bullets.get(i).y - 100,
                                                    m.nemici.get(j).velocita));

                                    MyPanel.score += m.nemici.get(j).dardiMaxUccisione;
                                    m.nemici.get(j).isVivo = false;

                                    int cavia = m.r.nextInt(0, m.nPowerUp);
                                    if (m.r.nextInt(0, 100) > 50) {
                                        System.out.println(cavia);
                                        m.powerUps.add(
                                                new PowerUp(
                                                        m.nemici.get(j).x + m.nemici.get(j).grandezzaPianeta / 2,
                                                        m.nemici.get(j).y + m.nemici.get(j).grandezzaPianeta / 2,
                                                        m.nemici.get(j).velocita,
                                                        m,
                                                        m.immaginiPowerUp.get(cavia),
                                                        cavia));
                                    }

                                    m.nemici.remove(m.nemici.get(j));

                                    // suono esplosione
                                    try {
                                        m.audioColpito = AudioSystem.getAudioInputStream(
                                                new File("esplosioneSoundGrande.wav"));
                                        m.colpitoClip.add(AudioSystem.getClip());
                                        m.colpitoClip.getLast().open(m.audioColpito);
                                        m.colpitoClip.getLast().start();
                                    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    // nemico colpito ma non distrutto
                                    m.esplosioni.add(
                                            new Esplosioni(
                                                    m.bullets.get(i).x - 50,
                                                    m.bullets.get(i).y - 50,
                                                    m.nemici.get(j).velocita));

                                    m.nemici.get(j).dardiNecessariPerMorte--;
                                    m.bullets.remove(m.bullets.get(i));
                                    i--;
                                    continue cicloBullet;
                                }
                            }
                        }
                    }
                }
            }

            try {
                sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
