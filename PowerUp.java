import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PowerUp extends Pianeti {

    public enum Tipo {
        MULTI, SCUDO, RESPINGI, RALLENTA, PENETRANTE
    }

    public Tipo tipo;

    public PowerUp(int x, int y, int velocita, MyPanel m, Tipo tipo) throws IOException {
        super(x, y, velocita, m, caricaImmagini(tipo));
        this.tipo = tipo;
        this.grandezzaPianeta = 50; // dimensione fissa dei power-up
        this.velocita = velocita;
    }

    // Carica immagini in base al tipo di power-up
    private static ArrayList<BufferedImage> caricaImmagini(Tipo tipo) {
        ArrayList<BufferedImage> imgs = new ArrayList<>();
        String cartella = "PowerUps/";

        switch (tipo) {
            case MULTI -> cartella += "MULTI/";
            case SCUDO -> cartella += "SCUDO/";
            case RESPINGI -> cartella += "RESPINGI/";
            case RALLENTA -> cartella += "RALLENTA/";
            case PENETRANTE -> cartella += "PENETRANTE/";
        }

        for (int i = 0;; i++) {
            try {
                imgs.add(ImageIO.read(new File(cartella + i + ".png")));
            } catch (IOException e) {
                break; // esce quando non ci sono più immagini
            }
        }
        return imgs;
    }

    // Collisione con la nave
    public boolean collideConNave(MyPanel m) {
        Rectangle nave = new Rectangle(m.xNave, m.yNave, m.larghezzaNave, m.altezzaNave);
        Rectangle box = new Rectangle(x, y, grandezzaPianeta, grandezzaPianeta);
        return nave.intersects(box);
    }

    // Applica l'effetto del power-up
    public void applicaPowerUp(MyPanel m) {
        switch (tipo) {
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
                    n.y -= 50;
                    if (n.y < 0)
                        n.y = 0;
                }
            }
        }
    }

    // Disegna il power-up
    @Override
    public void stampaOggettiClasse(Graphics g) {
        super.stampaOggettiClasse(g);
    }
}
