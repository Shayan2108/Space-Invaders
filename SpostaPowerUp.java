import java.awt.Rectangle;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SpostaPowerUp extends Thread {

    MyPanel m;

    public SpostaPowerUp(MyPanel m) {
        this.m = m;
        setName("PowerUpThread");
    }

    @Override
    public void run() {
        while (!m.gameOver) {

            synchronized (m.powerUps) {
                for (int i = 0; i < m.powerUps.size(); i++) {
                    PowerUp p = m.powerUps.get(i);
                    p.sposta();

                    Rectangle nave = new Rectangle(
                            m.xNave, m.yNave,
                            m.larghezzaNave, m.altezzaNave);

                    Rectangle box = new Rectangle(p.x, p.y, 40, 40);

                    if (nave.intersects(box)) {
                        applicaPowerUp(p);
                        m.powerUps.remove(i);
                        i--;
                        continue;
                    }

                    if (p.y > m.getHeight()) {
                        m.powerUps.remove(i);
                        i--;
                    }
                }
            }

            gestisciTimer();

            try {
                sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void applicaPowerUp(PowerUp p) {
        switch (p.tipo) {

            case MULTI -> {
                m.sparoMultiplo = true;
                m.timerMulti = System.currentTimeMillis() + 5000;
            }

            case SCUDO -> {
                m.scudoAttivo = true;
                m.timerScudo = System.currentTimeMillis() + 5000;
            }

            case PENETRANTE -> {
                m.proiettiliPenetranti = true;
                m.timerPenetrante = System.currentTimeMillis() + 5000;
            }

            case RALLENTA -> {
                m.velocitaNemici = m.velocitaNemiciBase / 2;
                m.timerRallenta = System.currentTimeMillis() + 5000;
            }

            case RESPINGI -> {
                for (Nemico n : m.nemici) {
                    n.y -= 50; // li spinge verso l'alto
                    if (n.y < 0)
                        n.y = 0;
                }
            }
        }
        /*
         * try {
         * AudioInputStream audio = AudioSystem.getAudioInputStream(new
         * File("powerup.wav"));
         * Clip clip = AudioSystem.getClip();
         * clip.open(audio);
         * clip.start();
         * } catch (Exception e) {
         * e.printStackTrace();
         * }
         */

    }

    private void gestisciTimer() {
        long now = System.currentTimeMillis();

        if (m.sparoMultiplo && now > m.timerMulti) {
            m.sparoMultiplo = false;
        }

        if (m.scudoAttivo && now > m.timerScudo) {
            m.scudoAttivo = false;
        }

        if (m.proiettiliPenetranti && now > m.timerPenetrante) {
            m.proiettiliPenetranti = false;
        }

        if (m.timerRallenta > 0 && System.currentTimeMillis() > m.timerRallenta) {
            m.velocitaNemici = m.velocitaNemiciBase;
            m.timerRallenta = 0;
        }
    }
}
