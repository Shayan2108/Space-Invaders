import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MyPanel extends JPanel {
    volatile ArrayList<Stella> stelle = new ArrayList<>();
    Random r;
    Sfondo sfondo;
    GameLoop game;
    BufferedImage nave;
    BufferedImage fiammaImg;
    int xNave;
    int yNave;
    int larghezzaNave;
    int altezzaNave;
    int larghezzaFiamma;
    int altezzaFiamma;
    int frameFiamma;
    boolean isPressed;
    int movimento;

    public MyPanel() {
        this.setFocusable(true);
        this.addKeyListener(new MyKey(this));
        this.addMouseListener(new MyMouse(this));
        this.setBackground(Color.black);
        r = new Random();
        game = new GameLoop(this);
        game.start();
        sfondo = new Sfondo(this);
        sfondo.start();
        xNave = 200;
        altezzaNave = 150;
        larghezzaNave = 100;
        altezzaFiamma = 50;
        larghezzaFiamma = 20;
        frameFiamma = 0;
        try {
            nave = ImageIO.read(new File("Nave.png"));
            fiammaImg = ImageIO.read(new File("Fire/1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        stampaStelle(g);
        g.drawImage(nave, xNave, yNave, larghezzaNave, altezzaNave, null);
        stampaFiamma(g);
    }

    private void stampaStelle(Graphics g) {
        synchronized (stelle) {
            for (Stella stella : stelle) {
                g.setColor(Color.WHITE);
                g.fillOval(stella.x, stella.y, 2, 2);
            }
        }
    }

    private void stampaFiamma(Graphics g) {
        g.drawImage(fiammaImg, xNave + (larghezzaNave / 2) - (larghezzaFiamma / 2),
                yNave + altezzaNave - (altezzaFiamma / 2), larghezzaFiamma, altezzaFiamma, null);
    }
}
class Sfondo extends Thread {
    MyPanel m;

    public Sfondo(MyPanel m) {
        this.m = m;
    }

    @Override
    public void run() {
        try {
            sleep(33);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < m.getWidth(); i++) {
            m.stelle.add(new Stella(m.r.nextInt(0, m.getWidth()), m.r.nextInt(0, m.getHeight()), 8, m));
        }
        while (true) {
            if (m.isPressed) {
                if (m.xNave + m.movimento >= 0 && m.xNave + m.movimento + m.larghezzaNave <= m.getWidth())
                    m.xNave += m.movimento;
            } else {
                m.yNave = m.getHeight() - 200; // posizionamento navicella quando non si muove
            }

            synchronized (m.stelle) {
                for (int i = 0; i < m.getWidth() / 75; i++) {
                    m.stelle.add(new Stella(m.r.nextInt(0, m.getWidth()), 0, 8, m));
                }
                for (int i = 0; i < m.stelle.size(); i++) {
                    m.stelle.get(i).sposta();
                    if (m.stelle.get(i).isFinita()) m.stelle.remove(i);
                }
            }

            try {
                sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
