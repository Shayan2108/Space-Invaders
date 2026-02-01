import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PowerUp extends Thread {
    public enum Tipo {
        MULTI, SCUDO, RESPINGI, RALLENTA, PENETRANTE
    }

    public int x, y;
    public int velocita;
    public MyPanel m;
    public BufferedImage image;
    public Tipo tipo;

    public PowerUp(int x, int y, int velocita, MyPanel m, BufferedImage image, Tipo tipo) {
        this.x = x;
        this.y = y;
        this.velocita = velocita;
        this.m = m;
        this.image = image;
        this.tipo = tipo;
    }

    @Override
    public void run() {
        while (y <= m.getHeight()) {
            y += velocita;
            try {
                sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // synchronized (m.powerUps) {
        //     m.powerUps.remove(this);
        // }
    }

    public void stampaOggettiClasse(Graphics g) {
        g.drawImage(image, x, y, 50, 50, null); // dimensione standard
    }
}