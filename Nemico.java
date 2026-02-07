
/**
* @author  mohammad Shayan Attari Bin Mohammad Zeshan Attari
* @version 1.0
* @file Nemico.java  
*
* @brief Classe per rappresentare un nemico nel gioco star destroyer.
*
* la classe estende pianeti e cambia le variabili
e riscrive la stampaOgetti e il run della classe thread
**/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

/**
 * @class Nemico
 *
 * @brief Rappresenta un nemico singolo nel gioco.
 *
 *        Ogni nemico ha una posizione, dimensione e immagine. Viene gestito
 *        insieme agli altri
 *        nemici dal pannello di gioco per disegnarlo e spostarlo.
 */
public class Nemico extends Pianeti {
    static int frequenzaAggiuntaNemicoMinima = 1000;
    static int frequenzaAggiuntaNemicoMassima = 5000;
    int dardiNecessariPerMorte;
    int dardiMaxUccisione;
    public BufferedImage image;
    int velocitaX;
    int velocitaMassima;
    boolean isMovimento;
    volatile boolean isVivo;
    ArrayList<Bullets> bullet = new ArrayList<>();
    Long timerSpawn;
    int frequenzaSpawn;
    int passo;
    static volatile boolean isScudoOn = false;
<<<<<<< HEAD
    static int frameScudo = 0;
=======
    public volatile boolean running = true;
>>>>>>> ff524d0c5687cdd3f2e81d36f207193db8ed269c

    public Nemico(int x, int y, int velocita, MyPanel m, ArrayList<BufferedImage> images) {
        super(x, y, velocita, m, images);
        super.grandezzaMassima = 100;
        super.grandezzaMinima = 50;
        grandezzaPianeta = m.r.nextInt(grandezzaMinima, grandezzaMassima);
        super.y = 0 - grandezzaPianeta;
        velocitaMinima = 10;
        super.velocitaMassima = 50;
        super.maxFrame = 1;
        dardiNecessariPerMorte = m.r.nextInt(6, 20);
        dardiMaxUccisione = dardiNecessariPerMorte;
        this.image = images.get(m.r.nextInt(0, images.size()));
        isVivo = true;
        velocitaMassima = 1;
        velocitaX = 0;
        frequenzaSpawn = 2000;
        timerSpawn = System.currentTimeMillis() + frequenzaSpawn;
        passo = 10;
    }

    @Override
    public void stampaOggettiClasse(Graphics g) {
        g.drawImage(image, x, y, grandezzaPianeta, grandezzaPianeta, null);
        if (bullet.size() > 0) {
            Graphics2D g2 = (Graphics2D) g;
            AffineTransform old = g2.getTransform();
            for (int i = 0; i < bullet.size(); i++) {
                g2.rotate(Math.toRadians(180), bullet.get(i).x + 10, bullet.get(i).y + 10);
                g2.drawImage(bullet.get(i).image, bullet.get(i).x, bullet.get(i).y, 20, 60, null);
                g2.setTransform(old);
                // g.setColor(Color.red);
                // g.drawRect(bullet.get(i).hitBox.x, bullet.get(i).hitBox.y,
                // bullet.get(i).hitBox.width, bullet.get(i).hitBox.height);
            }
        }
        if (isScudoOn) {
            g.setColor(Color.RED);
            stampaOggettiClasse(g, m.immagineScudo, m.hitBoxScudo.x - 32, m.hitBoxScudo.y - 32, 31, 176,192);
            //g.drawRect(m.hitboxNave.x, m.hitboxNave.y, m.hitboxNave.width, m.hitboxNave.height);
        }
    }

    @Override
    public void run() {
        while (y <= super.m.getHeight() - super.grandezzaPianeta && isVivo && running == true) {
            if (m.isPaused) {
                try {
                    Thread.sleep(33); // blocca loop ma non CPU
                } catch (InterruptedException e) {
                }
                continue;
            }
            y += velocita;
            int sinistro = 0, destro = 0;
            synchronized (m.bullets) {
                for (int i = 0; i < m.bullets.size(); i++) {
                    if (m.bullets.get(i).x > this.x && m.bullets.get(i).x < this.x + this.grandezzaPianeta / 2) {
                        destro++;
                    } else if (m.bullets.get(i).x > this.x + this.grandezzaPianeta / 2
                            && m.bullets.get(i).x < this.x + this.grandezzaPianeta) {
                        sinistro++;
                    }
                }
            }
            if (sinistro == 0 && destro == 0) {
                isMovimento = true;
            }
            if (isMovimento) {
                if (destro > sinistro) {
                    velocitaX = Math.abs(velocitaMassima);
                    isMovimento = false;
                } else if (sinistro > destro) {
                    velocitaX = -Math.abs(velocitaMassima);
                    isMovimento = false;
                } else {
                    velocitaX = 0;
                }
            }
            if (x + velocitaMassima + grandezzaPianeta > m.getWidth() || x - velocitaMassima < 0) {
                if (x + velocitaMassima + grandezzaPianeta > m.getWidth()) {
                    velocitaX = -Math.abs(velocitaMassima);
                } else {
                    velocitaX = Math.abs(velocitaMassima);
                }
                isMovimento = false;
            }
            x += velocitaX;

            if (timerSpawn < System.currentTimeMillis()) {
                bullet.add(new Bullets(m, x + grandezzaPianeta / 2 - 10, y + grandezzaPianeta / 2 + 30, 8));
                timerSpawn = System.currentTimeMillis() + frequenzaSpawn;
            }
            synchronized (bullet) {
                for (int i = 0; i < bullet.size(); i++) {
                    if (bullet.get(i).y + passo < m.getHeight()) {
                        bullet.get(i).y += passo;
                        bullet.get(i).hitBox.y += passo;
                    } else {
                        bullet.remove(i);
                        i--;
                        continue;
                    }
<<<<<<< HEAD
                    if (bullet.get(i).hitBox.intersects(m.hitboxNave)) {
                        System.out.println("sei stato colpito");
                        m.cl.show(m.contenitore, "GAMEOVER");
=======
                    if (m.gameOver)
                        break; // Se gioco finito, esci subito dal ciclo
                    if (bullet.get(i).hitBox.intersects(m.hitboxNave) && !m.gameOver) {
>>>>>>> ff524d0c5687cdd3f2e81d36f207193db8ed269c
                        m.gameOver = true;
                        GUI.scriviPunteggio();
                        SwingUtilities.invokeLater(() -> m.cl.show(m.contenitore, "GAMEOVER"));
                    }
                }
            }
            synchronized (m.bullets) {
                for (int i = 0; i < m.bullets.size(); i++) {
                    {
                        for (int j = 0; j < bullet.size(); j++) {
                            if (m.bullets.get(i).hitBox.intersects(bullet.get(j).hitBox)) {
                                m.esplosioni.add(new Esplosioni(bullet.get(j).x - 50, bullet.get(j).y - 50, 0));
                                m.bullets.remove(i);
                                bullet.remove(j);
                                j--;
                                i--;
                                continue;
                            }
                        }
                    }
                }
            }
            if (isScudoOn) {
                synchronized (bullet) {
                    for (int i = 0; i < bullet.size(); i++) {
                        if (bullet.get(i).hitBox.intersects(m.hitBoxScudo)) {
                            bullet.remove(i);
                            i--;
                        }
                    }
                }
            }
            try {
                sleep(33); // quasi 30 fps
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Rimuove il nemico dalla lista
        synchronized (m.nemici) {
            m.nemici.remove(this);
        }

        // Se il nemico arriva in fondo vivo, game over
        if (isVivo) {
<<<<<<< HEAD
            System.out.println("la nave ha superato il limite");
=======
>>>>>>> ff524d0c5687cdd3f2e81d36f207193db8ed269c
            m.cl.show(m.contenitore, "GAMEOVER");
            m.gameOver = true;
            GUI.scriviPunteggio();
        }
    }
        public static void stampaOggettiClasse(Graphics g,ArrayList<BufferedImage> image,int x,int y,int maxFrame,int width,int height) {
        g.drawImage(image.get(Nemico.frameScudo), x, y, width, height, null);
            Nemico.frameScudo++;
            if (Nemico.frameScudo > maxFrame)
                Nemico.frameScudo = 0;
    }
}
