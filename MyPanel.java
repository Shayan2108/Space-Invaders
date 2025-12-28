import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
    volatile ArrayList<BufferedImage> immaginiDettagli = new ArrayList<>();
    volatile ArrayList<Dettagli> dettagli = new ArrayList<>();
    volatile ArrayList<Nemico> nemici = new ArrayList<>();

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
    int dirNemici;
    int velocitaNemici;
    int passoDiscesaNemici = 20;
    long ultimoSparoNemici;
    Long timer;
    long timerStampaPianeta;
    int frequezaminimaPianeti;
    int frequezaMassimaPianeti;
    int NPianeti;
    int NpngPerDettagliImmagini;

    
    boolean gameOver;
    CardLayout cl;
    JPanel contenitore;

    public MyPanel(CardLayout cl, JPanel contenitore) {
        this.cl = cl;
        this.contenitore = contenitore;
        this.setFocusable(true);
        this.addKeyListener(new MyKey(this));
        this.addMouseListener(new MyMouse(this));
        this.setBackground(Color.BLACK);
        bulletMassime = 16;
        this.bulletDisponibili = new JLabel();
        bulletDisponibili.setForeground(Color.WHITE);
        this.add(bulletDisponibili);
        r = new Random();
        sfondo = new Sfondo(this);
        game = new GameLoop(this);
        spostaBullet = new SpostaBullet(this);
        inizializzaNemici();
        NPianeti = 10;
        xNave = 200;
        movimento = 3;
        paddingX = 90;
        paddingY = 150;
        frameFiamma = 1;
        altezzaNave = 150;
        larghezzaNave = 100;
        altezzaFiamma = 50;
        dirNemici = 1; // 1 = destra, -1 = sinistra
        velocitaNemici = 2;
        passoDiscesaNemici = 20;
        larghezzaFiamma = 20;
        paddingBullet1 = 30;
        paddingBullet2 = 50;
        frequezaminimaPianeti = 10000;
        frequezaMassimaPianeti = 20000;
        timer = System.currentTimeMillis() + r.nextLong(frequezaminimaPianeti, frequezaMassimaPianeti);
        timerStampaPianeta = System.currentTimeMillis() + 1000;
        NpngPerDettagliImmagini = 8;
        uploadPianeti();
        uploadAsteroidi();
        try {
            nave = ImageIO.read(new File("Nave.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 66; i++) {
            try {
                immaginiBullet.add(ImageIO.read(new File("Laser/" + (i + 1) + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 8; i++) {
            try {
                fiamma.add(ImageIO.read(new File("Fire/" + (i + 1) + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        spostaBullet.start();

       
        pianeti.add(new Pianeti(r.nextInt(0, 400), 0, 6, MyPanel.this, immaginiPianeti.get(r.nextInt(1, NPianeti))));
        dettagli.add(new Dettagli(r.nextInt(0, 400), 0, 6, MyPanel.this,
                immaginiDettagli.get(r.nextInt(0, NpngPerDettagliImmagini))));

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                MyPanel.this.requestFocusInWindow();
                pianeti.add(new Pianeti(r.nextInt(0, 400), 0, 6, MyPanel.this,
                        immaginiPianeti.get(r.nextInt(0, NPianeti))));
                if (!sfondo.isAlive())
                    sfondo.start();
                if (!game.isAlive())
                    game.start();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        bulletDisponibili.setLocation(this.getWidth() - bulletDisponibili.getWidth(),
                getHeight() - bulletDisponibili.getHeight());
        stampaStelle(g);
        stampaAsteroidi(g);
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
            pianeti.get(i).stampaOggettiClasse(g);
        }
    }

    private void stampaAsteroidi(Graphics g) {
        for (int i = 0; i < dettagli.size(); i++) {
            dettagli.get(i).stampaDettagli(g);
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
                    e.printStackTrace();
                }
            }
        }
    }

    private void uploadAsteroidi() {
        for (int j = 0; j < NpngPerDettagliImmagini; j++) {
            try {
                immaginiDettagli.add(ImageIO.read(new File("Dettagli/" + j + ".png")));
            } catch (IOException e) {
                System.err.println("Errore caricando: Asteroidi/vio/" + j + ".png");
            }
        }
    }

    public void inizializzaNemici() {
        int righe = 3; // numero di righe di nemici
        int colonne = 6; // numero di nemici per riga
        int spazioX = 60; // distanza orizzontale tra nemici
        int spazioY = 50; // distanza verticale tra righe

        BufferedImage imgNemico = null;
        /**
         * MESSO IN COMMENTO FINO A QUANDO NON HO LA CARTELLA NEMICI CON LE FOTO
         * try {
         * imgNemico = ImageIO.read(new File("Nemici/0.png"));
         * } catch (IOException e) {
         * e.printStackTrace();
         * }
         */

        for (int r = 0; r < righe; r++) {
            for (int c = 0; c < colonne; c++) {
                int x = 50 + c * spazioX;
                int y = 50 + r * spazioY;
                Nemico n = new Nemico(x, y, imgNemico);
                nemici.add(n);
            }
        }
    }
}
