import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Dettagli extends Stella{

    int frequenzaAggiornamento;
    int grandezzaDettaglio;
    BufferedImage image;
    public Dettagli(int x, int y, int velocita, MyPanel m,BufferedImage image) {
        super(x, y, velocita, m);
        super.y = -100;
        this.image = image;
        frequenzaAggiornamento = 10;
        super.velocita = 7;
        grandezzaDettaglio = 50;
        this.start();
    }
    @Override
    public void run() {
            while (y <= super.m.getHeight()) {
            y += velocita;
            try {
                sleep(33);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        synchronized (super.m.dettagli) {
            m.dettagli.remove(this);
        }
    }
    public void stampaDettagli(Graphics g)
    {
        for (int i = 0; i < m.dettagli.size(); i++) {
            g.drawImage(image, x, y,grandezzaDettaglio,grandezzaDettaglio, null);
            
        }
    }
}
