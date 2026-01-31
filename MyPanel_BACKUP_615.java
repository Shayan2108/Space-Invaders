
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MyPanel extends JPanel {
    /** variabili necessari per il Audio di quando spara la nave */
    AudioInputStream audioSparatoria;
    ArrayList<Clip> sparoClip = new ArrayList<>();

    AudioInputStream audioColpito;
    ArrayList<Clip> colpitoClip = new ArrayList<>();
    /** lista di stelle presenti sullo sfondo */
    volatile ArrayList<Sfondo> sfondi = new ArrayList<>();

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

    /** immagini dele navi nemiche */
    volatile ArrayList<BufferedImage> immaginiNemici = new ArrayList<>();

    /** immagini dei frame di esplosione */
    ArrayList<BufferedImage> framesEsplosione = new ArrayList<>();
    ArrayList<BufferedImage> framesEsplosione1 = new ArrayList<>();

    /** istanze delle esplozioni */
    ArrayList<Esplosioni> esplosioni = new ArrayList<>();
    ArrayList<Esplosioni1> esplosioni1 = new ArrayList<>();

    // lista dei powerup attivi
    volatile ArrayList<PowerUp> powerUps = new ArrayList<>();
    /** frame corrente della fiamma */
    int frameFiamma;

    /** generatore di numeri casuali */
    Random r;

    /** thread per lo sfondo */
    ManagerGenerale managerGenerale;
    /** immagine per lo sfondo */
    BufferedImage immagineSfondo;
    /** numero di punti del giocatore */
    public static int score = 0;

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

    /** thread per muovere i power-up */
    SpostaPowerUp spostaPowerUp;
    /** JLabel per mostrare proiettili disponibili */
    public JLabel bulletDisponibili;
    /** label per mostrare i punti */
    public JLabel scorePoint;
    /** label per mostrare i punti massimi mai fatti */
    public JLabel maxPoint;

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
    volatile boolean gameOver;

    /**
     * stato se il gioco è in corso, all'inizio del gioco è false, quando il player
     * preme SPACE diventa true ed inizia il gioco
     */
    boolean inGioco = false;

    /** layout e contenitore del pannello */
    CardLayout cl;
    JPanel contenitore;
    /** numero di immagini delle navi nemiche */
    int NImmaginiNemici;
    /** score piu alto fatto dul gioco */
    static int scoreMassimo;

    boolean sparoMultiplo = false;

    long fineSparoMultiplo;

    boolean scudoAttivo = false;
    long fineScudo;

    boolean respingiAttivo = false;
    long fineRespingi;

    public boolean rallentaAttivo = false;
    public long fineRallenta;

    boolean proiettiliPenetrantiAttivi = false;
    long fineProiettiliPenetranti;

    /**
     * @brief costruttore del pannello
     *
     *        inizializza tutti gli attributi, immagini, thread, nemici e listener
     *
     * @param cl          layout del contenitore
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
        this.scorePoint = new JLabel();
        scorePoint.setForeground(Color.WHITE);
        this.add(scorePoint);
        this.maxPoint = new JLabel();
        maxPoint.setForeground(Color.WHITE);
        this.add(maxPoint);
        r = new Random();
        managerGenerale = new ManagerGenerale(this);
        sfondi.add(new Sfondo(0, 0, this));
        game = new GameLoop(this);
        spostaBullet = new SpostaBullet(this);
<<<<<<< HEAD
        spostaPowerUp = new SpostaPowerUp(this);
        NPianeti = 10;
=======
        NPianeti = 11;
>>>>>>> 404097ed7e1483892ce68c7a3ce6825d133d0a74
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
        this.NImmaginiNemici = 8;
        gameOver = false;
        try {
            immagineSfondo = ImageIO.read(new File("Sfondo.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        uploadPianeti();
        uploadDettagli();
        inizializzaNemici();
        InizializzaImmaginiEsplosioni();
        InizializzaImmaginiEsplosioni1();
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
        dettagli.add(new Dettagli(r.nextInt(0, 400), 0, 6, MyPanel.this,
                immaginiDettagli.get(r.nextInt(0, NpngPerDettagliImmagini))));

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader("gr.txt"));
                    scoreMassimo = Integer.parseInt(br.readLine());
                    br.close();
                } catch (NumberFormatException | IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                gameOver = false;
                MyPanel.this.requestFocusInWindow();
                pianeti.add(new Pianeti(r.nextInt(0, 400), 0, 6, MyPanel.this,
                        immaginiPianeti.get(r.nextInt(0, NPianeti))));
                managerGenerale = new ManagerGenerale(MyPanel.this);
                spostaBullet = new SpostaBullet(MyPanel.this);
                // if (!managerGenerale.isAlive())
                managerGenerale.start();
                if (!game.isAlive())
                    game.start();
                // if (!spostaBullet.isAlive())
                spostaBullet.start();
                if (!sfondi.getLast().isAlive())
                    sfondi.getLast().start();
                spostaPowerUp.start();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                for (int i = 0; i < nemici.size(); i++) {
                    nemici.get(i).isVivo = false;
                }
                nemici.clear();
                pianeti.clear();
                dettagli.clear();
                bullets.clear();
                esplosioni.clear();
                esplosioni1.clear();
            }
        });
    }

    /**
     * @brief metodo che stampa tutto la grafica di swing
     *
     *        è il metodo che viene chiamato quando chiamamiamo la repaint()
     *
     * @param g oggetto grafico per disegnare
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // setto il text e position delle label
        setLabel();
        // Grafica di sfondo sempre
        StampaSfondo(g);
        stampaDettagli(g);
        stampaPianeti(g);
        g.drawImage(nave, xNave, yNave, larghezzaNave, altezzaNave, null);
        stampaBullets(g);
        stampaFuoco(g);
        stampaNemici(g);
        stampaEsplosioni(g);
        stampaEsplosioni1(g);
        stampaPowerUps(g);
        stampaEffettoScudo(g);
        stampaEffettoRespingi(g);
        stampaEffettoRallenta(g);
        stampaEffettoProiettiliPenetranti(g);
    }

    private void setLabel() {
        bulletDisponibili.setLocation(this.getWidth() - bulletDisponibili.getWidth(),
                getHeight() - bulletDisponibili.getHeight());
        scorePoint.setLocation((this.getWidth() - scorePoint.getWidth()) / 2, 0);
        scorePoint.setText(("score:" + Integer.toString(score) + " "));

        maxPoint.setLocation((this.getWidth() - maxPoint.getWidth()), 0);
        maxPoint.setText(("score:" + Integer.toString(scoreMassimo) + " "));
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
        if (frameFiamma >= 8)
            frameFiamma = 1;
    }

    /**
     * @brief disegna tutte le stelle sullo sfondo
     * @param g oggetto grafico
     */
    private void StampaSfondo(Graphics g) {
        synchronized (sfondi) {
            for (int i = 0; i < sfondi.size(); i++) {
                g.drawImage(immagineSfondo, sfondi.get(i).x, sfondi.get(i).y, 400, 800, null);
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
    private void stampaDettagli(Graphics g) {
        synchronized (dettagli) {
            for (int i = 0; i < dettagli.size(); i++) {
                dettagli.get(i).stampaDettagli(g);
            }
        }
    }

    /**
     * @brief disegna i proiettili e aggiorna JLabel
     * @param g oggetto grafico
     */
    private void stampaBullets(Graphics g) {
        bulletDisponibili.setText("Munizione Rimanenti :" + Integer.toString(bulletMassime - bullets.size()) + "   ");
        synchronized (bullets) {
            for (int i = 0; i < bullets.size(); i++) {
                g.drawImage(bullets.get(i).image, bullets.get(i).x, bullets.get(i).y, 20, 60, null);
            }
        }
    }

    /**
     * @brief disegna i power-ups e aggiorna JLabel
     * @param g oggetto grafico
     */
    private void stampaPowerUps(Graphics g) {
        synchronized (powerUps) {
            for (PowerUp p : powerUps) {
                // Disegna l'immagine giusta che è stata impostata in base al tipo
                p.stampaOggettiClasse(g);
            }
        }
    }

    private void stampaEffettoScudo(Graphics g) {
        if (scudoAttivo) {
            // Disegna un cerchio intorno alla nave per indicare lo scudo attivo
            g.setColor(new Color(0, 0, 255, 100)); // Colore blu trasparente
            g.fillOval(xNave - 10, yNave - 10, larghezzaNave + 20, altezzaNave + 20);
        }
    }

    private void stampaEffettoRespingi(Graphics g) {
        if (respingiAttivo) {
            g.setColor(new Color(255, 100, 0, 120));
            g.drawOval(xNave - 20, yNave - 20, larghezzaNave + 40, altezzaNave + 40);
        }
    }

    private void stampaEffettoRallenta(Graphics g) {
        if (rallentaAttivo) {
            g.setColor(new Color(0, 0, 255, 120)); // blu trasparente
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void stampaEffettoProiettiliPenetranti(Graphics g) {
        if (proiettiliPenetrantiAttivi) {
            g.setColor(new Color(0, 255, 255, 150)); // alone ciano
            synchronized (bullets) {
                for (Bullets b : bullets) {
                    g.fillOval(b.x - 5, b.y - 5, 30, 30);
                }
            }
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
     *        posiziona i nemici in righe e colonne sullo schermo
     */
    public void inizializzaNemici() {

        try {
            for (int i = 0; i < NImmaginiNemici; i++) {
                immaginiNemici.add(ImageIO.read(new File("Astronavi Nemiche/" + i + ".png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stampaNemici(Graphics g) {
        synchronized (nemici) {
            for (Nemico n : nemici) {
                n.stampaOggettiClasse(g);
            }
        }
    }

    private void InizializzaImmaginiEsplosioni() {
        for (int i = 0; i < 70; i++) {
            try {
                framesEsplosione.add(ImageIO.read(new File("Esplosioni/" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void InizializzaImmaginiEsplosioni1() {
        for (int i = 0; i < 16; i++) {
            try {
                framesEsplosione1.add(ImageIO.read(new File("Esplosioni1/" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stampaEsplosioni(Graphics g) {
        for (int i = 0; i < esplosioni.size(); i++) {
            if (esplosioni.get(i).frameinEseguzione < esplosioni.get(i).maxFrame) {
                g.drawImage(framesEsplosione.get(esplosioni.get(i).frameinEseguzione), esplosioni.get(i).x,
                        esplosioni.get(i).y, null);
                esplosioni.get(i).frameinEseguzione++;
                esplosioni.get(i).y += esplosioni.get(i).avanzamento;
            } else {
                synchronized (esplosioni) {
                    esplosioni.remove(esplosioni.get(i));
                }

            }
        }
    }

    private void stampaEsplosioni1(Graphics g) {
        for (int i = 0; i < esplosioni1.size(); i++) {
            if (esplosioni1.get(i).frameinEseguzione < esplosioni1.get(i).maxFrame) {
                g.drawImage(framesEsplosione1.get(esplosioni1.get(i).frameinEseguzione), esplosioni1.get(i).x,
                        esplosioni1.get(i).y, 200, 200, null);
                esplosioni1.get(i).frameinEseguzione++;
                esplosioni1.get(i).y += esplosioni1.get(i).avanzamento;
            } else {
                synchronized (esplosioni1) {
                    esplosioni1.remove(esplosioni1.get(i));
                }

            }
        }
    }

    public void attivaSparoMultiplo() {
        sparoMultiplo = true;
        fineSparoMultiplo = System.currentTimeMillis() + 8000;
    }

    public void attivaScudo() {
        scudoAttivo = true;
        fineScudo = System.currentTimeMillis() + 6000;
    }

    public void attivaRespingi() {
        respingiAttivo = true;
        fineRespingi = System.currentTimeMillis() + 5000; // 5 sec
    }

    public void attivaRallenta() {
        rallentaAttivo = true;
        fineRallenta = System.currentTimeMillis() + 5000; // dura 5 secondi
    }

    public void attivaProiettiliPenetranti() {
        proiettiliPenetrantiAttivi = true;
        fineProiettiliPenetranti = System.currentTimeMillis() + 5000; // 5 sec
    }
}
