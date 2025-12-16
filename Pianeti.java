import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Pianeti extends Stella {
    ArrayList<BufferedImage> image;
    int frameAttuale;
    int grandezzaPianeta;
    long timer;
    int frequenzaAggiornamento;

    public Pianeti(int x, int y, int velocita, MyPanel m, ArrayList<BufferedImage> image) {
        super(x, y, velocita, m);
        grandezzaPianeta = m.r.nextInt(80, 300);
        super.x = m.r.nextInt(0, 400 - grandezzaPianeta);
        super.y = 0 - grandezzaPianeta;
        this.image = image;
        frameAttuale = 1;
        frequenzaAggiornamento = 66;
        timer = System.currentTimeMillis() + frequenzaAggiornamento;
        this.start();
    }

    @Override
    public void run() {
        while (y <= super.m.getHeight()) {
            y += 1;
            try {
                sleep(33);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        synchronized (super.m.pianeti) {
            m.pianeti.remove(this);
        }
    }

    public void stampaPianeti(Graphics g) {
        g.drawImage(image.get(frameAttuale), x, y, grandezzaPianeta, grandezzaPianeta, null);
        if (System.currentTimeMillis() > timer) {
            frameAttuale++;
            timer += frequenzaAggiornamento;
            if (frameAttuale > 59)
                frameAttuale = 0;
        }
    }

}
