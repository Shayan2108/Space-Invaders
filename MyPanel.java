/**
 * @file MyPanel.java
 *
 * @author Mohammad Shayan Attari Bin Mohammad Zeshan Attari
 * @version 1.0
 *
 * @brief pannello principale del gioco
 *
 * La classe gestisce tutto ciò che viene disegnato a schermo:
 * stelle, pianeti, asteroidi, nave, fuoco e proiettili.
 * Contiene anche la logica per il movimento della nave e la gestione
 * dei nemici.
 */
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

    /** lista di stelle presenti sullo sfondo */
    volatile ArrayList<Stella> stelle = new ArrayList<>();

    /** immagini dei proiettili */
    volatile ArrayList<BufferedImage> immaginiBullet = new ArrayList<>();

    /** lista dei proiettili attivi */
    volatile ArrayList<Bullets> bullets = new ArrayList<>();

    /** immagini della fiamma della nave */
    volatile ArrayList<BufferedImage> fiamma = new ArrayList<>();

    /** lista dei pianeti sullo schermo */
    volatile ArrayList<Pianeti> pianeti = new ArrayList<>();

    /** immagini dei pianeti divise per tipo */
    volatile ArrayList<ArrayList<BufferedImage>> immaginiPianeti = new ArrayList<>();

    /** immagini degli asteroidi/dettagli */
    volatile ArrayList<BufferedImage> immaginiDettagli = new ArrayList<>();

    /** lista degli asteroidi/dettagli sullo schermo */
    volatile ArrayList<Dettagli> dettagli = new ArrayList<>();

    /** lista dei nemici presenti sullo schermo */
    volatile ArrayList<Nemico> nemici = new ArrayList<>();

    /** frame corrente della fiamma */
    int frameFiamma;

    /** generatore di numeri casuali */
    Random r;

    /** thread per lo sfondo */
    Sfondo sfondo;

    /** immagine della nave */
    BufferedImage nave;

    /** posizione orizzontale della nave */
    int xNave;

    /** posizione verticale della nave */
    int yNave;

    /** stato dei tasti premuti */
    volatile boolean isPressed;

    /** velocità della nave */
    volatile int movimento;

    /** thread per aggiornare il pannello */
    GameLoop game;

    /** thread per muovere i proiettili */
    SpostaBullet spostaBullet;

    /** JLabel per mostrare proiettili disponibili */
    public JLabel bulletDisponibili;

    /** numero massimo di proiettili */
    public volatile int bulletMassime;

    /** margini e dimensioni della nave e della fiamma */
    int paddingX, paddingY, larghezzaNave, altezzaNave, larghezzaFiamma, altezzaFiamma;
    int paddingBullet1, paddingBullet2;

    /** direzione e velocità dei nemici */
    int dirNemici, velocitaNemici, passoDiscesaNemici = 20;

    /** tempo dell'ultimo sparo nemici */
    long ultimoSparoNemici;

    /** timer per spawn pianeti */
    Long timer;

    /** timer per stampare pianeta */
    long timerStampaPianeta;

    /** intervalli per spawn pianeti */
    int frequezaminimaPianeti, frequezaMassimaPianeti;

    /** numero di pianeti disponibili */
    int NPianeti;

    /** numero di immagini per dettagli */
    int NpngPerDettagliImmagini;

    /** stato del gioco */
    boolean gameOver;

    /** layout e contenitore del pannello */
    CardLayout cl;
    JPanel contenitore;

    /**
     * @brief costruttore del pannello
     *
     * inizializza tutti gli attributi, immagini, thread, nemici e listener
     *
     * @param cl layout del contenitore
     * @param contenitore pannello contenitore
     */
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
        dirNemici = 1;
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
        uploadDettagli();
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

    /**
     * @brief metodo che stampa tutto la grafica di swing
     *
     * è il metodo che viene chiamato quando chiamamiamo la repaint()
     *
     * @param g oggetto grafico per disegnare
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        bulletDisponibili.setLocation(this.getWidth() - bulletDisponibili.getWidth(), getHeight() - bulletDisponibili.getHeight());
        stampaStelle(g);
        stampaAsteroidi(g);
        stampaPianeti(g);
        g.drawImage(nave, xNave, yNave, larghezzaNave, altezzaNave, null);
        stampaBullets(g);
        stampaFuoco(g);
    }

    // Metodi privati chiamati nel PiantComponent
     /**
     * @brief disegna la fiamma della nave
     * @param g oggetto grafico
     */
    private void stampaFuoco(Graphics g) {
        g.drawImage(fiamma.get(frameFiamma), xNave + (larghezzaNave / 2) - (larghezzaFiamma / 2),
                yNave + altezzaNave - (altezzaFiamma / 2) - 17, larghezzaFiamma, altezzaFiamma, null);
        frameFiamma++;
        if (frameFiamma >= 8) frameFiamma = 1;
    }

    /**
     * @brief disegna tutte le stelle sullo sfondo
     * @param g oggetto grafico
     */
    private void stampaStelle(Graphics g) {
        synchronized (stelle) {
            for (Stella stella : stelle) {
                g.setColor(Color.WHITE);
                g.fillOval(stella.x, stella.y, 2, 2);
            }
        }
    }

    /**
     * @brief disegna tutti i pianeti sullo schermo
     * @param g oggetto grafico
     */
    private void stampaPianeti(Graphics g) {
        for (Pianeti p : pianeti) {
            p.stampaOggettiClasse(g);
        }
    }

    /**
     * @brief disegna gli asteroidi/dettagli sullo schermo
     * @param g oggetto grafico
     */
    private void stampaAsteroidi(Graphics g) {
        for (Dettagli d : dettagli) {
            d.stampaDettagli(g);
        }
    }

    /**
     * @brief disegna i proiettili e aggiorna JLabel
     * @param g oggetto grafico
     */
    private void stampaBullets(Graphics g) {
        bulletDisponibili.setText("Munizione Rimanenti :" + Integer.toString(bulletMassime - bullets.size()) + "   ");
        for (Bullets b : bullets) {
            g.drawImage(b.image, b.x, b.y, 20, 60, null);
        }
    }

    /**
     * @brief carica le immagini dei pianeti
     */
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

    /**
     * @brief carica le immagini degli asteroidi/dettagli
     */
    private void uploadDettagli() {
        for (int j = 0; j < NpngPerDettagliImmagini; j++) {
            try {
                immaginiDettagli.add(ImageIO.read(new File("Dettagli/" + j + ".png")));
            } catch (IOException e) {
                System.err.println("Errore caricando: Asteroidi/vio/" + j + ".png");
            }
        }
    }
    /**
     * @brief inizializza i nemici
     *
     * posiziona i nemici in righe e colonne sullo schermo
     */
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
