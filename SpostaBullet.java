import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @file SpostaBullet.java
 * 
 * @author Mohammad Shayan Attari Bin Mohammad Zeshan Attari
 * @version 1.0
 * 
 * @brief Thread per aggiornare continuamente la posizione dei proiettili
 *
 *        La classe gestisce il movimento di tutti i proiettili presenti
 *        nel pannello ogni 33ms
 */
class SpostaBullet extends Thread {

    /**
     * pannello principale del gioco, usato per accedere alla lista dei proiettili
     */
    MyPanel m;

    /**
     * @brief costruttore del thread
     * @param m pannello principale del gioco
     */
    SpostaBullet(MyPanel m) {
        this.m = m;
    }

    /**
     * @brief metodo principale del thread Ovvero run()
     *
     *        Per ogni proiettile nella lista, chiama il metodo sposta().
     *        Il thread dorme per 33ms.
     */
    @Override
    public void run() {
        while (!m.gameOver) {
            // Muovo tutti i proiettili
            for (int i = 0; i < m.bullets.size(); i++) {
                m.bullets.get(i).sposta();
            }

            synchronized (m.bullets) {
                synchronized (m.nemici) {
                    cicloBullet: for (int i = 0; i < m.bullets.size(); i++) {
                        Bullets bullet = m.bullets.get(i);

                        for (int j = 0; j < m.nemici.size(); j++) {
                            Nemico nemicoColpito = m.nemici.get(j);

                            // Collisione proiettile-nemico
                            if (bullet.x > nemicoColpito.x &&
                                    bullet.x < nemicoColpito.x + nemicoColpito.grandezzaPianeta &&
                                    bullet.y > nemicoColpito.y &&
                                    bullet.y < nemicoColpito.y + nemicoColpito.grandezzaPianeta) {

                                // Nemico muore
                                if (nemicoColpito.dardiNecessariPerMorte - 1 == 0) {
                                    m.esplosioni1.add(new Esplosioni1(
                                            bullet.x - 100,
                                            bullet.y - 100,
                                            nemicoColpito.velocita));

                                    MyPanel.score += nemicoColpito.dardiMaxUccisione;
                                    nemicoColpito.isVivo = false;
                                    m.nemici.remove(nemicoColpito); // rimuovo il nemico dalla lista

                                    // SPAWN POWER-UP CASUALE
                                    if (m.r.nextInt(0, 100) < 20) {
                                        try {
                                            PowerUp.Tipo[] tipi = PowerUp.Tipo.values();
                                            PowerUp.Tipo tipoCasuale = tipi[m.r.nextInt(tipi.length)];

                                            String percorsoImg = switch (tipoCasuale) {
                                                case MULTI -> "PowerUps/MULTI.png";
                                                case SCUDO -> "PowerUps/SCUDO.png";
                                                case RESPINGI -> "PowerUps/RESPINGI.png";
                                                case RALLENTA -> "PowerUps/RALLENTA.png";
                                                case PENETRANTE -> "PowerUps/PENETRANTI.png";
                                            };

                                            BufferedImage img = ImageIO.read(new File(percorsoImg));

                                            PowerUp p = new PowerUp(
                                                    nemicoColpito.x,
                                                    nemicoColpito.y,
                                                    3,
                                                    m,
                                                    img,
                                                    tipoCasuale);

                                            synchronized (m.powerUps) {
                                                m.powerUps.add(p);
                                            }
                                            p.start();

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    // Suono esplosione grande
                                    try {
                                        m.audioColpito = AudioSystem
                                                .getAudioInputStream(new File("esplosioneSoundGrande.wav"));
                                        m.colpitoClip.add(AudioSystem.getClip());
                                        m.colpitoClip.getLast().open(m.audioColpito);
                                        m.colpitoClip.getLast().start();
                                    } catch (UnsupportedAudioFileException | LineUnavailableException
                                            | IOException e1) {
                                        e1.printStackTrace();
                                    }

                                } else {
                                    // Nemico colpito ma non muore
                                    m.esplosioni.add(new Esplosioni(
                                            bullet.x - 50,
                                            bullet.y - 50,
                                            nemicoColpito.velocita));
                                    nemicoColpito.dardiNecessariPerMorte--;

                                    // Rimuovo il proiettile solo se NON è penetrante
                                    if (!m.proiettiliPenetrantiAttivi) {
                                        m.bullets.remove(bullet);
                                        i--;
                                        continue cicloBullet;
                                    }
                                    // Se è penetrante, il proiettile continua
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
