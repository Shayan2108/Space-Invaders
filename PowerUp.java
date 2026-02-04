import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PowerUp extends Pianeti {

    public enum Tipo {
        MULTI, SCUDO, RESPINGI, RALLENTA, PENETRANTE
    }
    public PowerUp(int x, int y, int velocita, MyPanel m, ArrayList<BufferedImage> image) {
        super(x, y, velocita, m, image);
        maxFrame = 60;
        //TODO Auto-generated constructor stub
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
    }

    public void stampaOggettiClasse(Graphics g) {
        g.drawImage(image.get(frameAttuale), x, y, 50, 50, null); // dimensione standard
            if (System.currentTimeMillis() >= timer) {
            frameAttuale++;
            timer += frequenzaAggiornamento;
            if (frameAttuale > maxFrame)
                frameAttuale = 0;
        }
    }
}