import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PowerUp extends Pianeti {
    int tipo;
    Rectangle hitbox;
    public PowerUp(int x, int y, int velocita, MyPanel m, ArrayList<BufferedImage> image,int tipo) {
        super(x, y, velocita, m, image);
        super.x = x;
        super.y = y;
        super.velocita = 5;
        maxFrame = 60;
        this.tipo = tipo;
        hitbox = new Rectangle(x, y, 25, 25);
    }

    @Override
    public void run() {
        while (y <= m.getHeight()) {
            y += velocita;
            hitbox.translate(0, velocita);
            try {
                sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized(m.powerUps)
        {
            m.powerUps.remove(this);
        }
    }

    public void stampaOggettiClasse(Graphics g) {
        g.drawImage(image.get(frameAttuale), x, y, 25, 25, null); // dimensione standard
            if (System.currentTimeMillis() >= timer) {
            frameAttuale++;
            timer += frequenzaAggiornamento;
            if (frameAttuale >= maxFrame)
                frameAttuale = 0;
        }
    }
}