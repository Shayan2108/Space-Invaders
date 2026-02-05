import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PowerUp extends Pianeti {
    int tipo;
    Rectangle hitbox;
    volatile boolean effettoFinito;
    volatile boolean effettoIniziato;
    boolean iniziatoUnaVolta;
    volatile boolean finireThread;
    Long timerPowerUp;

    public PowerUp(int x, int y, int velocita, MyPanel m, ArrayList<BufferedImage> image, int tipo) {
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
    }

    @Override
    public void run() {
        while ((y <= m.getHeight()|| iniziatoUnaVolta) && !finireThread && !m.gameOver) {
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
                effettoIniziato = false;
                iniziatoUnaVolta = true;
            }
            if(timerPowerUp != null && timerPowerUp < System.currentTimeMillis()) {
                if (tipo == 1) {
                    m.bulletMassime -= 16;
                }
                finireThread = true;
            }
        }
        synchronized (m.powerUps) {
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