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
        this.image = image;
        frameAttuale = 1;
        grandezzaPianeta = m.r.nextInt(25, 200);
        frequenzaAggiornamento = 500;
        super.y =  0 - grandezzaPianeta;
        timer = System.currentTimeMillis() + frequenzaAggiornamento;
        this.start();
    }
    @Override
    public void run() {
        while (y <= super.m.getHeight()) {
            y += 4;
            try {
                sleep(33);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        synchronized(super.m.pianeti)
        {
            m.pianeti.remove(this);
        }
    }
    public void stampaPianeti(Graphics g)
    {
        g.drawImage(image.get(frameAttuale), x, y,grandezzaPianeta,grandezzaPianeta, null);
        if(System.currentTimeMillis() > timer)
        {
            frameAttuale++;
            timer += frequenzaAggiornamento;
        if(frameAttuale > 4)
            frameAttuale = 0;
        }
    }
    
}
