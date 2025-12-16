import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MyPanel extends JPanel {
    volatile ArrayList<Stella> stelle = new ArrayList<>();
    volatile ArrayList<BufferedImage> immaginiBullet = new ArrayList<>();
    volatile ArrayList<Bullets> bullets = new ArrayList<>();
    volatile ArrayList<BufferedImage> fiamma = new ArrayList<>();
    volatile ArrayList<Pianeti> pianeti = new ArrayList<>();
    volatile ArrayList<ArrayList<BufferedImage>> immaginiPianeti = new ArrayList<>();
    int frameFiamma;
    Random r;
    Sfondo sfondo;
    BufferedImage nave;
    int xNave;
    int yNave;
    volatile boolean isPressed;
    volatile int movimento;
    GameLoop game;
    SpostaBullet spostaBullet;
    public JLabel bulletDisponibili;
    public volatile int bulletMassime;
    int paddingX;
    int paddingY;
    int larghezzaNave;
    int altezzaNave;
    int larghezzaFiamma;
    int altezzaFiamma;
    int paddingBullet1;
    int paddingBullet2;
    Long timer;
    long timerStampaPianeta;
    int frequezaminimaPianeti;
    int frequezaMassimaPianeti;
    int NPianeti;
    

    public MyPanel() {
        this.setFocusable(true);
        this.addKeyListener(new MyKey(this));
        this.addMouseListener(new MyMouse(this));
        this.setBackground(Color.BLACK);
        bulletMassime = 16;
        this.bulletDisponibili = new JLabel();
        bulletDisponibili.setForeground(Color.WHITE);
        this.add(bulletDisponibili);
        r = new Random();
        sfondo = new Sfondo();
        game = new GameLoop(this);
        spostaBullet = new SpostaBullet(this);
        NPianeti = 10;
        uploadPianeti();
        try {
            nave = ImageIO.read(new File("Nave.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (int i = 0; i < 66; i++) {
            try {
                immaginiBullet.add(ImageIO.read(new File("Laser/" + (i + 1) + ".png")));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 8; i++) {
            try {
                fiamma.add(ImageIO.read(new File("Fire/" + (i + 1) + ".png")));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        xNave = 200;
        movimento = 3;
        game.start();
        sfondo.start();
        spostaBullet.start();
        paddingX = 90;
        paddingY = 150;
        frameFiamma = 1;
        altezzaNave = 150;
        larghezzaNave = 100;
        altezzaFiamma = 50;
        larghezzaFiamma = 20;
        paddingBullet1 = 30;
        paddingBullet2 = 50;
        frequezaminimaPianeti = 10000;
        frequezaMassimaPianeti = 20000;
        timer = System.currentTimeMillis() + r.nextLong(frequezaminimaPianeti, frequezaMassimaPianeti);
        timerStampaPianeta = System.currentTimeMillis() + 1000;
        pianeti.add(new Pianeti(r.nextInt(0, 400), 0, 6, MyPanel.this,immaginiPianeti.get(r.nextInt(1, NPianeti))));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        bulletDisponibili.setLocation(this.getWidth() - bulletDisponibili.getWidth(),
                getHeight() - bulletDisponibili.getHeight());
        stampaStelle(g);
        stampaPianeti(g);
        g.drawImage(nave, xNave, yNave, larghezzaNave, altezzaNave, null);
        stampaBullets(g);
        stampaFuoco(g);
    }
    private void stampaFuoco(Graphics g) {
        g.drawImage(fiamma.get(frameFiamma), xNave + (larghezzaNave / 2) - (larghezzaFiamma / 2),
                yNave + altezzaNave - (altezzaFiamma / 2) - 17, larghezzaFiamma, altezzaFiamma, null);
        frameFiamma++;
        if (frameFiamma >= 8)
            frameFiamma = 1;
    }

    private void stampaStelle(Graphics g) {
        synchronized (stelle) {
            for (Stella stella : stelle) {
                g.setColor(Color.WHITE);
                g.fillOval(stella.x, stella.y, 2, 2);
            }
        }
    }

    private void stampaPianeti(Graphics g) {
        for (int i = 0; i < pianeti.size(); i++) {
            pianeti.get(i).stampaPianeti(g);
        }
    }

    private void stampaBullets(Graphics g) {
        this.bulletDisponibili
                .setText("Munizione Rimanenti :" + Integer.toString(bulletMassime - bullets.size()) + "   ");
        for (int i = 0; i < bullets.size(); i++) {
            g.drawImage(bullets.get(i).image, bullets.get(i).x, bullets.get(i).y, 20, 60, null);
        }
    }

    private void uploadPianeti() {
        for (int i = 0; i < NPianeti; i++) {
            immaginiPianeti.add(new ArrayList<>());
        }
        for (int i = 0; i < NPianeti; i++) {
            for (int j = 0; j < 60; j++) {
                try {
                    immaginiPianeti.get(i).add(ImageIO.read(new File("Planets/" + (i + 1) + "/" + j + ".png")));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }

    class Sfondo extends Thread {
        @Override
        public void run() {
            try {
                sleep(33);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            synchronized (stelle) {
                for (int i = 0; i < getWidth(); i++) {
                    stelle.add(new Stella(r.nextInt(0, getWidth()), r.nextInt(0, getHeight()), 8, MyPanel.this));
                }
            }
            while (true) {
                if (isPressed) {
                    if (xNave + paddingX + movimento <= getWidth() && xNave + movimento >= 0)
                        xNave += movimento;
                    else if (xNave + paddingX + movimento > getWidth()) {
                        xNave = getWidth() - paddingX + 1;
                    } else if (xNave + movimento < 0) {
                        xNave = 0;
                    }
                } else {
                    yNave = getHeight() - paddingY;
                }
                try {
                    sleep(33);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
                if (System.currentTimeMillis() >= timer) {
                    pianeti.add(new Pianeti(r.nextInt(0, getWidth()), 0, 6, MyPanel.this,
                    immaginiPianeti.get(r.nextInt(1, NPianeti))));
                    timer = System.currentTimeMillis() + r.nextLong(frequezaminimaPianeti, frequezaMassimaPianeti);
                }
                synchronized (stelle) {
                    for (int i = 0; i < getWidth() / 75; i++) {
                        stelle.add(new Stella(r.nextInt(0, getWidth()), 0, 8, MyPanel.this));
                    }
                    for (int i = 0; i < stelle.size(); i++) {
                        stelle.get(i).sposta();
                        if (stelle.get(i).isFinita()) {
                            stelle.remove(i);
                        }
                    }
                }
            }
        }
    }
}
